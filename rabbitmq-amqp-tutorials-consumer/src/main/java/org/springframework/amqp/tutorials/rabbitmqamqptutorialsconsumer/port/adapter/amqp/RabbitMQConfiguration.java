package org.springframework.amqp.tutorials.rabbitmqamqptutorialsconsumer.port.adapter.amqp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.jackson.datatype.VavrModule;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.postprocessor.GUnzipPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    @Bean
    FanoutExchange myFanOut() {
        return new FanoutExchange("FAN_OUT_EXCHANGE");
    }

    @Bean
    Queue myQueue() {
        return new Queue("MY_QUEUE");
    }

    @Bean
    Binding queueBinding() {
        return BindingBuilder.bind(myQueue()).to(myFanOut());
    }

    @Bean
    Module vavrModule() {
        return new VavrModule();
    }

    @Bean
    MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        objectMapper.registerModule(new VavrModule());
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(CachingConnectionFactory factory, ObjectMapper objectMapper) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory() {
            @Override
            protected SimpleMessageListenerContainer createContainerInstance() {
                SimpleMessageListenerContainer listenerContainer = super.createContainerInstance();
                listenerContainer.setAfterReceivePostProcessors(new GUnzipPostProcessor());
                return listenerContainer;
            }
        };
        containerFactory.setConnectionFactory(factory);
        containerFactory.setMessageConverter(jsonMessageConverter(objectMapper));
        return containerFactory;
    }
}
