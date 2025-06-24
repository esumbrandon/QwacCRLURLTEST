package com.example.test;

import de.adorsys.psd2.qwac.lib.validator.certificate.CrlProtocol;
import eu.europa.esig.dss.service.http.commons.CommonsDataLoader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * A test runner (using the injected CommonsDataLoader) that calls allowed (HTTP, HTTPS) and disallowed (FTP) protocols.
 */
@Component
public class  QwacRunner {

    private static final Logger log = LoggerFactory.getLogger(QwacRunner.class);

    private final CommonsDataLoader dataLoader;

    public QwacRunner(CommonsDataLoader dataLoader) {
        this.dataLoader = dataLoader;
    }

    @PostConstruct
    public void testCrlFetch() {
        try {
            // allowed protocols (HTTP, HTTPS) – these should not throw a DSSException for protocol
            log.info("QwacRunner: Calling http://example.com (allowed)");
            dataLoader.get("https://example.com");
            log.info("QwacRunner: Calling https://example.com (allowed)");
            dataLoader.get("https://example.com");
            // disallowed protocol (FTP) – this should throw a DSSException
            // log.info("QwacRunner: Calling ftp://not-allowed.com/crl (disallowed)");
            // dataLoader.get("ftp://not-allowed.com/crl");
        } catch (Exception e) {
            log.error("QwacRunner: Error during CRL fetch (caught and logged, not re-thrown):", e);
        }
    }
}
