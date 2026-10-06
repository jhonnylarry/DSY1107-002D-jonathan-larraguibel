package cl.duoc.ecopunto.estadisticas.mensajeria;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Suscripción de ms-estadisticas: le interesa CUALQUIER evento de reporte (reporte.*),
 * así un futuro reporte.resuelto llega sin modificar el producer.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "ecopunto.events";
    public static final String QUEUE = "estadisticas.reporte-creado";
    public static final String BINDING_KEY = "reporte.*";

    @Bean
    public TopicExchange ecopuntoEventsExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue estadisticasQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding estadisticasBinding(Queue estadisticasQueue, TopicExchange ecopuntoEventsExchange) {
        return BindingBuilder.bind(estadisticasQueue).to(ecopuntoEventsExchange).with(BINDING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
