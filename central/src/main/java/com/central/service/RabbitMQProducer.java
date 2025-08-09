package com.central.service;

import com.central.event.CosechaEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.nueva_cosecha}")
    private String queueName;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendNuevaCosechaEvent(CosechaEvent event) {
        rabbitTemplate.convertAndSend(queueName, event);
    }
}
