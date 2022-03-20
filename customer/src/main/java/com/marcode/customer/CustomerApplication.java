package com.marcode.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        scanBasePackages = {
            "com.marcode.customer",
            "com.marcode.amqp"     //This allows injecting the configurated producer
        }
)
@EnableEurekaClient
@EnableFeignClients(
        basePackages = "com.marcode.clients"
)
public class CustomerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);

    }
}
