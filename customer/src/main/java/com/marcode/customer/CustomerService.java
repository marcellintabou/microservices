package com.marcode.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(){

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.firstName())
                .email(customerRegistrationRequest.email())
                .build();
        // todo: check if email is valid
        // todo: check if email is not taken
        // todo: store the customer in db

    }
}
