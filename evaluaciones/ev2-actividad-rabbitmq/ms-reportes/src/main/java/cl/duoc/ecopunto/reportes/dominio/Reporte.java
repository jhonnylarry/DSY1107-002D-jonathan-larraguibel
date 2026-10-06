package cl.duoc.ecopunto.reportes.dominio;

import java.time.Instant;

public record Reporte(
        Long id,
        Long puntoLimpioId,
        String material,
        String descripcion,
        String autorEmail,
        String estado,
        Instant creadoEn) {
}
