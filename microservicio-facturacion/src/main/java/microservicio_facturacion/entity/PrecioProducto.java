package microservicio_facturacion.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "precios_productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioProducto {
    @Id
    private String id;

    @Column(unique = true)
    private String producto;

    @Column(name = "precio_por_tonelada")
    private BigDecimal precioPorTonelada;

    private Boolean activo;
}