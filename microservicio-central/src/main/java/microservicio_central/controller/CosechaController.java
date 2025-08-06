package microservicio_central.controller;

import microservicio_central.dto.CosechaRequest;
import microservicio_central.dto.EstadoRequest;
import microservicio_central.entity.Cosecha;
import microservicio_central.service.CosechaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/cosechas")
@CrossOrigin(origins = "*")
public class CosechaController {

    @Autowired
    private CosechaService cosechaService;

    @GetMapping
    public List<Cosecha> obtenerTodas() {
        return cosechaService.obtenerTodasLasCosechas();
    }

    @PostMapping
    public ResponseEntity<Cosecha> registrarCosecha(@Valid @RequestBody CosechaRequest request) {
        try {
            Cosecha cosecha = cosechaService.registrarCosecha(request);
            return ResponseEntity.ok(cosecha);
        } catch (Exception e) {
            System.err.println("Error registrando cosecha: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> actualizarEstado(@PathVariable String id,
                                                 @RequestBody EstadoRequest request) {
        try {
            cosechaService.actualizarEstado(id, request.getEstado(), request.getFacturaId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println("Error actualizando estado: " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/agricultor/{agricultorId}")
    public List<Cosecha> obtenerPorAgricultor(@PathVariable String agricultorId) {
        return cosechaService.obtenerCosechasPorAgricultor(agricultorId);
    }
}
