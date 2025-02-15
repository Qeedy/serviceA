package com.microservice.serviceA.controller;

import com.microservice.serviceA.client.BookingClient;
import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
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

    @GetMapping("/get-booking-list")
    public ResponseEntity<Page<BookingListModel>> getBookingList(
            @RequestParam(defaultValue = "") String search,
            @RequestHeader("userId") String userId,
            @RequestHeader("role") String role,
            Pageable pageable) {
        return ResponseEntity.ok(bookingClient
                .getBookingList(UUID.fromString(userId), search,
                        "ADMIN".equals(role), pageable));
    }

    @GetMapping("/get-revenue")
    public ResponseEntity<BigDecimal> getRevenueByMonth() {
        return ResponseEntity.ok(bookingClient.getRevenue());
    }

    @GetMapping("/get-total-bookings")
    public ResponseEntity<Integer> getTotalBookings() {
        return ResponseEntity.ok(bookingClient.getTotalBookings());
    }

    @GetMapping("/get-transaction-history")
    public ResponseEntity<Map<String, Object>> getTransactionHistory() {
        return ResponseEntity.ok(bookingClient.getTransactionHistory());
    }
}
