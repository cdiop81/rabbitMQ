package org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer.model;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Result {
    private List<String> parameters = new ArrayList<>();
}
