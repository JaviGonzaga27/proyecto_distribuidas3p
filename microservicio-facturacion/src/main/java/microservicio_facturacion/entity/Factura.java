package microservicio_facturacion.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Factura {
    @Id
    private String id;

    @Column(name = "cosecha_id", nullable = false)
    private String cosechaId;

    @Column(name = "numero_factura", unique = true)
    private String numeroFactura;

    private BigDecimal monto;

    private Boolean pagado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    private String observaciones;
}