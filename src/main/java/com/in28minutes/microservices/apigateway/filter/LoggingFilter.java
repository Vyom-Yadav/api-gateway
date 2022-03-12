package com.in28minutes.microservices.apigateway.filter;

import java.util.logging.Logger;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter implements GlobalFilter {

    private final Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        logger.info(
            "Path of request received -> {" + exchange.getRequest().getPath().value() + "}");
        return chain.filter(exchange);
    }
}
