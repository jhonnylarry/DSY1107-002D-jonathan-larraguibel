package cl.duoc.ecopunto.notificaciones.mensajeria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import cl.duoc.ecopunto.notificaciones.NotificacionService;
import cl.duoc.ecopunto.notificaciones.ReporteCreadoEvent;

@Component
public class ReporteCreadoListener {

    private static final Logger log = LoggerFactory.getLogger(ReporteCreadoListener.class);

    private final NotificacionService notificacionService;

    public ReporteCreadoListener(NotificacionService notificacionService) {
        this.notificacionService = notificacionService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void onReporteCreado(ReporteCreadoEvent event) {
        log.info("[CONSUMER] {} recibido desde cola {} eventId={}", event.tipo(), RabbitMQConfig.QUEUE,
                event.eventId());
        notificacionService.notificarEncargado(event);
    }
}
