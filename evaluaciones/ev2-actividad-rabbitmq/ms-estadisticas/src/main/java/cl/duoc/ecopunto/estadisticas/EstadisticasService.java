package cl.duoc.ecopunto.estadisticas;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Lógica de negocio: contar reportes por comuna y por material.
 */
@Service
public class EstadisticasService {

    private static final Logger log = LoggerFactory.getLogger(EstadisticasService.class);

    private final AtomicLong total = new AtomicLong();
    private final Map<String, AtomicLong> porComuna = new ConcurrentHashMap<>();
    private final Map<String, AtomicLong> porMaterial = new ConcurrentHashMap<>();

    public void registrar(ReporteCreadoEvent event) {
        long nuevoTotal = total.incrementAndGet();
        long comuna = porComuna.computeIfAbsent(event.comuna(), k -> new AtomicLong()).incrementAndGet();
        long material = porMaterial.computeIfAbsent(event.material(), k -> new AtomicLong()).incrementAndGet();
        log.info("[ESTADISTICAS] total={} | {}={} | {}={}", nuevoTotal, event.comuna(), comuna,
                event.material(), material);
    }

    public Resumen resumen() {
        return new Resumen(total.get(), copia(porComuna), copia(porMaterial));
    }

    private static Map<String, Long> copia(Map<String, AtomicLong> mapa) {
        Map<String, Long> resultado = new TreeMap<>();
        mapa.forEach((clave, valor) -> resultado.put(clave, valor.get()));
        return resultado;
    }

    public record Resumen(long totalReportes, Map<String, Long> porComuna, Map<String, Long> porMaterial) {
    }
}
