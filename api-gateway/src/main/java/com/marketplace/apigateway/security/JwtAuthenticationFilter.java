package com.marketplace.apigateway.security;

import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.springframework.core.annotation.Order;


import java.util.List;

@Component
@Order(-1)
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        String method = exchange.getRequest().getMethod().name();

        // ✅ ALLOW CORS PREFLIGHT
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return chain.filter(exchange);
        }

        // 🔓 Public endpoints
        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = jwtUtil.validateToken(token);
            List<String> roles = jwtUtil.getRoles(claims);

            if (!isAuthorized(path, roles)) {
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            exchange = exchange.mutate()
                    .request(builder -> builder
                            .header("X-USER", claims.getSubject())
                            .header("X-ROLES", String.join(",", roles))
                    ).build();

        } catch (Exception e) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }


    private boolean isAuthorized(String path, List<String> roles) {

        // ADMIN APIs
        if (path.startsWith("/api/admin")) {
            return roles.contains("ADMIN");
        }

        // SELLER APIs
        if (path.startsWith("/api/sellers")
                || path.startsWith("/api/products")
                || path.startsWith("/api/inventory")) {
            return roles.contains("SELLER");
        }

        // CUSTOMER APIs
        if (path.startsWith("/api/cart")
                || path.startsWith("/api/orders")
                || path.startsWith("/api/payments")) {   // ✅ ADD THIS
            return roles.contains("CUSTOMER");
        }

        return false;
    }


}
