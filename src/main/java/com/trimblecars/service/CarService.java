package com.trimblecars.service;

import com.trimblecars.entity.Car;
import com.trimblecars.enums.CarStatus;
import java.util.List;

public interface CarService {
    Car registerCar(Car car, Long ownerId);
    List<Car> getAllCars();
    List<Car> getCarsByStatus(CarStatus status);
    List<Car> getCarsByOwner(Long ownerId);
    Car getCarById(Long id);
}
