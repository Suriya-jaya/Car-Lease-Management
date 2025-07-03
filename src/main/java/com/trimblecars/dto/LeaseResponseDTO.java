package com.trimblecars.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeaseResponseDTO {
    private Long leaseId;
    private Long carId;
    private String carModel;
    private Long customerId;
    private String customerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
