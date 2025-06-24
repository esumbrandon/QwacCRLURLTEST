package com.example.test;

import eu.europa.esig.dss.model.DSSException;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyCustomDataLoader extends CommonsDataLoader {

    private static final Logger log = LoggerFactory.getLogger(MyCustomDataLoader.class);
    private static final List<String> DEFAULT_PROTOCOLS = Arrays.asList("FILE", "HTTP", "HTTPS", "FTP");

    private final List<String> allowedProtocols;

    public MyCustomDataLoader(List<String> allowedProtocols) {
        // If no protocols are configured, use the default set [FILE, HTTP, HTTPS, FTP]
        this.allowedProtocols = (allowedProtocols == null || allowedProtocols.isEmpty()) 
            ? DEFAULT_PROTOCOLS 
            : allowedProtocols;
        
        log.info("Using ProtocolFilteringDataLoader with allowed protocols: {}", this.allowedProtocols);
        log.info("CRL protocol filter enabled. Allowed protocols: {}", this.allowedProtocols);
    }

    @Override
    public byte[] get(String urlString) {
        Objects.requireNonNull(urlString, "URL string cannot be null");
        try {
            URL url = new URL(urlString);
            String protocol = url.getProtocol().toUpperCase();
            
            if (allowedProtocols.stream().anyMatch(p -> p.equalsIgnoreCase(protocol))) {
                log.debug("Fetching CRL from allowed URL: {}", urlString);
                return super.get(urlString);
            } else {
                log.warn("Blocked CRL fetch due to unsupported protocol: {}", protocol);
                throw new DSSException("Protocol not allowed: " + protocol);
            }
        } catch (Exception e) {
            log.error("Error fetching CRL from URL: {}", urlString, e);
            throw new DSSException("Error fetching CRL: " + urlString, e);
        }
    }
} 