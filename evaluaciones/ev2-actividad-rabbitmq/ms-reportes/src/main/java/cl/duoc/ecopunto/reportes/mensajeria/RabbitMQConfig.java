package cl.duoc.ecopunto.reportes.mensajeria;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Infraestructura de mensajería del producer: solo declara el exchange.
 * Las colas y bindings los declara cada consumer, porque cada uno decide qué eventos le interesan.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "ecopunto.events";
    public static final String RK_REPORTE_CREADO = "reporte.creado";

    @Bean
    public TopicExchange ecopuntoEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false); // durable, sin auto-delete
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
