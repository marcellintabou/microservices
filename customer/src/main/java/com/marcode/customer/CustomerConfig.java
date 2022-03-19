package com.marcode.customer;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerConfig {
    @Bean
    @LoadBalanced //send the request to either on or another FRAUD instance
    public RestTemplate retsTemplate(){
        return new RestTemplate();
    }
}
