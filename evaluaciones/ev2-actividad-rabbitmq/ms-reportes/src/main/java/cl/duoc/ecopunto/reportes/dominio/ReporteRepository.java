package cl.duoc.ecopunto.reportes.dominio;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

@Repository
public class ReporteRepository {

    private final Map<Long, Reporte> reportes = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public Reporte guardar(Reporte nuevo) {
        Reporte reporte = new Reporte(secuencia.incrementAndGet(), nuevo.puntoLimpioId(), nuevo.material(),
                nuevo.descripcion(), nuevo.autorEmail(), nuevo.estado(), nuevo.creadoEn());
        reportes.put(reporte.id(), reporte);
        return reporte;
    }

    public Collection<Reporte> listar() {
        return reportes.values();
    }
}
