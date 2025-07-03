package com.trimblecars.dto;

import lombok.Data;

@Data
public class LeaseRequestDTO {
    private Long carId;
    private Long customerId;
}
