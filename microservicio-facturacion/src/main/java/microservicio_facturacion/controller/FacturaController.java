package microservicio_facturacion.controller;

import microservicio_facturacion.entity.Factura;
import microservicio_facturacion.repository.FacturaRepository;
import microservicio_facturacion.service.FacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/facturas")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private FacturacionService facturacionService;

    @GetMapping
    public List<Factura> obtenerTodas() {
        return facturacionService.obtenerTodasLasFacturas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> obtenerPorId(@PathVariable String id) {
        return facturaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cosecha/{cosechaId}")
    public ResponseEntity<Factura> obtenerPorCosecha(@PathVariable String cosechaId) {
        return facturaRepository.findByCosechaId(cosechaId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Factura> marcarComoPagada(@PathVariable String id) {
        return facturaRepository.findById(id)
                .map(factura -> {
                    factura.setPagado(true);
                    return ResponseEntity.ok(facturaRepository.save(factura));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/pendientes")
    public List<Factura> obtenerFacturasPendientes() {
        return facturacionService.obtenerFacturasPendientes();
    }
}