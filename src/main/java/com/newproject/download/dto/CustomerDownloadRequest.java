package com.newproject.download.dto;

import jakarta.validation.constraints.NotBlank;

public class CustomerDownloadRequest {
    private Long orderId;

    @NotBlank
    private String name;

    @NotBlank
    private String filename;

    private Long sizeBytes;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public Long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(Long sizeBytes) { this.sizeBytes = sizeBytes; }
}
