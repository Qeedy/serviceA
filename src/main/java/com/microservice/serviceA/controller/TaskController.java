package com.microservice.serviceA.controller;


import com.microservice.serviceA.client.TaskClient;
import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import com.microservice.serviceA.model.CreateBookingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskClient taskClient;

    @GetMapping("/detail/{bookingNumber}")
    public ResponseEntity<BookingDetailModel> getBookingDetail(
            @PathVariable String bookingNumber){
        return ResponseEntity.ok(taskClient
                .getBookingDetail(bookingNumber));
    }

    @GetMapping("/task/{userId}")
    public ResponseEntity<Page<BookingListModel>> getBookingList(
            @RequestHeader("userId") String userId,
            @RequestHeader("role") String role,
            Pageable pageable){
        return ResponseEntity.ok(taskClient
                .getBookingList(UUID.fromString(userId),
                        "ADMIN".equals(role), pageable));
    }

    @PostMapping("/task/create")
    public ResponseEntity<String> createTask(
            @RequestBody CreateBookingModel model) {
        return ResponseEntity.ok(taskClient.createTask(model));
    }

    @PostMapping("/task/process-task/{bookingNumber}")
    public ResponseEntity<Void> processTask(
            @PathVariable String bookingNumber,
            @RequestBody Map<String, Object> variables) {
        taskClient.processTask(bookingNumber, variables);
        return ResponseEntity.ok().build();
    }
}
