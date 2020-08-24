package com.socgen.rabbit.qpid.config;

import org.apache.qpid.server.SystemLauncher;
import org.apache.qpid.server.model.SystemConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.io.Files.createTempDir;
import static java.io.File.separator;


public class EmbededQpidBroker {


    private final Logger log = LoggerFactory.getLogger(getClass());
    private final SystemLauncher brokerLauncher;

    private String configJsonUrl = null;
    private final int amqpPort;

    public EmbededQpidBroker(
            int amqpPort,
            String vhost,
            int managementPort,
            String username,
            String password,
            Resource sourceConfigFile,
            String applicationLogLevel,
            String bindingAddress) throws IOException {
        final String tempDir = createTempDir().getAbsolutePath();
        final File configFile = new File(tempDir + separator + sourceConfigFile.getFilename());
        FileCopyUtils.copy(sourceConfigFile.getInputStream(), new FileOutputStream(configFile));
        this.amqpPort = amqpPort;
        System.setProperty("qpid.amqp_port", amqpPort + "");
        System.setProperty("qpid.http_port", managementPort + "");
        System.setProperty("qpid.vhost", vhost);
        System.setProperty("qpid.user.name", username);
        System.setProperty("qpid.user.password", password);
        System.setProperty("qpid.work_dir", tempDir);
        System.setProperty("application.logger.level", applicationLogLevel);
        System.setProperty("qpid.bindingAddress", bindingAddress);
        configJsonUrl = configFile.getAbsolutePath();
        brokerLauncher = new SystemLauncher();
    }


    public void start(){
        log.info("Starting QPID Broker...");

        try{
            brokerLauncher.startup(createSystemConfig());
        }catch (Exception e){
            throw new RuntimeException("Failed to start embedded QPID broker!", e);
        }
        log.info("Successfully Started QPID Broker...");
    }

    public void shutdown(){
        log.info("Shutting down broker...");
        brokerLauncher.shutdown();
    }


    private Map<String, Object> createSystemConfig(){
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(SystemConfig.TYPE, "JSON");
        if(configJsonUrl != null && !configJsonUrl.isEmpty()){
            attributes.put(SystemConfig.INITIAL_CONFIGURATION_LOCATION, configJsonUrl);
        }
        attributes.put(SystemConfig.STARTUP_LOGGED_TO_SYSTEM_OUT, false);
        return attributes;
    }
}

