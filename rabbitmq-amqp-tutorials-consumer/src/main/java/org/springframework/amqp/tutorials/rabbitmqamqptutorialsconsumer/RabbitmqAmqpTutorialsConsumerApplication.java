package org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class RabbitmqAmqpTutorialsConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqAmqpTutorialsConsumerApplication.class, args);
	}

}
