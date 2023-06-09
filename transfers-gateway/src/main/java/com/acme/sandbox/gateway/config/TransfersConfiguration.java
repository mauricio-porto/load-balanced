package com.acme.sandbox.gateway.config;

import com.acme.sandbox.gateway.loadbalance.RandomBasedLoadBalancer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancerClientRequestTransformer;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.ClientRequest;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class TransfersConfiguration {

    private final TransfersWebClientConfig transfersWebClientConfig;

    @Bean
    @Primary
    ServiceInstanceListSupplier serviceInstanceListSupplier() {
        return new TransfersServiceInstanceListSupplier("transfers-gateway", transfersWebClientConfig);
    }

    @Bean
    ReactorLoadBalancer<ServiceInstance> randomLoadBalancer(Environment environment,
                                                            LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        return new RandomBasedLoadBalancer(loadBalancerClientFactory
                .getLazyProvider(name, ServiceInstanceListSupplier.class),
                name, transfersWebClientConfig.getRatio());
    }

    @Bean
    public LoadBalancerClientRequestTransformer transformer() {
        return (request, instance) -> {

            boolean experimental = instance.getInstanceId().contains("experimental");
            if (!experimental) {
                return request;
            }

            return ClientRequest.from(request)
                    .header("experimental", instance.getInstanceId())
                    .body(request.body())   // TODO: replace body
                    .url(request.url())
                    .build();
        };
    }
}
