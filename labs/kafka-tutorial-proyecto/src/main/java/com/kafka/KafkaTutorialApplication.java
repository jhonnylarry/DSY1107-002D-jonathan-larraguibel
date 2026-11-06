package com.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación Spring Boot para tutorial de Kafka
 * Demuestra cómo funcionan topics, particiones y offsets
 */
@SpringBootApplication
public class KafkaTutorialApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaTutorialApplication.class, args);
    }

}
