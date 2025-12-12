package com.dipak;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class HealthController {
    @GetMapping("/healthcheck")
    public Map<String, String> health() {
        return Map.of("status", "OK", "service", "api-gateway");
    }
}