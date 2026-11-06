package com.kafka.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

/**
 * Consumidor de Kafka que escucha el topic 'tutorial-particiones'
 *
 * Muestra en tiempo real:
 *   - Partición: En cuál de las 3 particiones llegó el mensaje
 *   - Offset: Posición secuencial dentro de esa partición
 *   - Key: La clave del mensaje (null si no se envió)
 *   - Mensaje: El contenido
 */
@Service
public class KafkaConsumer {

    /**
     * Escucha todos los mensajes del topic 'tutorial-particiones'
     *
     * Los parámetros @Header extraen metadatos de Kafka:
     * - RECEIVED_PARTITION: Número de partición (0, 1 o 2)
     * - OFFSET: Posición del mensaje en esa partición
     * - RECEIVED_KEY: La clave (puede ser null)
     */
    @KafkaListener(
            topics = "tutorial-particiones",
            groupId = "mi-grupo-educativo"
    )
    public void listen(
            @Payload String message,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            // Corrección a la guía: sin required = false, un mensaje sin key no trae este header
            // y el listener falla con "Missing header 'kafka_receivedMessageKey'".
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) String key) {

        // Imprimir información de manera clara y visual
        System.out.println("\n" + "=".repeat(100));
        System.out.printf(" MENSAJE RECIBIDO%n");
        System.out.printf(" 📍 Partición: %d%n", partition);
        System.out.printf(" Offset: %d%n", offset);
        System.out.printf(" Key: %s%n", key != null ? key : "(sin key)");
        System.out.printf(" Mensaje: %s%n", message);
        System.out.println("=".repeat(100) + "\n");
    }

}
