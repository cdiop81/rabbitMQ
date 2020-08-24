package org.springframework.amqp.tutorials.rabbitmqamqptutorials.rest.endpoint;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.tutorials.rabbitmqamqptutorials.application.service.EventsService;
import org.springframework.amqp.tutorials.rabbitmqamqptutorials.domain.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@Slf4j
@RequiredArgsConstructor
public class EventController {

    private final EventsService eventsService;

    @ResponseStatus(ACCEPTED)
    @PostMapping(value = "/eventProcessing/v0", produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value = "This is the root API to process Exposures")
    public String eventProcessing(@RequestBody String parameters) {
        final Result result = new Result();
        result.getParameters().add(parameters);
        eventsService.triggerEvents(result);
        return "processId";
    }
}
