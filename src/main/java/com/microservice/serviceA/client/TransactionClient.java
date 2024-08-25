package com.microservice.serviceA.client;


import com.microservice.serviceA.model.TransactionModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@FeignClient(name = "transaction-service", url = "http://localhost:8081")
public interface TransactionClient {
    @GetMapping("/transaction/get-transaction")
    public TransactionModel getTransactionHistory(@RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String category);
    @GetMapping("/transaction/send-email-transaction")
    public void sendEmailTransaction(@RequestParam String receiver);
}
