package com.microservice.serviceA.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.microservice.serviceA.enums.BookingStatus;
import com.microservice.serviceA.enums.ServiceTime;
import com.microservice.serviceA.enums.ServiceType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingTaskModel {
    private String bookingId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookingDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ServiceTime serviceTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BookingStatus bookingStatus;
    private String customerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ServiceType serviceType;
    private String technicianName;
    private String taskName;
    private String taskId;
}

