package org.springframework.amqp.tutorials.rabbitmqamqptutorials.port.adapter.amqp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Profile("!h2")
@Slf4j
public class RabitMQCommunicator implements MessageBrokerCommunicator {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void send(String queueName, Object messageObject) {
        log.info("RabbitMQReceiver send Start");
        rabbitTemplate.convertAndSend(queueName, messageObject);
        log.info("Queue which is getting publish is :" + queueName);
        log.info("RabbitMQReceiver send End");

    }
}
