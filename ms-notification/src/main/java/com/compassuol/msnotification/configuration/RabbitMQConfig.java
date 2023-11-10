package com.compassuol.msnotification.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_NOTIFY_NAME = "users.v1.user-activity.get-notification";
    public static final String USER_EXCHANGE_NAME = "users.v1.user-activity";

    @Bean
    public Queue queueNotification() {
        return new Queue(QUEUE_NOTIFY_NAME,
                true, false, false, null);
    }

    @Bean
    public Binding binding() {
        Queue queue = new Queue(QUEUE_NOTIFY_NAME);
        TopicExchange exchange = new TopicExchange(USER_EXCHANGE_NAME);
        return BindingBuilder.bind(queue).to(exchange).with("routingJsonKey");
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> readyEventApplicationListener(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }
}
