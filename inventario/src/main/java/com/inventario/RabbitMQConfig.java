package com.inventario;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue nuevaCosechaQueue() {
        return new Queue("nueva_cosecha", true);
    }
    @Bean
    public Queue inventarioAjustadoQueue() {
        return new Queue("inventario_ajustado", true);
    }
}
