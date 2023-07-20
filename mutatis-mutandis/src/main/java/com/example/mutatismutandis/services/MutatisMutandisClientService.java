package com.example.mutatismutandis.services;

import com.example.mutatismutandis.payloads.TransferResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class MutatisMutandisClientService {
    public Mono<TransferResponse> initiateTransfer() {
        return WebClient.builder()
                .build()
                .get()
                .uri("http://localhost:8090/dizai")
                .retrieve()
                .bodyToMono(TransferResponse.class)
                .flatMap(transferResponse -> {
                    Long instructionId = transferResponse.getInstructionId();
                    log.warn("eilo: {}", instructionId);
                    return null;
                });
    }

    public String initiateTransferString() {
        return WebClient.builder()
                .build()
                .get()
                .uri("http://localhost:8090/dizai")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
