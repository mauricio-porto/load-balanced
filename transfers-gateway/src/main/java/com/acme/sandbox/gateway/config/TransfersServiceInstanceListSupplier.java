package com.acme.sandbox.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class TransfersServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;
    private final TransfersWebClientConfig transfersWebClientConfig;

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        var defaultRoute = transfersWebClientConfig.getRouteByName("default")
                .orElseGet(() -> Route.builder().host("localhost").port(8090).build());
        var experimentalRoute = transfersWebClientConfig.getRouteByName("experimental")
                .orElseGet(() -> Route.builder().host("localhost").port(9090).build());
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "-default",
                                serviceId,
                                defaultRoute.getHost(),
                                defaultRoute.getPort(),
                                false),
                        new DefaultServiceInstance(serviceId + "-experimental",
                                serviceId,
                                experimentalRoute.getHost(),
                                experimentalRoute.getPort(),
                                false)));
    }
}
