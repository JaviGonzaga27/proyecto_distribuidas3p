package microservicio_central.dto;

import java.time.LocalDateTime;

public class EventoCosecha {
    private String eventId;
    private String eventType;
    private LocalDateTime timestamp;
    private String cosechaId;
    private String producto;
    private Double toneladas;
    private String[] requiereInsumos;

    // Constructores
    public EventoCosecha() {}

    // Getters y Setters
    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public String getCosechaId() { return cosechaId; }
    public void setCosechaId(String cosechaId) { this.cosechaId = cosechaId; }

    public String getProducto() { return producto; }
    public void setProducto(String producto) { this.producto = producto; }

    public Double getToneladas() { return toneladas; }
    public void setToneladas(Double toneladas) { this.toneladas = toneladas; }

    public String[] getRequiereInsumos() { return requiereInsumos; }
    public void setRequiereInsumos(String[] requiereInsumos) { this.requiereInsumos = requiereInsumos; }
}
