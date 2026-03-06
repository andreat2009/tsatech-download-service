package com.newproject.download.repository;

import com.newproject.download.domain.CustomerDownload;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDownloadRepository extends JpaRepository<CustomerDownload, Long> {
    List<CustomerDownload> findByCustomerIdOrderByCreatedAtDesc(Long customerId);
}
