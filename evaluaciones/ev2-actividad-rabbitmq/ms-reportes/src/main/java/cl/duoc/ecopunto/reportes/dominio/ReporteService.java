package cl.duoc.ecopunto.reportes.dominio;

import java.time.Instant;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cl.duoc.ecopunto.reportes.cliente.PuntosLimpiosClient;
import cl.duoc.ecopunto.reportes.cliente.PuntosLimpiosClient.ValidacionMaterial;

/**
 * Lógica de aplicación. No conoce RabbitMQ: depende del puerto ReporteEventPublisher.
 */
@Service
public class ReporteService {

    private static final Logger log = LoggerFactory.getLogger(ReporteService.class);

    private final PuntosLimpiosClient puntosLimpiosClient;
    private final ReporteRepository repository;
    private final ReporteEventPublisher eventPublisher;

    public ReporteService(PuntosLimpiosClient puntosLimpiosClient, ReporteRepository repository,
            ReporteEventPublisher eventPublisher) {
        this.puntosLimpiosClient = puntosLimpiosClient;
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Reporte crear(Long puntoLimpioId, String material, String descripcion, String autorEmail) {
        // 1. Comunicación síncrona: sin esta respuesta no se puede decidir si el reporte es válido.
        ValidacionMaterial validacion = puntosLimpiosClient.validarMaterial(puntoLimpioId, material);
        if (!validacion.aceptado()) {
            throw new ReporteInvalidoException("El punto " + validacion.nombre()
                    + " no recibe " + validacion.material() + ": no se puede reportar que dejó de recibirlo");
        }

        // 2. Procesamiento principal: registrar el reporte.
        Reporte reporte = repository.guardar(new Reporte(null, puntoLimpioId, validacion.material(),
                descripcion, autorEmail, "PENDIENTE", Instant.now()));
        log.info("[REPORTE] registrado id={} punto={} material={}", reporte.id(), puntoLimpioId,
                reporte.material());

        // 3. Comunicación asíncrona: informar que ocurrió, sin esperar a los interesados.
        eventPublisher.publicar(ReporteCreadoEvent.desde(reporte, validacion.nombre(), validacion.comuna()));
        return reporte;
    }

    public Collection<Reporte> listar() {
        return repository.listar();
    }
}
