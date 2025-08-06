package microservicio_central.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "cosechas")
public class Cosecha {

    @Id
    private String id; // UUID

    @NotBlank
    @Column(name = "agricultor_id", nullable = false)
    private String agricultorId;

    @NotBlank
    private String producto;

    @Positive
    private Double toneladas;

    @Enumerated(EnumType.STRING)
    private EstadoCosecha estado;

    @Column(name = "factura_id")
    private String facturaId;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    private String ubicacion; // GPS opcional

    // Constructor vacío
    public Cosecha() {}

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getAgricultorId() { return agricultorId; }
    public void setAgricultorId(String agricultorId) { this.agricultorId = agricultorId; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Double getToneladas() { return toneladas; }
    public void setToneladas(Double toneladas) { this.toneladas = toneladas; }

    public EstadoCosecha getEstado() { return estado; }
    public void setEstado(EstadoCosecha estado) { this.estado = estado; }

    public String getFacturaId() { return facturaId; }
    public void setFacturaId(String facturaId) { this.facturaId = facturaId; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}

