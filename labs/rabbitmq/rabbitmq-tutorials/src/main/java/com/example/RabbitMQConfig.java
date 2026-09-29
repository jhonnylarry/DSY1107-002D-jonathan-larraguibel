package com.example;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de RabbitMQ.
 *
 * Basada en el tutorial de Spring AMQP y su documentación oficial.
 */
@Configuration
public class RabbitMQConfig {

    /**
     * Define la cola "hello".
     *
     * La cola es idempotente: si no existe, se crea; si ya existe, se reutiliza.
     * Al ser no durable, se elimina si RabbitMQ se reinicia.
     */
    @Bean
    public Queue helloQueue() {
        return new Queue("hello", false);
    }
}
