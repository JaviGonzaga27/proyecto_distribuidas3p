package microservicio_inventario.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "insumos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Insumo {
    @Id
    private String id;

    @NotBlank
    @Column(name = "nombre_insumo", unique = true)
    private String nombreInsumo;

    @PositiveOrZero
    private Double stock;

    @NotBlank
    private String unidad; // kg, litros, unidades

    @PositiveOrZero
    @Column(name = "precio_unitario")
    private Double precioUnitario;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}