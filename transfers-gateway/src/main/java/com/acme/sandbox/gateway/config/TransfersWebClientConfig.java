package com.acme.sandbox.gateway.config;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "transfers-gateway")
@LoadBalancerClient(name = "transfers-gateway", configuration = TransfersConfiguration.class)
public class TransfersWebClientConfig {
    private double ratio = 0.0;
    private List<Routes> routes;

    public Optional<Route> getRouteByName(String name) {
        return routes.stream()
                .filter(routes -> routes.getRoute().getName().equalsIgnoreCase(name))
                .findFirst()
                .map(Routes::getRoute);
    }
}

@Data
class Routes {
    private Route route;
}

@Data
@Builder
class Route {
    private String name;
    private String host;
    private int port;
    private String url;
}
