package com.spring.gateway.filter;

import com.spring.gateway.config.JwtUtil;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.SetPathGatewayFilterFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config>
{
//    @Autowired
//    private WebClient client;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RouteValidator routeValidator;
    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                {
                    throw new RuntimeException("missing authorization header");
                }
                String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                if (authHeader!=null && authHeader.startsWith("Bearer "))
                {
                    authHeader=authHeader.substring(7);
                }
                try {
                // String ur= client.get().uri("http://localhost:8086/auth/validate?token="+authHeader).retrieve().bodyToMono(String.class).block();
                    jwtUtil.validateToken(authHeader);
                }

                catch (Exception e) {
                    System.out.println("invalid access ");
                    throw new RuntimeException("unauthorized access to application");
                }
            }

            return chain.filter(exchange);
        });
    }

    public static class Config{

   }


}
