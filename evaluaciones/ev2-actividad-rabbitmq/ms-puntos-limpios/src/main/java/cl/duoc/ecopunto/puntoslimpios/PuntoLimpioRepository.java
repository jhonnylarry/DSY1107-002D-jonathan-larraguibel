package cl.duoc.ecopunto.puntoslimpios;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

/**
 * Repositorio en memoria con los mismos puntos de prueba que usa EcoPunto.
 */
@Repository
public class PuntoLimpioRepository {

    private final Map<Long, PuntoLimpio> puntos = new TreeMap<>();

    public PuntoLimpioRepository() {
        guardar(new PuntoLimpio(1L, "Punto Limpio Ñuñoa", "Av. Irarrázaval 3000", "Ñuñoa",
                List.of("PAPEL", "CARTON", "PLASTICO", "VIDRIO")));
        guardar(new PuntoLimpio(2L, "Punto Limpio Providencia", "Av. Providencia 1500", "Providencia",
                List.of("VIDRIO", "LATAS", "PILAS")));
    }

    private void guardar(PuntoLimpio punto) {
        puntos.put(punto.id(), punto);
    }

    public Collection<PuntoLimpio> listar() {
        return puntos.values();
    }

    public Optional<PuntoLimpio> buscar(Long id) {
        return Optional.ofNullable(puntos.get(id));
    }
}
