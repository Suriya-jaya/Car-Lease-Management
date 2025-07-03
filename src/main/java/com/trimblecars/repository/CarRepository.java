package com.trimblecars.repository;

import com.trimblecars.entity.Car;
import com.trimblecars.enums.CarStatus;
import com.trimblecars.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByStatus(CarStatus status);

    List<Car> findByOwner(User owner);
}
