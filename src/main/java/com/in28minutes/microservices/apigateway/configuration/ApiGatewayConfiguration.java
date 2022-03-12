package com.in28minutes.microservices.apigateway.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(predicateSpec -> {
                return predicateSpec.path("/get") // if request is sent to '/get'
                    .filters(
                        gatewayFilterSpec -> gatewayFilterSpec // filter the result in between,
                            // we are adding
                            // some headers
                            .addRequestHeader("MyHeader", "MyURI"))
                    .uri("http://httpbin.org:80"); // redirect it to given url.
            })
            .route(p -> p.path("/currency-exchange/**").uri("lb://currency-exchange"))
            .route(p -> p.path("/currency-conversion/**").uri("lb://currency-conversion"))
            .route(p -> p.path("/currency-conversion-feign/**").uri("lb://currency-conversion"))
            .route(p -> p.path("/currency-conversion-new/**")
                .filters(f -> f.rewritePath(
                    "/currency-conversion-new/(?<segment>.*)",
                    "/currency-conversion-feign/${segment}"
                ))
                .uri("lb://currency-conversion"))
            .build();
    }

}
