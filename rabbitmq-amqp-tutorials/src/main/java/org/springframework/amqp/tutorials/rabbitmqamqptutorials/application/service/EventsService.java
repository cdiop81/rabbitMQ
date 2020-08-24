package org.springframework.amqp.tutorials.rabbitmqamqptutorials.application.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.tutorials.rabbitmqamqptutorials.domain.model.Result;
import org.springframework.amqp.tutorials.rabbitmqamqptutorials.port.adapter.amqp.MessageBrokerCommunicator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventsService {

    @Getter
    private final MessageBrokerCommunicator messageBrokerCommunicator;

    public void triggerEvents(Result result) {
        try {
            messageBrokerCommunicator.send("FAN_OUT_EXCHANGE", result);
        } catch (RuntimeException re) {
            throw new IllegalArgumentException("RuntimeException while sending messages to the queue", re);
        }
    }
}
