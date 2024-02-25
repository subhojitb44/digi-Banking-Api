package org.ewallet.gateway.configuration.security;

import lombok.extern.slf4j.Slf4j;
import org.ewallet.gateway.dto.AppUserDto;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component @Slf4j
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final WebClient.Builder webClientBuilder;

    public AuthFilter(WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                log.info("authorization  contains no token");
            }

            String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String[] parts = authHeader.split(" ");
            if (parts.length !=2 || !"Bearer".equals(parts[0])){
                log.info("Unauthorized to access the requested resources , check the token sent if contains [ Bearer ] and if it's correct");
            }

            return webClientBuilder.build()
                    .get()
                    .uri("http://ewallet-authentication-service-v1/api/authenticate/validate-token?token=" + parts[1])
                    .retrieve().bodyToMono(AppUserDto.class)
                    .map(appUserDto -> {
                        exchange.getRequest()
                                .mutate()
                                .header("X-auth-user-id", String.valueOf(appUserDto.getId()));
                        log.info("Exchange Gateway Principle {}",exchange.getPrincipal());
                        return exchange;
                    }).flatMap(chain::filter);
        };
    }

    public static class Config{

    }
}