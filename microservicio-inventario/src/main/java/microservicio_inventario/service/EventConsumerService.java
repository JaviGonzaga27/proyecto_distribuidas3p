package microservicio_inventario.service;
import microservicio_inventario.dto.EventoCosecha;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {

    @Autowired
    private InventarioService inventarioService;

    @RabbitListener(queues = "cola_inventario")
    public void procesarEventoCosecha(EventoCosecha evento) {
        System.out.println("📦 Procesando evento de cosecha: " + evento.getCosechaId());

        boolean procesado = inventarioService.procesarConsumoInsumos(
                evento.getCosechaId(),
                evento.getProducto(),
                evento.getToneladas()
        );

        if (procesado) {
            System.out.println("✅ Inventario ajustado para cosecha: " + evento.getCosechaId());
        } else {
            System.err.println("❌ Error ajustando inventario para: " + evento.getCosechaId());
        }
    }
}