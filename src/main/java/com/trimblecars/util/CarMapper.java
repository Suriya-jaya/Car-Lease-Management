package com.trimblecars.util;

import com.trimblecars.dto.CarDTO;
import com.trimblecars.entity.Car;
import com.trimblecars.entity.User;

public class CarMapper {

    public static CarDTO toDTO(Car car) {
        CarDTO dto = new CarDTO();
        dto.setId(car.getId());
        dto.setModel(car.getModel());
        dto.setVariant(car.getVariant());
        dto.setRegistrationNumber(car.getRegistrationNumber());
        dto.setStatus(car.getStatus());
        dto.setOwnerId(car.getOwner() != null ? car.getOwner().getId() : null);
        return dto;
    }

    public static Car toEntity(CarDTO dto, User owner) {
        return Car.builder()
                .id(dto.getId())
                .model(dto.getModel())
                .variant(dto.getVariant())
                .registrationNumber(dto.getRegistrationNumber())
                .status(dto.getStatus())
                .owner(owner)
                .build();
    }
}
