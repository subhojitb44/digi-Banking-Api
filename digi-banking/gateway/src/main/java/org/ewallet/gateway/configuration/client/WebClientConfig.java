package org.ewallet.gateway.configuration.client;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
// The WebClient.Builder is a builder that is used to create instances of the WebClient
// class which is a non-blocking HTTP client that can be used to make HTTP requests.
// By creating a load-balanced WebClient.Builder instance, the application can use the Spring Cloud load balancing infrastructure to distribute the requests to multiple instances of the same service.
public class WebClientConfig {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}