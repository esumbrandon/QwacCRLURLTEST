package com.example.test;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "qwac.dss.dataloader")
public class QwacProperties {

        // Maps (qwac.dss.dataloader.protocols) from dss.yaml (or application.yaml) for Spring's config binding.
        private String protocols;
        // getters and setters (provided by lombok @Data)
}
