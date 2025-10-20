package com.bytetrio.api.model;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;


@Configuration
public class Api {
    
    private RouterFunction<ServerResponse> post(String name, String initialName, String finalName) {
        return route(name).POST(finalName, http()).before(uri(initialName)).build();
    }

    private RouterFunction<ServerResponse> get(String name, String initialName, String finalName) {
        return route(name).GET(finalName, http()).before(uri(initialName)).build();
    }

    private RouterFunction<ServerResponse> delete(String name, String initialName, String finalName) {
        return route(name).DELETE(finalName, http()).before(uri(initialName)).build();
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return post("authentication", "http://authentication:8081", "/user/**")
            .and(
                post("customer-post", "http://customer:8082", "/customer/**")
            ).and(
                get("customer-get", "http://customer:8082", "/customer/**")
            ).and(
                delete("customer-delete", "http://customer:8082", "/customer/**")
            ).and(
                post("financial-post", "http://financial:8082", "/financial/**")
            ).and(
                get("financial-get", "http://financial:8082", "/financial/**")
            ).and(
                delete("financial-delete", "http://financial:8082", "/financial/**")
            );
    }

}
