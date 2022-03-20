package com.marcode.customer;

import com.marcode.amqp.RabbitMQMessageProducer;
import com.marcode.clients.fraud.FraudCheckResponse;
import com.marcode.clients.fraud.FraudClient;
import com.marcode.clients.notification.NotificationClient;
import com.marcode.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        //RestTemplate restTemplate, //no more needed
        FraudClient fraudClient,
        //NotificationClient notificationClient, //no more needed
        RabbitMQMessageProducer rabbitMQMessageProducer
    ){

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
            /*  no more needed
                notificationClient.sendNotification(
                    new NotificationRequest(
                            customer.getId(),
                            customer.getEmail(),
                            String.format("Hi %s, welcome to Marcode ...", customer.getFirstName())
                    )
            );*/

            //Send async message. i.e add to queue
            NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Marcode ...", customer.getFirstName())
            );
            rabbitMQMessageProducer.publish(
                    notificationRequest,
                    "internal.exchange",
                    "internal.notification.routing-key"
                    );
        }

    }
}
