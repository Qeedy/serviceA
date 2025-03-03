package com.microservice.serviceA.service.impl;

import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import com.microservice.serviceA.service.ReportService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public byte[] generateReportDetail(BookingDetailModel data)
            throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        DecimalFormat df = new DecimalFormat("###,###.00");
        parameters.put("logoPath", "classpath:/static/logo.png"); // Path logo di resources/static
        parameters.put("bookingNumber", data.getBookingId());
        parameters.put("customerName", data.getCustomerName());
        parameters.put("serviceType", data.getServiceType().name());
        parameters.put("serviceName", data.getServiceName());
        parameters.put("bookingDate", data.getBookingDateTime());
        parameters.put("technicianName", data.getTechnitionName());
        parameters.put("status", data.getBookingStatus().name());
        parameters.put("address", data.getLocation());
        parameters.put("instruction", data.getInstructions());
        parameters.put("customerPhoneNumber", data.getCustomerPhoneNumber());
        parameters.put("customerEmail", data.getCustomerEmail());
        parameters.put("customerAddress", data.getCustomerAddress());
        parameters.put("paymentMethod", data.getPaymentMethod());
        parameters.put("totalCost", df.format(
                Optional.ofNullable(data.getTotalCost())
                        .orElse(BigDecimal.ZERO)));
        String jrxmlPath = "reports/booking_detail.jrxml";
        String jasperPath = "reports/booking_detail.jasper";
        compileJrxmlToJasper(jrxmlPath, jasperPath);
        InputStream reportStream = new ClassPathResource("reports/booking_detail.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void compileJrxmlToJasper(String jrxmlPath, String jasperPath) throws Exception {
        InputStream jrxmlStream = new ClassPathResource(jrxmlPath).getInputStream();
        File tempJrxml = Files.createTempFile("temp_jasper_report", ".jrxml").toFile();
        Files.copy(jrxmlStream, tempJrxml.toPath(), StandardCopyOption.REPLACE_EXISTING);
        JasperCompileManager.compileReportToFile(tempJrxml.getAbsolutePath(), "src/main/resources/" + jasperPath);
        System.out.println("✔ Jasper file berhasil dikompilasi ulang dengan JasperReports 7.0.1!");
    }

    @Override
    public byte[] generateReportList(List<BookingListModel> data, String period) throws Exception {
        String jrxmlPath = "reports/booking_list.jrxml";
        String jasperPath = "reports/booking_list.jasper";
        compileJrxmlToJasper(jrxmlPath, jasperPath);
        InputStream reportStream = new ClassPathResource("reports/booking_list.jasper").getInputStream();
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportStream);
        List<Map<String, Object>> bookingDataList = new ArrayList<>();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (BookingListModel booking : data) {
            Map<String, Object> bookingMap = new HashMap<>();
            bookingMap.put("bookingId", booking.getBookingId());
            bookingMap.put("bookingDate", booking.getBookingDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            bookingMap.put("serviceTime", booking.getServiceTime().name());
            bookingMap.put("serviceType", booking.getServiceType().name());
            bookingMap.put("status", booking.getBookingStatus().name());
            bookingMap.put("customerName", booking.getCustomerName());
            bookingMap.put("technicianName", booking.getTechnicianName());
            bookingMap.put("createdDate", booking.getCreatedDate().format(dateFormat));

            bookingDataList.add(bookingMap);
        }
        JRDataSource dataSource = new JRBeanCollectionDataSource(bookingDataList);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoPath", "classpath:/static/logo.png");
        parameters.put("period", period != null ? period : "All Time");
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
