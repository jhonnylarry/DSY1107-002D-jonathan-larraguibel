package cl.duoc.ecopunto.reportes.dominio;

import java.time.Instant;
import java.util.UUID;

/**
 * Evento de dominio: "se registró un reporte". Lleva los datos del punto
 * (nombre, comuna) para que los consumers no tengan que consultar a ms-puntos-limpios.
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

    public static ReporteCreadoEvent desde(Reporte reporte, String puntoNombre, String comuna) {
        return new ReporteCreadoEvent(UUID.randomUUID().toString(), "ReporteCreado", Instant.now(),
                reporte.id(), reporte.puntoLimpioId(), puntoNombre, comuna, reporte.material(),
                reporte.descripcion(), reporte.autorEmail());
    }
}
