package com.trimblecars.service.impl;

import com.trimblecars.entity.Car;
import com.trimblecars.enums.CarStatus;
import com.trimblecars.exception.CarNotFoundException;
import com.trimblecars.exception.UserNotFoundException;
import com.trimblecars.entity.User;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.UserRepository;
import com.trimblecars.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;

        @Override
        public Car registerCar(Car car, Long ownerId) {
            User owner = userRepository.findById(ownerId)
                    .orElseThrow(() -> new UserNotFoundException(ownerId));

            car.setOwner(owner);
            car.setStatus(CarStatus.IDLE);
            return carRepository.save(car);
        }

        @Override
        public List<Car> getAllCars() {
            return carRepository.findAll();
        }

        @Override
        public List<Car> getCarsByStatus(CarStatus status) {
            return carRepository.findAll().stream()
                    .filter(car -> car.getStatus() == status)
                    .toList();
        }

        @Override
        public List<Car> getCarsByOwner(Long ownerId) {
            return carRepository.findAll().stream()
                    .filter(car -> car.getOwner().getId().equals(ownerId))
                    .toList();
        }

        @Override
        public Car getCarById(Long id) {
            return carRepository.findById(id)
                    .orElseThrow(() -> new CarNotFoundException(id));
        }
}
