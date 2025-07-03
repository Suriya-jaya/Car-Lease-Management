package com.trimblecars.dto;

import com.trimblecars.enums.CarStatus;
import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    private String model;
    private String variant;
    private String registrationNumber;
    private CarStatus status;
    private Long ownerId;
}
