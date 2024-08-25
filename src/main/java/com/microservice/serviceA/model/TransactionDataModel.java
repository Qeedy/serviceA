package com.microservice.serviceA.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDataModel {
    private UUID transactionId;
    private UUID customerId;
    private String customerName;
    private List<String> itemNames;
    private BigDecimal customerChange;
    private BigDecimal totalCost;
    private BigDecimal customerOldBalance;
    private BigDecimal customerNewBalance;
    private Set<String> categoryNames;
}
