package com.microservice.serviceA.service;

import com.microservice.serviceA.model.BookingDetailModel;
import com.microservice.serviceA.model.BookingListModel;
import net.sf.jasperreports.engine.JRException;

import java.io.IOException;
import java.util.List;

public interface ReportService {
    public byte[] generateReportDetail(BookingDetailModel data) throws Exception;
    public byte[] generateReportList(
            List<BookingListModel> data,
            String period) throws Exception;
}
