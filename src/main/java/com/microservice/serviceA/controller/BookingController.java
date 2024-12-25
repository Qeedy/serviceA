package com.microservice.serviceA.controller;

import com.microservice.serviceA.client.BookingClient;
import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingClient bookingClient;

    @GetMapping("/detail/{bookingNumber}")
    public ResponseEntity<BookingDetailModel> getBookingDetail(@PathVariable String bookingNumber){
        return ResponseEntity.ok(bookingClient.getBookingDetail(bookingNumber));
    }

    @GetMapping("/booking/get-booking-list")
    public ResponseEntity<Page<BookingListModel>> getBookingList(
            @RequestHeader("userId") String userId,
            @RequestHeader("role") String role,
            Pageable pageable) {
        return ResponseEntity.ok(bookingClient
                .getBookingList(UUID.fromString(userId),
                        "ADMIN".equals(role), pageable));
    }
}
