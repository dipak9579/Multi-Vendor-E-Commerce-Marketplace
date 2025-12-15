package com.marketplace.apigateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;

@Configuration
@EnableWebFluxSecurity   // ✅ THIS IS REQUIRED
public class WebFluxSecurityEnableConfig {
}
