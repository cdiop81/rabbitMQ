package com.socgen.rabbit.qpid.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;

@Configuration
public class EmbeddedQpidBrokerConfiguration {

    @Value("${qpid.embedded.port:8002}")
    private int amqpPort;

    @Value("${qpid.embedded.management.port:8085}")
    private int httpPort;

    @Value(("${qpid.embedded.vhost:default}"))
    private String virtualHost;

    @Value(("${qpid.embedded.user.name:guest}"))
    private String username;

    @Value(("${qpid.embedded.user.password:guest}"))
    private String password;

    @Value("classpath:qpid/initial-config.json")
    private Resource sourceConfigFile;

    @Value("${application.logger.level}")
    private String applicationLoggerLevel;

    private EmbededQpidBroker broker;
    @Value("${qpid.embedded.bindingAddress}")
    private String bindingAddress;

    public EmbeddedQpidBrokerConfiguration() {  }


    @PostConstruct
    public void startBroker() throws IOException {
        broker = new EmbededQpidBroker(
                amqpPort,
                virtualHost,
                httpPort,
                username,
                password,
                sourceConfigFile,
                applicationLoggerLevel,
                bindingAddress);
        broker.start();
    }

    @PreDestroy
    public void shutdown(){
        broker.shutdown();
    }


}
