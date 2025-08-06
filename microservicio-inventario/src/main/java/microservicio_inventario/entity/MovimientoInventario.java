package microservicio_inventario.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoInventario {
    @Id
    private String id;

    @Column(name = "insumo_id")
    private String insumoId;

    @Column(name = "cosecha_id")
    private String cosechaId;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    private Double cantidad;

    private String descripcion;

    @Column(name = "fecha_movimiento")
    private LocalDateTime fechaMovimiento;
}



