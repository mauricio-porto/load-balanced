package com.acme.sandbox.gateway.config;

import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

public class TransfersServiceInstanceListSupplier implements ServiceInstanceListSupplier {

    private final String serviceId;

    public TransfersServiceInstanceListSupplier(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getServiceId() {
        return serviceId;
    }

    @Override
    public Flux<List<ServiceInstance>> get() {
        return Flux.just(Arrays
                .asList(new DefaultServiceInstance(serviceId + "-default", serviceId, "localhost", 8090, false),
                        new DefaultServiceInstance(serviceId + "-experimental", serviceId, "localhost", 9090, false)));
    }
}
