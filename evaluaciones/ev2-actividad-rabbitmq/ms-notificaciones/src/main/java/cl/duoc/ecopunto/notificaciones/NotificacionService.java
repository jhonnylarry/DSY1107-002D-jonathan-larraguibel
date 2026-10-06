package cl.duoc.ecopunto.notificaciones;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Lógica de negocio: avisar al encargado del punto limpio.
 * El envío de correo se simula con un log.
 */
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);

    public void notificarEncargado(ReporteCreadoEvent event) {
        log.info("[NOTIFICACION] Para: encargado de \"{}\" ({}) | Asunto: nuevo reporte #{} | "
                + "El material {} ya no se estaría recibiendo. Detalle: \"{}\" (reportado por {})",
                event.puntoNombre(), event.comuna(), event.reporteId(), event.material(),
                event.descripcion(), event.autorEmail());
    }
}
