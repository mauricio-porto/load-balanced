package com.acme.sandbox.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "transfers-gateway")
@LoadBalancerClient(name = "transfers-gateway", configuration = TransfersConfiguration.class)
public class TransfersWebClientConfig {
    private double ratio = 0.0;
}
