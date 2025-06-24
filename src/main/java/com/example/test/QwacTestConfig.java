package com.example.test;

import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
public class QwacTestConfig {
    private static final Logger log = LoggerFactory.getLogger(QwacTestConfig.class);
    private static final List<String> DEFAULT_PROTOCOLS = Arrays.asList("FILE", "HTTP", "HTTPS", "FTP", "LDAP");

    @Autowired
    private QwacProperties qwacProperties;

    @Bean
    public CommonsDataLoader crlDataLoader() {
        Properties props = null;
        try {
            props = ConfigLoader.loadYamlAsProperties("dss.yaml");
        } catch (Exception e) {
            log.warn("Could not load dss.yaml, using default protocols: {}", DEFAULT_PROTOCOLS);
            return new MyCustomDataLoader(DEFAULT_PROTOCOLS);
        }

        String protocolsStr = props != null ? props.getProperty("qwac.dss.dataloader.protocols") : null;
        log.info("dss.yaml (qwac.dss.dataloader.protocols) value: {}", protocolsStr);
        
        List<String> allowedProtocols;
        if (protocolsStr == null || protocolsStr.trim().isEmpty()) {
            log.info("No protocols configured in dss.yaml, using default protocols: {}", DEFAULT_PROTOCOLS);
            allowedProtocols = DEFAULT_PROTOCOLS;
        } else {
            allowedProtocols = Arrays.stream(protocolsStr.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
            log.info("Using configured protocols from dss.yaml: {}", allowedProtocols);
        }
        
        return new MyCustomDataLoader(allowedProtocols);
    }
}
