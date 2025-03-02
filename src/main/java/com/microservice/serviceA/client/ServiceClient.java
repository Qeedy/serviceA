package com.microservice.serviceA.client;

import com.microservice.serviceA.model.ServiceModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "service-service", url = "${booking-service.url}")
public interface ServiceClient {
    @GetMapping("/service/list")
    public Page<ServiceModel> getListService(@RequestParam("keyword") String keyword, Pageable pageable);

    @GetMapping("/service/get-by-type")
    public List<ServiceModel> getServiceByType(
            @RequestParam("serviceType") String serviceType);

    @PostMapping("/service")
    public ServiceModel saveService(@RequestBody ServiceModel serviceModel);

    @DeleteMapping("/service/{id}")
    public void deleteService(@PathVariable("id") UUID id);
}
