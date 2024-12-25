package com.microservice.serviceA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservice.serviceA.enums.ServiceTime;
import com.microservice.serviceA.enums.ServiceType;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookingModel {
    private UUID customerId;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ServiceType serviceType;
    private String address;
    @JsonFormat(pattern = "MM-dd")
    private LocalDate bookingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ServiceTime serviceTime;
    private String instruction;
}
