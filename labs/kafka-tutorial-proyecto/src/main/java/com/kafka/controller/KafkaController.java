package com.kafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST que envía mensajes a Kafka
 *
 * Demuestra dos conceptos:
 *   - Sin Key: Round Robin (distribuye entre particiones 0, 1, 2...)
 *   - Con Key: Sticky Partitioning (todos a la misma partición)
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public KafkaController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Endpoint para enviar mensajes a Kafka
     *
     * Ejemplos de uso:
     *
     * SIN KEY (Round Robin - distribuye entre particiones):
     * GET http://localhost:8888/api/kafka/send?message=Mensaje1
     *
     * CON KEY (Sticky Partitioning - misma partición):
     * GET http://localhost:8888/api/kafka/send?message=Mensaje1&key=orden-555
     */
    @GetMapping("/send")
    public String sendMessage(
            @RequestParam String message,
            @RequestParam(required = false) String key) {

        String topicName = "tutorial-particiones";

        if (key != null && !key.isEmpty()) {
            // Con Key: Kafka calcula hash(key) para determinar partición
            // Todos los mensajes con la misma key van a la MISMA partición
            kafkaTemplate.send(topicName, key, message);
            return "Mensaje enviado al topic '" + topicName
                    + "' con KEY: " + key
                    + " | MENSAJE: " + message;
        } else {
            // Sin Key: Kafka usa Round Robin
            // Distribuye automáticamente entre particiones 0, 1, 2
            kafkaTemplate.send(topicName, message);
            return "Mensaje enviado al topic '" + topicName
                    + "' (sin key - distribución automática) | MENSAJE: " + message;
        }
    }

    /**
     * Endpoint de verificación
     */
    @GetMapping("/health")
    public String health() {
        return "Aplicación de Kafka Tutorial está corriendo en puerto 8888";
    }

}
