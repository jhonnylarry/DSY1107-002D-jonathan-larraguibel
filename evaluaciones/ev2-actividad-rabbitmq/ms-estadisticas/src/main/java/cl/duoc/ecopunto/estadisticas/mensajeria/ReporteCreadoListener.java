package cl.duoc.ecopunto.estadisticas.mensajeria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import cl.duoc.ecopunto.estadisticas.EstadisticasService;
import cl.duoc.ecopunto.estadisticas.ReporteCreadoEvent;

@Component
public class ReporteCreadoListener {

    private static final Logger log = LoggerFactory.getLogger(ReporteCreadoListener.class);

    private final EstadisticasService estadisticasService;

    public ReporteCreadoListener(EstadisticasService estadisticasService) {
        this.estadisticasService = estadisticasService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void onReporteCreado(ReporteCreadoEvent event) {
        log.info("[CONSUMER] {} recibido desde cola {} eventId={}", event.tipo(), RabbitMQConfig.QUEUE,
                event.eventId());
        estadisticasService.registrar(event);
    }
}
