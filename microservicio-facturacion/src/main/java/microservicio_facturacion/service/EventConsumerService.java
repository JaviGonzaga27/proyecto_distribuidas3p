package microservicio_facturacion.service;

import microservicio_facturacion.dto.EventoCosecha;
import microservicio_facturacion.entity.Factura;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerService {

    @Autowired
    private FacturacionService facturacionService;

    @RabbitListener(queues = "cola_facturacion")
    public void procesarEventoCosecha(EventoCosecha evento) {
        System.out.println("🧾 Generando factura para cosecha: " + evento.getCosechaId());

        try {
            Factura factura = facturacionService.generarFactura(
                    evento.getCosechaId(),
                    evento.getProducto(),
                    evento.getToneladas()
            );

            System.out.println(" Factura generada: " + factura.getNumeroFactura()
                    + " - Monto: $" + factura.getMonto());
        } catch (Exception e) {
            System.err.println(" Error generando factura: " + e.getMessage());
        }
    }
}