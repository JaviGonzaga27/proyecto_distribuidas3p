package microservicio_central.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class CosechaRequest {

    @NotBlank
    private String agricultorId;

    @NotBlank
    private String producto;

    @Positive
    private Double toneladas;

    private String ubicacion; // GPS opcional

    // Constructores
    public CosechaRequest() {}

    public CosechaRequest(String agricultorId, String producto, Double toneladas, String ubicacion) {
        this.agricultorId = agricultorId;
        this.producto = producto;
        this.toneladas = toneladas;
        this.ubicacion = ubicacion;
    }

    // Getters y Setters
    public String getAgricultorId() { return agricultorId; }
    public void setAgricultorId(String agricultorId) { this.agricultorId = agricultorId; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Double getToneladas() { return toneladas; }
    public void setToneladas(Double toneladas) { this.toneladas = toneladas; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
}

