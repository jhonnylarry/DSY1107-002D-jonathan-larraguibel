package cl.duoc.ecopunto.estadisticas;

import java.time.Instant;

/**
 * Copia local del contrato del evento. Cada servicio define su propia clase:
 * lo compartido es el JSON, no el código.
 */
public record ReporteCreadoEvent(
        String eventId,
        String tipo,
        Instant ocurridoEn,
        Long reporteId,
        Long puntoLimpioId,
        String puntoNombre,
        String comuna,
        String material,
        String descripcion,
        String autorEmail) {
}
