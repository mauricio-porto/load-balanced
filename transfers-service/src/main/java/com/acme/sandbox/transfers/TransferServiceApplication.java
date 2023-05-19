package com.acme.sandbox.transfers;

import com.acme.sandbox.transfers.payloads.Transfer;
import com.acme.sandbox.transfers.payloads.TransferResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@SpringBootApplication
public class TransferServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(TransferServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(TransferServiceApplication.class, args);
    }

    @GetMapping("/xfer")
    public String xfer() {
        log.info("Will transfer");

        return "Transferred ";
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/transfers",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TransferResponse> backToDell(
            @RequestHeader Map<String, String> headers,
            @RequestBody Transfer transfer) {

        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });

        log.info("Received Transfer={}", transfer);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TransferResponse.builder()
                        .assetId(1234567L)
                        .instructionId(987654321L)
                        .build());
    }

    @GetMapping("/")
    public String smokeTest() {
        log.info("Smoke test");
        return "Hi!";
    }
}
