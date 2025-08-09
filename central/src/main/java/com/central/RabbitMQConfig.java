package com.central;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue nuevaCosechaQueue() {
        return new Queue("nueva_cosecha", true);
    }
}
