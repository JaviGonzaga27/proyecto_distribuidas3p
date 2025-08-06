package microservicio_facturacion.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoCosecha {
    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private String cosechaId;
    private String producto;
    private Double toneladas;
    private String[] requiereInsumos;
}
