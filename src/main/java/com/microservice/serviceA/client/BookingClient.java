package com.microservice.serviceA.client;


import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@FeignClient(name = "booking-service", url = "http://localhost:8082")
public interface BookingClient {
    @GetMapping("/booking/detail/{bookingNumber}")
    public BookingDetailModel getBookingDetail(@PathVariable String bookingNumber);
    @GetMapping("/booking/get-booking-list")
    public Page<BookingListModel> getBookingList(
            @RequestParam UUID userId,
            @RequestParam String search,
            @RequestParam(name = "isAdmin", defaultValue = "false") Boolean isAdmin,
            Pageable pageable);
    @GetMapping("/booking/get-revenue")
    public BigDecimal getRevenue();
    @GetMapping("/booking/get-total-bookings")
    public Integer getTotalBookings();
    @GetMapping("/booking/get-transaction-history")
    public Map<String, Object> getTransactionHistory();
}
