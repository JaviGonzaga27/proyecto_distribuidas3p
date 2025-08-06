package microservicio_central.service;

import microservicio_central.config.RabbitConfig;
import microservicio_central.dto.EventoCosecha;
import microservicio_central.entity.Cosecha;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class EventPublisherService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void publicarNuevaCosecha(Cosecha cosecha) {
        EventoCosecha evento = new EventoCosecha();
        evento.setEventId(UUID.randomUUID().toString());
        evento.setEventType("nueva_cosecha");
        evento.setTimestamp(LocalDateTime.now());
        evento.setCosechaId(cosecha.getId());
        evento.setProducto(cosecha.getProducto());
        evento.setToneladas(cosecha.getToneladas());
        evento.setRequiereInsumos(determinarInsumos(cosecha.getProducto()));

        rabbitTemplate.convertAndSend(RabbitConfig.COSECHAS_EXCHANGE, "nueva", evento);

        System.out.println("Evento publicado: " + evento.getEventType() + " - " + cosecha.getId());
    }

    private String[] determinarInsumos(String producto) {
        // Lógica para determinar qué insumos necesita cada producto
        switch (producto.toLowerCase()) {
            case "arroz oro":
                return new String[]{"Semilla Arroz L-23", "Fertilizante N-PK"};
            case "café premium":
                return new String[]{"Semilla Café", "Fertilizante Orgánico"};
            default:
                return new String[]{"Fertilizante General"};
        }
    }
}

