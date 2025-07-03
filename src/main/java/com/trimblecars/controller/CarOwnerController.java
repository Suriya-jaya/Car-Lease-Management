package com.trimblecars.controller;

import com.trimblecars.dto.CarDTO;
import com.trimblecars.dto.LeaseResponseDTO;
import com.trimblecars.entity.Car;
import com.trimblecars.service.CarService;
import com.trimblecars.service.LeaseService;
import com.trimblecars.util.CarMapper;
import com.trimblecars.util.LeaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Car Owner", description = "Operations for Car Owners to manage cars")
@RestController
@RequestMapping("/owner")
@RequiredArgsConstructor
public class CarOwnerController {

    private final CarService carService;
    private final LeaseService leaseService;

    @Operation(summary = "Register a car for lease")
    @PostMapping("/{ownerId}/register-car")
    public CarDTO registerCar(@RequestBody CarDTO carDTO, @PathVariable Long ownerId) {
        Car car = carService.registerCar(CarMapper.toEntity(carDTO, null), ownerId);
        return CarMapper.toDTO(car);
    }

    @Operation(summary = "Get all cars for the owner")
    @GetMapping("/{ownerId}/cars")
    public List<CarDTO> getCarsByOwner(@PathVariable Long ownerId) {
        return carService.getCarsByOwner(ownerId).stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/car/{carId}/status")
    public CarDTO getCarDetails(@PathVariable Long carId) {
        return CarMapper.toDTO(carService.getCarById(carId));
    }

    @Operation(summary = "Get lease history of a car")
    @GetMapping("/car/{carId}/lease-history")
    public List<LeaseResponseDTO> getLeaseHistory(@PathVariable Long carId) {
        return leaseService.getLeaseHistoryByCar(carId).stream()
                .map(LeaseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
