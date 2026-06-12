package com.newproject.download.service;

import com.newproject.download.domain.CustomerDownload;
import com.newproject.download.dto.CustomerDownloadRequest;
import com.newproject.download.dto.CustomerDownloadResponse;
import com.newproject.download.events.EventPublisher;
import com.newproject.download.repository.CustomerDownloadRepository;
import com.newproject.download.security.RequestActor;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerDownloadService {
    private final CustomerDownloadRepository repository;
    private final EventPublisher eventPublisher;
    private final RequestActor requestActor;

    public CustomerDownloadService(CustomerDownloadRepository repository, EventPublisher eventPublisher, RequestActor requestActor) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
        this.requestActor = requestActor;
    }

    @Transactional(readOnly = true)
    public List<CustomerDownloadResponse> list(Long customerId) {
        // SECURITY (H3): un utente autenticato puo' leggere solo i propri download (IDOR fix).
        requestActor.assertCustomerAccessIfAuthenticated(customerId);
        return repository.findByCustomerIdOrderByCreatedAtDesc(customerId)
            .stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public CustomerDownloadResponse create(Long customerId, CustomerDownloadRequest request) {
        // SECURITY (H3): emettere un entitlement di download e' un'operazione amministrativa /
        // di fulfillment, mai self-service del cliente. Senza questo controllo chiunque poteva
        // forgiare download digitali per qualsiasi customerId.
        requestActor.assertAdmin();
        CustomerDownload download = new CustomerDownload();
        download.setCustomerId(customerId);
        download.setOrderId(request.getOrderId());
        download.setName(request.getName());
        download.setFilename(request.getFilename());
        download.setSizeBytes(request.getSizeBytes());
        download.setCreatedAt(OffsetDateTime.now());

        CustomerDownload saved = repository.save(download);
        CustomerDownloadResponse response = toResponse(saved);
        eventPublisher.publish("CUSTOMER_DOWNLOAD_CREATED", "customer_download", saved.getId().toString(), response);
        return response;
    }

    private CustomerDownloadResponse toResponse(CustomerDownload download) {
        CustomerDownloadResponse response = new CustomerDownloadResponse();
        response.setId(download.getId());
        response.setCustomerId(download.getCustomerId());
        response.setOrderId(download.getOrderId());
        response.setName(download.getName());
        response.setFilename(download.getFilename());
        response.setSizeBytes(download.getSizeBytes());
        response.setCreatedAt(download.getCreatedAt());
        return response;
    }
}
