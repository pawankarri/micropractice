package com.spring.user.service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.LoadBalancedExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MyConfig {
//
@Bean
//@LoadBalanced
public WebClient webClient(LoadBalancedExchangeFilterFunction loadBalancedExchangeFilterFunction) {

    return WebClient.builder().filter(loadBalancedExchangeFilterFunction).build();
}
}
