package com.example.mutatismutandis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientResponse;
import reactor.core.publisher.Mono;

@Slf4j
public class WebClientResponseHook {
    public static Mono<ClientResponse> exchangeFilterResponseHook(ClientResponse response) {
        log.warn("Eis o korpo: {}", response.bodyToMono(String.class));
        response.bodyToMono(String.class).doOnNext(body -> log.warn("onNext: {}", body)).subscribe();
        return Mono.just(response.mutate().body("MUTATIS MUTANDIS").build());
    }
}
