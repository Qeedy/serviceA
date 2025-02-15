package com.microservice.serviceA.client;

import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import com.microservice.serviceA.model.BookingTaskModel;
import com.microservice.serviceA.model.CreateBookingModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "task-service", url = "http://localhost:8082")
public interface TaskClient {
    @GetMapping("/task/detail/{bookingNumber}")
    public BookingDetailModel getBookingDetail(
            @PathVariable String bookingNumber);
    @GetMapping("/task/get-task-list")
    public Page<BookingTaskModel> getBookingList(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam UUID userId,
            @RequestParam(name = "isAdmin", defaultValue = "false") Boolean isAdmin,
            Pageable pageable);
    @PostMapping("/task/create")
    public String createTask(@RequestBody CreateBookingModel model);
    @PostMapping("/task/process-task/{bookingNumber}")
    public void processTask(
            @PathVariable String bookingNumber,
            @RequestBody Map<String, Object> variables);
    @GetMapping("/task/get-total-task")
    public Integer getTotalTask();
}
