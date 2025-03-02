package com.microservice.serviceA.controller;

import com.microservice.serviceA.client.BookingClient;
import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import com.microservice.serviceA.service.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;

@RestController
@RequestMapping("/booking")
public class BookingController {
    @Autowired
    BookingClient bookingClient;
    @Autowired
    ReportService reportService;

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

    @GetMapping("/report/{bookingNumber}")
    public ResponseEntity<byte[]> generateBookingDetailReport(@PathVariable("bookingNumber") String bookingNumber) throws Exception {
        BookingDetailModel data = bookingClient.getBookingDetail(bookingNumber);
        byte[] reportPdf = reportService.generateReportDetail(data);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("inline; filename=%s.pdf", bookingNumber));
        headers.add("Content-Type", "application/pdf");
        return new ResponseEntity<>(reportPdf, headers, HttpStatus.OK);
    }
    @GetMapping("/report")
    public ResponseEntity<byte[]> generateBookingListReport(
            @RequestParam(required = false) String status,
            @RequestParam String dateRange,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo) throws Exception {
        List<BookingListModel> data = bookingClient.getReportBookings(status, dateRange, dateFrom, dateTo);
        String period = constructPeriod(dateRange, dateTo, dateFrom);
        byte[] reportPdf = reportService.generateReportList(data, period);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("inline; filename=booking_list.pdf"));
        headers.add("Content-Type", "application/pdf");
        return new ResponseEntity<>(reportPdf, headers, HttpStatus.OK);
    }

    @GetMapping("/report/preview")
    public ResponseEntity<Page<BookingListModel>> getReportPreview(
            @RequestParam(required = false) String status,
            @RequestParam String dateRange,
            @RequestParam(required = false) LocalDateTime dateFrom,
            @RequestParam(required = false) LocalDateTime dateTo,
            Pageable pageable) throws Exception {
        return ResponseEntity.ok(bookingClient.getReportBookingsPreview(
                status, dateRange, dateFrom, dateTo, pageable));
    }

    public String constructPeriod(String dateRange, LocalDateTime dateto, LocalDateTime dateFrom) {
        Map<String, Supplier<String>> periodMap = Map.of(
                "TODAY", () -> "TODAY",
                "WEEKLY", () -> "WEEKLY",
                "MONTHLY", () -> "MONTHLY",
                "YEARLY", () -> "YEARLY",
                "CUSTOM_DATE", () -> String.format("From %s To %s",
                        dateFrom.toLocalDate(),
                        dateto.toLocalDate())
        );
        return periodMap.getOrDefault(dateRange.toUpperCase(),
                () -> "ALL"
        ).get();
    }
}
