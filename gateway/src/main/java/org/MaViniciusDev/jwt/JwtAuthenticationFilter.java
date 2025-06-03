package org.MaViniciusDev.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    @Override
    public int getOrder(){
        return -1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchage, GatewayFilterChain chain) {
        String path = exchage.getRequest().getURI().getPath();

        if (path.startsWith("/api/v1/auth") || path.startsWith("/api/v1/registration/")) {
            return chain.filter(exchage);
        }
    }
}
