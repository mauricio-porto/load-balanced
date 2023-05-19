package com.acme.sandbox.gateway;

import com.acme.sandbox.gateway.payloads.Transfer;
import com.acme.sandbox.gateway.payloads.TransferResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@SpringBootApplication
public class TransferClientApplication {
    private final ReactorLoadBalancerExchangeFilterFunction lbFunction;

    public TransferClientApplication(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.lbFunction = lbFunction;
    }

    public static void main(String[] args) {
        SpringApplication.run(TransferClientApplication.class, args);
    }

    @RequestMapping("/xfer")
    public Mono<String> xfer(@RequestParam(value = "asset", defaultValue = "VR46DUC") String asset) {
        return WebClient.builder()
                .filter(lbFunction)
                .build().get().uri("http://transfers-gateway/xfer")
                .retrieve().bodyToMono(String.class)
                .map(xferResult -> String.format("%s, %s!", xferResult, asset));
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/transfers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TransferResponse> transfer(
            @RequestBody Transfer transfer) {

        return WebClient.builder()
                .filter(lbFunction)
                .build().post()
                .uri("http://transfers-gateway/transfers")
                .bodyValue(transfer)
                .retrieve()
                .bodyToMono(TransferResponse.class);

    }
}
