package microservicio_inventario.controller;


import microservicio_inventario.entity.Insumo;
import microservicio_inventario.repository.InsumoRepository;
import microservicio_inventario.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/insumos")
@CrossOrigin(origins = "*")
public class InsumoController {

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Insumo> obtenerTodos() {
        return inventarioService.obtenerTodosLosInsumos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Insumo> obtenerPorId(@PathVariable String id) {
        return insumoRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Insumo> crear(@Valid @RequestBody Insumo insumo) {
        insumo.setId(UUID.randomUUID().toString());
        insumo.setFechaActualizacion(LocalDateTime.now());
        Insumo insumoGuardado = insumoRepository.save(insumo);
        return ResponseEntity.ok(insumoGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Insumo> actualizar(@PathVariable String id,
                                             @Valid @RequestBody Insumo insumo) {
        return insumoRepository.findById(id)
                .map(insumoExistente -> {
                    insumoExistente.setNombreInsumo(insumo.getNombreInsumo());
                    insumoExistente.setStock(insumo.getStock());
                    insumoExistente.setUnidad(insumo.getUnidad());
                    insumoExistente.setPrecioUnitario(insumo.getPrecioUnitario());
                    insumoExistente.setFechaActualizacion(LocalDateTime.now());
                    return ResponseEntity.ok(insumoRepository.save(insumoExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return insumoRepository.findById(id)
                .map(insumo -> {
                    insumoRepository.delete(insumo);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/stock-bajo")
    public List<Insumo> obtenerStockBajo() {
        return inventarioService.obtenerInsumosConStockBajo();
    }
}
