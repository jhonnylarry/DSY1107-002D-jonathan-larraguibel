package com.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Productor (Sender) que envía mensajes a la cola "hello".
 */
@Component
public class Sender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * Envía un mensaje simple a la cola "hello" usando el exchange por defecto.
	 *
	 * @param message contenido del mensaje
	 */
	public void sendMessage(String message) {
		try {
			String timestamp = LocalDateTime.now()
					.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
			String fullMessage = String.format("[%s] %s", timestamp, message);

			rabbitTemplate.convertAndSend("hello", fullMessage);
			System.out.println("[OK] Mensaje enviado: '" + fullMessage + "'");
		} catch (Exception exception) {
			System.err.println("[ERROR] Error enviando mensaje: " + exception.getMessage());
			exception.printStackTrace();
		}
	}

	/**
	 * Envía un mensaje usando un exchange y una routing key explícitos.
	 *
	 * @param exchange exchange de destino
	 * @param routingKey routing key de destino
	 * @param message contenido del mensaje
	 */
	public void sendMessage(String exchange, String routingKey, String message) {
		try {
			rabbitTemplate.convertAndSend(exchange, routingKey, message);
			System.out.println("[OK] Mensaje enviado a exchange='" + exchange
					+ "', routingKey='" + routingKey + "': '" + message + "'");
		} catch (Exception exception) {
			System.err.println("[ERROR] Error enviando mensaje: " + exception.getMessage());
			exception.printStackTrace();
		}
	}
}
