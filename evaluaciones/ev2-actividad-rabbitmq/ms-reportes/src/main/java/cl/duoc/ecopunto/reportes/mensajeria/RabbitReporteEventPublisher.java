package cl.duoc.ecopunto.reportes.mensajeria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import cl.duoc.ecopunto.reportes.dominio.ReporteCreadoEvent;
import cl.duoc.ecopunto.reportes.dominio.ReporteEventPublisher;

@Component
public class RabbitReporteEventPublisher implements ReporteEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(RabbitReporteEventPublisher.class);

    private final RabbitTemplate rabbitTemplate;

    public RabbitReporteEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publicar(ReporteCreadoEvent event) {
        // Los mensajes se publican como persistentes (delivery mode por defecto de Spring AMQP).
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_REPORTE_CREADO, event);
        log.info("[ASYNC] publicado {} eventId={} -> exchange={} routingKey={}", event.tipo(), event.eventId(),
                RabbitMQConfig.EXCHANGE, RabbitMQConfig.RK_REPORTE_CREADO);
    }
}
