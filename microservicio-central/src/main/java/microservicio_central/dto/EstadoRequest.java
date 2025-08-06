package microservicio_central.dto;

public class EstadoRequest {
    private String estado;
    private String facturaId;

    // Constructores
    public EstadoRequest() {}

    public EstadoRequest(String estado, String facturaId) {
        this.estado = estado;
        this.facturaId = facturaId;
    }

    // Getters y Setters
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFacturaId() { return facturaId; }
    public void setFacturaId(String facturaId) { this.facturaId = facturaId; }
}
