package microservicio_facturacion.controller;

import microservicio_facturacion.entity.PrecioProducto;
import microservicio_facturacion.repository.PrecioProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/precios")
@CrossOrigin(origins = "*")
public class PrecioProductoController {

    @Autowired
    private PrecioProductoRepository precioProductoRepository;

    @GetMapping
    public List<PrecioProducto> obtenerTodos() {
        return precioProductoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<PrecioProducto> crear(@Valid @RequestBody PrecioProducto precio) {
        precio.setId(UUID.randomUUID().toString());
        PrecioProducto precioGuardado = precioProductoRepository.save(precio);
        return ResponseEntity.ok(precioGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecioProducto> actualizar(@PathVariable String id,
                                                     @Valid @RequestBody PrecioProducto precio) {
        return precioProductoRepository.findById(id)
                .map(precioExistente -> {
                    precioExistente.setProducto(precio.getProducto());
                    precioExistente.setPrecioPorTonelada(precio.getPrecioPorTonelada());
                    precioExistente.setActivo(precio.getActivo());
                    return ResponseEntity.ok(precioProductoRepository.save(precioExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}