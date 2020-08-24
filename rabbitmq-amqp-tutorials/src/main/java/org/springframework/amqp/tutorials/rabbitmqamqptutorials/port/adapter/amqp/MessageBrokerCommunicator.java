package org.springframework.amqp.tutorials.rabbitmqamqptutorials.port.adapter.amqp;

import org.springframework.stereotype.Component;

@Component
public interface MessageBrokerCommunicator {
    void send(String queueName, Object messageObject);
}
