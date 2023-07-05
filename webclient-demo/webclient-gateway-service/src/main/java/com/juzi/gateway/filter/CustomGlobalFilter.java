package com.juzi.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        HttpHeaders headers = request.getHeaders();
        String name = headers.getFirst("name");
        WebClient webClient = WebClient.builder().defaultHeader("Content-Type", "application/json").build();
        Mono<String> stringMono = webClient.get().uri("http://localhost:8090/user/name?name=" + name)
                .retrieve().bodyToMono(String.class);
        stringMono.subscribe(log::info);

        return stringMono.flatMap(responseBody -> {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().add("Content-Type", "application/json");
            response.setStatusCode(HttpStatus.OK);
            byte[] responseBytes = responseBody.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = response.bufferFactory().wrap(responseBytes);
            return response.writeWith(Mono.just(buffer));
        }).then(chain.filter(exchange));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}