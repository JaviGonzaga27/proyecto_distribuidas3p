package microservicio_central.service;

import microservicio_central.dto.CosechaRequest;
import microservicio_central.entity.Cosecha;
import microservicio_central.entity.EstadoCosecha;
import microservicio_central.repository.CosechaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CosechaService {

    @Autowired
    private CosechaRepository cosechaRepository;

    @Autowired
    private EventPublisherService eventPublisher;

    public Cosecha registrarCosecha(CosechaRequest request) {
        // Crear nueva cosecha
        Cosecha cosecha = new Cosecha();
        cosecha.setId(UUID.randomUUID().toString());
        cosecha.setAgricultorId(request.getAgricultorId());
        cosecha.setProducto(request.getProducto());
        cosecha.setToneladas(request.getToneladas());
        cosecha.setUbicacion(request.getUbicacion());
        cosecha.setEstado(EstadoCosecha.REGISTRADA);
        cosecha.setFechaRegistro(LocalDateTime.now());

        // Guardar en base de datos
        Cosecha cosechaGuardada = cosechaRepository.save(cosecha);

        // Publicar evento en RabbitMQ
        eventPublisher.publicarNuevaCosecha(cosechaGuardada);

        return cosechaGuardada;
    }

    public void actualizarEstado(String cosechaId, String estado, String facturaId) {
        Cosecha cosecha = cosechaRepository.findById(cosechaId)
                .orElseThrow(() -> new RuntimeException("Cosecha no encontrada"));

        cosecha.setEstado(EstadoCosecha.valueOf(estado));
        if (facturaId != null) {
            cosecha.setFacturaId(facturaId);
        }

        cosechaRepository.save(cosecha);
    }

    public List<Cosecha> obtenerCosechasPorAgricultor(String agricultorId) {
        return cosechaRepository.findByAgricultorIdOrderByFechaDesc(agricultorId);
    }

    public List<Cosecha> obtenerTodasLasCosechas() {
        return cosechaRepository.findAll();
    }
}
