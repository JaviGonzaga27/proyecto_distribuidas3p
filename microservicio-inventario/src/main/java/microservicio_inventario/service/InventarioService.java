package microservicio_inventario.service;


import microservicio_inventario.entity.Insumo;
import microservicio_inventario.entity.MovimientoInventario;
import microservicio_inventario.entity.TipoMovimiento;
import microservicio_inventario.repository.InsumoRepository;
import microservicio_inventario.repository.MovimientoInventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class InventarioService {

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private MovimientoInventarioRepository movimientoRepository;

    @Transactional
    public boolean procesarConsumoInsumos(String cosechaId, String producto, Double toneladas) {
        try {
            // Calcular insumos necesarios según el producto
            List<String[]> insumosNecesarios = calcularInsumosNecesarios(producto, toneladas);

            for (String[] insumoData : insumosNecesarios) {
                String nombreInsumo = insumoData[0];
                Double cantidad = Double.parseDouble(insumoData[1]);

                // Descontar stock
                int filasAfectadas = insumoRepository.descontarStock(nombreInsumo, cantidad);

                if (filasAfectadas > 0) {
                    // Registrar movimiento
                    registrarMovimiento(nombreInsumo, cosechaId, TipoMovimiento.SALIDA,
                            cantidad, "Consumo por cosecha " + producto);
                    System.out.println("Descontado stock: " + nombreInsumo + " - " + cantidad + " kg");
                } else {
                    System.err.println("Stock insuficiente para: " + nombreInsumo);
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            System.err.println("Error procesando insumos: " + e.getMessage());
            return false;
        }
    }

    private List<String[]> calcularInsumosNecesarios(String producto, Double toneladas) {
        List<String[]> insumos = new ArrayList<>();

        switch (producto.toLowerCase()) {
            case "arroz oro":
                insumos.add(new String[]{"Semilla Arroz L-23", String.valueOf(toneladas * 5)}); // 5kg por tonelada
                insumos.add(new String[]{"Fertilizante N-PK", String.valueOf(toneladas * 2)}); // 2kg por tonelada
                break;
            case "café premium":
                insumos.add(new String[]{"Semilla Café", String.valueOf(toneladas * 3)});
                insumos.add(new String[]{"Fertilizante Orgánico", String.valueOf(toneladas * 1.5)});
                break;
            default:
                insumos.add(new String[]{"Fertilizante General", String.valueOf(toneladas * 1)});
        }

        return insumos;
    }

    private void registrarMovimiento(String nombreInsumo, String cosechaId,
                                     TipoMovimiento tipo, Double cantidad, String descripcion) {
        // Encontrar el insumo
        Insumo insumo = insumoRepository.findByNombreInsumo(nombreInsumo).orElse(null);
        if (insumo == null) return;

        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setId(UUID.randomUUID().toString());
        movimiento.setInsumoId(insumo.getId());
        movimiento.setCosechaId(cosechaId);
        movimiento.setTipo(tipo);
        movimiento.setCantidad(cantidad);
        movimiento.setDescripcion(descripcion);
        movimiento.setFechaMovimiento(LocalDateTime.now());

        movimientoRepository.save(movimiento);
    }

    public List<Insumo> obtenerTodosLosInsumos() {
        return insumoRepository.findAll();
    }

    public List<Insumo> obtenerInsumosConStockBajo() {
        return insumoRepository.findInsumosConStockBajo();
    }
}