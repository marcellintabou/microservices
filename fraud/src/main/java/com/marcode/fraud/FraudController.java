package com.marcode.fraud;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/fraud-check")
public class FraudController {

    private final FraudCheckService fraudCheckService;

    @PostMapping(path = "{customerId}")
    public FraudCheckresponse isFraudster(@PathVariable("customerId") Integer customerId){

        return new FraudCheckresponse(fraudCheckService.isFraudulentCustomer(customerId));
    }
}
