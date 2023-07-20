package com.example.mutatismutandis;

import com.example.mutatismutandis.payloads.TransferResponse;
import com.example.mutatismutandis.services.MutatisMutandisClientService;
import com.example.mutatismutandis.utils.WebClientResponseHook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@SpringBootApplication
public class MutatisMutandisApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutatisMutandisApplication.class, args);
	}

	private final MutatisMutandisClientService mutatisMutandisClientService;

	@RequestMapping("/dizai")
	public Mono<String> dizai() {

		var responseHook = ExchangeFilterFunction
				.ofResponseProcessor(WebClientResponseHook::exchangeFilterResponseHook);

		return WebClient.builder()
				.filters(exchangeFilterFunctions -> {
					exchangeFilterFunctions.add(responseHook);
				})
				.build().get().uri("http://localhost:8090/dizai")
				.retrieve().bodyToMono(String.class)
				.map(result -> String.format("Ei-lo: %s!", result));
	}

	@RequestMapping("/vaila")
	public Mono<TransferResponse> vaila() {
		return mutatisMutandisClientService.initiateTransfer();
//		return Mono.just(mutatisMutandisClientService.initiateTransferString());
	}

}
