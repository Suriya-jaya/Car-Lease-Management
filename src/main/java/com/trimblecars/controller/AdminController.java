package com.trimblecars.controller;

import com.trimblecars.dto.CarDTO;
import com.trimblecars.dto.LeaseResponseDTO;
import com.trimblecars.dto.UserDTO;
import com.trimblecars.entity.User;
import com.trimblecars.enums.Role;
import com.trimblecars.service.CarService;
import com.trimblecars.service.LeaseService;
import com.trimblecars.service.UserService;
import com.trimblecars.util.CarMapper;
import com.trimblecars.util.LeaseMapper;
import com.trimblecars.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CarService carService;
    private final LeaseService leaseService;

    @PostMapping("/register-user")
    public UserDTO registerUser(@RequestBody UserDTO userDTO) {
        User user = userService.registerUser(UserMapper.toEntity(userDTO));
        return UserMapper.toDTO(user);
    }

    @GetMapping("/users/{role}")
    public List<UserDTO> getUsersByRole(@PathVariable Role role) {
        return userService.getUsersByRole(role).stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/cars")
    public List<CarDTO> getAllCars() {
        return carService.getAllCars().stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/car/{carId}/leases")
    public List<LeaseResponseDTO> getLeaseHistoryByCar(@PathVariable Long carId) {
        return leaseService.getLeaseHistoryByCar(carId).stream()
                .map(LeaseMapper::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/user/{userId}/leases")
    public List<LeaseResponseDTO> getLeaseHistoryByUser(@PathVariable Long userId) {
        return leaseService.getLeaseHistoryByCustomer(userId).stream()
                .map(LeaseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
