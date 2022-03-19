package com.marcode.customer;

import com.marcode.clients.fraud.FraudCheckResponse;
import com.marcode.clients.fraud.FraudClient;
import com.marcode.clients.notification.NotificationClient;
import com.marcode.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        RestTemplate restTemplate,
        FraudClient fraudClient,
        NotificationClient notificationClient){

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        // todo: check if email is valid
        // todo: check if email is not taken
        customerRepository.saveAndFlush(customer);

        // old way
       /* FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId());
        */

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());
        if(fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster found");  //Todo: implement good message or status
        } else {
            NotificationRequest notificationSendResponse = notificationClient.sendNotification(
                    new NotificationRequest(
                            customer.getId(),
                            customer.getEmail(),
                            String.format("Hi %s, welcome to Marcode ...", customer.getFirstName())
                    )
            );
        }

    }
}
