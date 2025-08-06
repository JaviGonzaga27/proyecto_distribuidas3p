package microservicio_facturacion.service;

import microservicio_facturacion.dto.EstadoRequest;
import microservicio_facturacion.entity.Factura;
import microservicio_facturacion.entity.PrecioProducto;
import microservicio_facturacion.repository.FacturaRepository;
import microservicio_facturacion.repository.PrecioProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FacturacionService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${services.central.url}")
    private String centralServiceUrl;

    public Factura generarFactura(String cosechaId, String producto, Double toneladas) {
        // Calcular monto
        BigDecimal monto = calcularMonto(producto, toneladas);

        // Generar número de factura
        String numeroFactura = generarNumeroFactura();

        // Crear factura
        Factura factura = new Factura();
        factura.setId(UUID.randomUUID().toString());
        factura.setCosechaId(cosechaId);
        factura.setNumeroFactura(numeroFactura);
        factura.setMonto(monto);
        factura.setPagado(false);
        factura.setFechaCreacion(LocalDateTime.now());
        factura.setFechaVencimiento(LocalDateTime.now().plusDays(30)); // 30 días para pagar
        factura.setObservaciones("Factura por cosecha de " + producto);

        // Guardar factura
        Factura facturaGuardada = facturaRepository.save(factura);

        // Notificar al servicio central
        notificarCentralEstadoFacturada(cosechaId, facturaGuardada.getId());

        return facturaGuardada;
    }

    private BigDecimal calcularMonto(String producto, Double toneladas) {
        PrecioProducto precio = precioProductoRepository.findByProductoAndActivo(producto, true)
                .orElse(null);

        if (precio != null) {
            return precio.getPrecioPorTonelada().multiply(BigDecimal.valueOf(toneladas));
        } else {
            // Precio por defecto si no existe configuración
            return BigDecimal.valueOf(100).multiply(BigDecimal.valueOf(toneladas));
        }
    }

    private String generarNumeroFactura() {
        int year = LocalDateTime.now().getYear();
        long count = facturaRepository.countByFechaCreacionYear(year) + 1;
        return String.format("F-%d-%06d", year, count);
    }

    private void notificarCentralEstadoFacturada(String cosechaId, String facturaId) {
        try {
            String url = centralServiceUrl + "/api/cosechas/" + cosechaId + "/estado";
            EstadoRequest request = new EstadoRequest();
            request.setEstado("FACTURADA");
            request.setFacturaId(facturaId);

            restTemplate.put(url, request);
            System.out.println("✅ Notificado estado FACTURADA para cosecha: " + cosechaId);
        } catch (Exception e) {
            System.err.println("❌ Error notificando al servicio central: " + e.getMessage());
        }
    }

    public List<Factura> obtenerTodasLasFacturas() {
        return facturaRepository.findAll();
    }

    public List<Factura> obtenerFacturasPendientes() {
        return facturaRepository.findByPagado(false);
    }
}