package com.microservice.serviceA.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.microservice.serviceA.entity.Transaction;
import com.microservice.serviceA.model.TransactionModel;
import com.microservice.serviceA.service.CustomerService;
import com.microservice.serviceA.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable String id) {
        Optional<Transaction> transaction = transactionService.getTransactionById(id);
        return transaction.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction)
            throws JsonProcessingException {
        return ResponseEntity.ok(transactionService.createTransaction(transaction));
    }

    @GetMapping("/get-history")
    public ResponseEntity<TransactionModel> getTransactionHistory(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String category) {
        return ResponseEntity.ok(transactionService
                .getTransactionGistory(startDate, endDate, category));
    }

    @GetMapping("/generate-pdf")
    public ResponseEntity<InputStreamResource> generatePdf(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate,
            @RequestParam String category) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=transaction-history.pdf");
        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(transactionService
                        .getTransactionHistoryPdf(startDate, endDate, category));
    }

    @GetMapping("/send-email-transaction")
    public ResponseEntity<Void> sendEmail(@RequestParam String receiver) {
        transactionService.sendEmail(receiver);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
