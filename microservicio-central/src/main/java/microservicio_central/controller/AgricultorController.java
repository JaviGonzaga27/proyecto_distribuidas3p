package microservicio_central.controller;

import microservicio_central.entity.Agricultor;
import microservicio_central.repository.AgricultorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/agricultores")
@CrossOrigin(origins = "*")
public class AgricultorController {

    @Autowired
    private AgricultorRepository agricultorRepository;

    @GetMapping
    public List<Agricultor> obtenerTodos() {
        return agricultorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agricultor> obtenerPorId(@PathVariable String id) {
        return agricultorRepository.findById(id)
                .map(agricultor -> ResponseEntity.ok(agricultor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Agricultor> crear(@Valid @RequestBody Agricultor agricultor) {
        if (agricultorRepository.existsByEmail(agricultor.getEmail())) {
            return ResponseEntity.badRequest().build();
        }

        agricultor.setId(UUID.randomUUID().toString());
        agricultor.setFechaRegistro(LocalDateTime.now());

        Agricultor agricultorGuardado = agricultorRepository.save(agricultor);
        return ResponseEntity.ok(agricultorGuardado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agricultor> actualizar(@PathVariable String id,
                                                 @Valid @RequestBody Agricultor agricultor) {
        return agricultorRepository.findById(id)
                .map(agricultorExistente -> {
                    agricultorExistente.setNombre(agricultor.getNombre());
                    agricultorExistente.setEmail(agricultor.getEmail());
                    agricultorExistente.setTelefono(agricultor.getTelefono());
                    return ResponseEntity.ok(agricultorRepository.save(agricultorExistente));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable String id) {
        return agricultorRepository.findById(id)
                .map(agricultor -> {
                    agricultorRepository.delete(agricultor);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
