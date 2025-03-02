package com.microservice.serviceA.controller;

import com.microservice.serviceA.client.ServiceClient;
import com.microservice.serviceA.model.ServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    private ServiceClient serviceClient;

    @GetMapping("/list")
    public ResponseEntity<Page<ServiceModel>> getServices(
            @RequestParam(value = "keyword", required = false) String keyword,
            @PageableDefault(page = 0, size = 5) Pageable pageable) {
        return ResponseEntity.ok(serviceClient.getListService(keyword, pageable));
    }

    @GetMapping("/get-by-type")
    public ResponseEntity<List<ServiceModel>> getServiceByType(
            @RequestParam("serviceType") String serviceType) {
        return ResponseEntity.ok(serviceClient.getServiceByType(serviceType));
    }

    @PostMapping
    public ResponseEntity<ServiceModel> saveService(@RequestBody ServiceModel service){
        return ResponseEntity.ok(serviceClient.saveService(service));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable("id") UUID id){
        serviceClient.deleteService(id);
        return ResponseEntity.ok().build();
    }
}
