package com.trimblecars.service;

import com.trimblecars.entity.Car;
import com.trimblecars.entity.User;
import com.trimblecars.enums.CarStatus;
import com.trimblecars.enums.Role;
import com.trimblecars.exception.CarNotFoundException;
import com.trimblecars.exception.UserNotFoundException;
import com.trimblecars.repository.CarRepository;
import com.trimblecars.repository.UserRepository;
import com.trimblecars.service.impl.CarServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarRepository carRepository;

    @Mock
    private UserRepository userRepository;

    private User owner;
    private Car car;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        owner = User.builder().id(1L).name("Alice").role(Role.CAR_OWNER).build();
        car = Car.builder().id(1L).model("Toyota").status(CarStatus.IDLE).owner(owner).build();
    }

    @Test
    void testRegisterCarSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(carRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        Car savedCar = carService.registerCar(car, 1L);
        assertEquals(CarStatus.IDLE, savedCar.getStatus());
        assertEquals(owner, savedCar.getOwner());
    }

    @Test
    void testRegisterCarOwnerNotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> carService.registerCar(car, 2L));
    }

    @Test
    void testGetCarByIdSuccess() {
        when(carRepository.findById(1L)).thenReturn(Optional.of(car));
        Car found = carService.getCarById(1L);
        assertEquals("Toyota", found.getModel());
    }

    @Test
    void testGetCarByIdNotFound() {
        when(carRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(CarNotFoundException.class, () -> carService.getCarById(99L));
    }

    @Test
    void testGetCarsByStatus() {
        when(carRepository.findAll()).thenReturn(List.of(car));
        List<Car> idleCars = carService.getCarsByStatus(CarStatus.IDLE);
        assertEquals(1, idleCars.size());
    }

    @Test
    void testGetCarsByOwner() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(carRepository.findAll()).thenReturn(List.of(car));
        List<Car> cars = carService.getCarsByOwner(1L);
        assertEquals(1, cars.size());
        assertEquals("Toyota", cars.get(0).getModel());
    }
}
