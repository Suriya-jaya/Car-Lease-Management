package com.trimblecars.service;

import com.trimblecars.entity.Car;
import com.trimblecars.entity.Lease;
import com.trimblecars.entity.User;
import com.trimblecars.enums.CarStatus;
import com.trimblecars.enums.Role;
import com.trimblecars.exception.BusinessRuleException;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.LeaseRepository;
import com.trimblecars.repository.UserRepository;
import com.trimblecars.service.impl.LeaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LeaseServiceImplTest {

    @InjectMocks
    private LeaseServiceImpl leaseService;

    @Mock
    private LeaseRepository leaseRepository;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    private Car car;
    private User customer;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        customer = User.builder().id(1L).name("John").role(Role.CUSTOMER).build();
        car = Car.builder().id(1L).model("Honda").status(CarStatus.IDLE).build();
    }

    @Test
    void testStartLeaseSuccess() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(leaseRepository.findAll()).thenReturn(List.of());
        when(leaseRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Lease lease = leaseService.startLease(1L, 1L);

        assertEquals(customer, lease.getCustomer());
        assertEquals(car, lease.getCar());
        assertNull(lease.getEndDate());
        verify(carRepository).save(car);
    }

    @Test
    void testStartLeaseWithMaxLimit() {
        Lease active1 = Lease.builder().customer(customer).car(car).build();
        Lease active2 = Lease.builder().customer(customer).car(car).build();

        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        when(userRepository.findById(1L)).thenReturn(Optional.of(customer));
        when(leaseRepository.findAll()).thenReturn(List.of(active1, active2));

        assertThrows(BusinessRuleException.class, () -> leaseService.startLease(1L, 1L));
    }
}
