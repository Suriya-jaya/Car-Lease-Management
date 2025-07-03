package com.trimblecars.service.impl;

import com.trimblecars.entity.Car;
import com.trimblecars.enums.CarStatus;
import com.trimblecars.exception.BusinessRuleException;
import com.trimblecars.exception.CarNotFoundException;
import com.trimblecars.exception.LeaseNotFoundException;
import com.trimblecars.exception.UserNotFoundException;
import com.trimblecars.entity.Lease;
import com.trimblecars.entity.User;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.LeaseRepository;
import com.trimblecars.repository.UserRepository;
import com.trimblecars.service.LeaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaseServiceImpl implements LeaseService {

    private final LeaseRepository leaseRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    @Override
    public Lease startLease(Long carId, Long customerId) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new CarNotFoundException(carId));

        if (car.getStatus() != CarStatus.IDLE) {
            throw new BusinessRuleException("Car is not available for lease");
        }

        User customer = userRepository.findById(customerId)
                .orElseThrow(() -> new UserNotFoundException(customerId));

        long activeLeaseCount = leaseRepository.findAll().stream()
                .filter(l -> l.getCustomer().getId().equals(customerId))
                .filter(l -> l.getEndDate() == null)
                .count();

        if (activeLeaseCount >= 2) {
            throw new BusinessRuleException("Customer already has 2 active leases");
        }

        Lease lease = Lease.builder()
                .car(car)
                .customer(customer)
                .startDate(LocalDateTime.now())
                .build();

        car.setStatus(CarStatus.ON_LEASE);
        carRepository.save(car);

        return leaseRepository.save(lease);
    }

    @Override
    public Lease endLease(Long leaseId) {
        Lease lease = leaseRepository.findById(leaseId)
                .orElseThrow(() -> new LeaseNotFoundException(leaseId));

        if (lease.getEndDate() != null) {
            throw new BusinessRuleException("Lease already ended");
        }

        lease.setEndDate(LocalDateTime.now());
        lease.getCar().setStatus(CarStatus.IDLE);
        carRepository.save(lease.getCar());

        return leaseRepository.save(lease);
    }

    @Override
    public List<Lease> getLeaseHistoryByCustomer(Long customerId) {
        return leaseRepository.findAll().stream()
                .filter(l -> l.getCustomer().getId().equals(customerId))
                .toList();
    }

    @Override
    public List<Lease> getLeaseHistoryByCar(Long carId) {
        return leaseRepository.findAll().stream()
                .filter(l -> l.getCar().getId().equals(carId))
                .toList();
    }

}
