package org.springframework.amqp.tutorials.rabbitmqamqptutorials.domain.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
@Getter
public class Result {
    private List<String> parameters = new ArrayList<>();
}
