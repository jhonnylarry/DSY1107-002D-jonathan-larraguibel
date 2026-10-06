package cl.duoc.ecopunto.puntoslimpios;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/puntos-limpios")
public class PuntoLimpioController {

    private static final Logger log = LoggerFactory.getLogger(PuntoLimpioController.class);

    private final PuntoLimpioRepository repository;

    public PuntoLimpioController(PuntoLimpioRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Collection<PuntoLimpio> listar() {
        return repository.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuntoLimpio> buscar(@PathVariable Long id) {
        return ResponseEntity.of(repository.buscar(id));
    }

    /**
     * Consulta síncrona usada por ms-reportes antes de registrar un reporte:
     * ¿existe el punto y acepta (o aceptaba) este material?
     */
    @GetMapping("/{id}/materiales/{material}")
    public ResponseEntity<ValidacionMaterial> validarMaterial(
            @PathVariable Long id, @PathVariable String material) {
        return repository.buscar(id)
                .map(punto -> {
                    boolean aceptado = punto.acepta(material);
                    log.info("[VALIDACION] punto={} material={} aceptado={}", id, material, aceptado);
                    return ResponseEntity.ok(new ValidacionMaterial(
                            punto.id(), punto.nombre(), punto.comuna(), material.toUpperCase(), aceptado));
                })
                .orElseGet(() -> {
                    log.info("[VALIDACION] punto={} no existe", id);
                    return ResponseEntity.notFound().build();
                });
    }

    public record ValidacionMaterial(
            Long puntoLimpioId, String nombre, String comuna, String material, boolean aceptado) {
    }
}
