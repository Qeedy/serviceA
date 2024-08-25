package com.microservice.serviceA.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.microservice.serviceA.client.TransactionClient;
import com.microservice.serviceA.entity.Customer;
import com.microservice.serviceA.entity.Item;
import com.microservice.serviceA.entity.Transaction;
import com.microservice.serviceA.model.TransactionDataModel;
import com.microservice.serviceA.model.TransactionModel;
import com.microservice.serviceA.repository.CustomerRepository;
import com.microservice.serviceA.repository.TransactionRepository;
import com.microservice.serviceA.service.TransactionService;
import com.microservice.serviceA.util.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionClient transactionClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    @Transactional
    public Transaction createTransaction(Transaction transaction) throws JsonProcessingException {
        Customer cust = customerRepository.findByUsername(JwtUtil.getCurrentUsername());
        BigDecimal totalCost = transaction.getItems().stream().map(Item::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal customerChange = cust.getBalance().subtract(totalCost);
        transaction.setCustomerChange(customerChange);
        transaction.setLastBalance(cust.getBalance());
        transaction.setCustomer(cust);
        transaction.setTotalCost(totalCost);
        cust.setBalance(customerChange);
        Transaction response = transactionRepository.save(transaction);
        customerRepository.save(cust);
        TransactionDataModel model = constructTransactionDataModel(response, cust);
        String transactionData = objectMapper.writeValueAsString(model);
        kafkaTemplate.send("transaction", transactionData);
        return response;
    }

    @Override
    public TransactionModel getTransactionGistory(LocalDate startDate, LocalDate endDate, String category) {
        return transactionClient.getTransactionHistory(startDate, endDate, category);
    }

    @Override
    public InputStreamResource getTransactionHistoryPdf(LocalDate startDate, LocalDate endDate, String category) {
        TransactionModel data = transactionClient.getTransactionHistory(startDate, endDate, category);
        Document document = new Document();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            PdfPTable table = new PdfPTable(8);
            addTableHeader(table, "Transaction Id", "Customer ID", "Customer Name",
                    "List Item Name", "Customer Change", "Total Cost",
                    "Customer Old Balance", "Customer New Balance");
            addRow(table, "1", "C001", "John Doe", "Item1, Item2",
                    "$10", "$100", "$500", "$400");
            data.getDatas().stream().forEach(e -> {
                addRow(table, e.getTransactionId().toString(), e.getCustomerId().toString(),
                        e.getCustomerName(), String.join(", ", e.getItemNames()),
                        e.getCustomerChange().toPlainString(),
                        e.getTotalCost().toPlainString(),
                        e.getCustomerOldBalance().toPlainString(),
                        e.getCustomerNewBalance().toPlainString());
            });
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        return new InputStreamResource(byteArrayInputStream);
    }

    @Override
    public void sendEmail(String receiver) {
        transactionClient.sendEmailTransaction(receiver);
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new com.itextpdf.text.Phrase(header));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }

    private void addRow(PdfPTable table, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell();
            cell.setPhrase(new com.itextpdf.text.Phrase(value));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private static TransactionDataModel constructTransactionDataModel(Transaction response, Customer cust) {
        List<String> itemNames = response.getItems().stream().map(Item::getItemName).toList();
        Set<String> categoryNames = response.getItems().stream().map(e -> e.getCategory().getCategoryName()).collect(Collectors.toSet());
        TransactionDataModel model = TransactionDataModel.builder()
                .transactionId(response.getId())
                .customerId(cust.getId())
                .customerChange(response.getCustomerChange())
                .customerName(cust.getCustomerName())
                .itemNames(itemNames)
                .totalCost(response.getTotalCost())
                .customerOldBalance(response.getLastBalance())
                .customerNewBalance(response.getCustomerChange())
                .categoryNames(categoryNames)
                .build();
        return model;
    }
}
