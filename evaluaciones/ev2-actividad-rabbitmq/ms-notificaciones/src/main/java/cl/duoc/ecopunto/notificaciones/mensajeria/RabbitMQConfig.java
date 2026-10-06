package cl.duoc.ecopunto.notificaciones.mensajeria;

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
 * Suscripción de ms-notificaciones: solo le interesa cuando se CREA un reporte.
 */
@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "ecopunto.events";
    public static final String QUEUE = "notificaciones.reporte-creado";
    public static final String BINDING_KEY = "reporte.creado";

    @Bean
    public TopicExchange ecopuntoEventsExchange() {
        // Declaración idempotente: el consumer puede arrancar antes que el producer.
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue notificacionesQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    @Bean
    public Binding notificacionesBinding(Queue notificacionesQueue, TopicExchange ecopuntoEventsExchange) {
        return BindingBuilder.bind(notificacionesQueue).to(ecopuntoEventsExchange).with(BINDING_KEY);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }
}
