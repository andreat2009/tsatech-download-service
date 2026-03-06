package com.newproject.download.controller;

import com.newproject.download.dto.CustomerDownloadRequest;
import com.newproject.download.dto.CustomerDownloadResponse;
import com.newproject.download.service.CustomerDownloadService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/{customerId}/downloads")
public class CustomerDownloadController {
    private final CustomerDownloadService service;

    public CustomerDownloadController(CustomerDownloadService service) {
        this.service = service;
    }

    @GetMapping
    public List<CustomerDownloadResponse> list(@PathVariable Long customerId) {
        return service.list(customerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDownloadResponse create(
        @PathVariable Long customerId,
        @Valid @RequestBody CustomerDownloadRequest request
    ) {
        return service.create(customerId, request);
    }
}
