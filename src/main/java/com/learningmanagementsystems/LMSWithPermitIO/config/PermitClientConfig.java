package com.learningmanagementsystems.LMSWithPermitIO.config;

import io.permit.sdk.Permit;
import io.permit.sdk.PermitConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermitClientConfig {

    @Value("${permit.api-key}")
    private String apiKey;

    @Value("${permit.pdp-url}")
    private String pdpUrl;

    @Bean
    public Permit permit() {
        return new Permit(
                new PermitConfig.Builder(apiKey)
                        .withPdpAddress(pdpUrl)
                        .build()
        );
    }
}
