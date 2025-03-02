package com.microservice.serviceA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservice.serviceA.enums.ServiceType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceModel {
    private UUID uuid;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ServiceType serviceType;
    private String serviceName;
    private BigDecimal cost;
}
