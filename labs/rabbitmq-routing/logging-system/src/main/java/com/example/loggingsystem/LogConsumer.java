package com.example.loggingsystem;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * Consumidores: uno por cola. No conocen el exchange ni las routing keys.
 */
@Service
public class LogConsumer {

	@RabbitListener(queues = RabbitMQConfig.ALL_LOGS_QUEUE)
	public void receiveAllLogs(String message) {
		System.out.println("[MONITOR GENERAL] Log recibido: " + message);
	}

	@RabbitListener(queues = RabbitMQConfig.ERRORS_ONLY_QUEUE)
	public void receiveErrorLogs(String message) {
		// Simular una alerta
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		System.out.println("[ALERTA CRÍTICA] Error detectado: " + message);
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}
}
