package org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer.model.Result;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsumerService {

    @RabbitListener(queues = "MY_QUEUE")
    public void run(Result result) {
        log.info("Request received for exposure ProcessId " + result.toString());
    }
}
