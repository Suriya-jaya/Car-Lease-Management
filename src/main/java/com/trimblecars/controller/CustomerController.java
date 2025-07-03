package com.trimblecars.controller;

import com.trimblecars.dto.CarDTO;
import com.trimblecars.dto.LeaseResponseDTO;
import com.trimblecars.dto.UserDTO;
import com.trimblecars.entity.User;
import com.trimblecars.enums.CarStatus;
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
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CarService carService;
    private final LeaseService leaseService;
    private final UserService userService;

    @PostMapping("/register")
    public UserDTO registerCustomer(@RequestBody UserDTO userDTO) {
        userDTO.setRole(Role.CUSTOMER);
        User user = userService.registerUser(UserMapper.toEntity(userDTO));
        return UserMapper.toDTO(user);
    }

    @GetMapping("/cars")
    public List<CarDTO> getAvailableCars() {
        return carService.getCarsByStatus(CarStatus.IDLE).stream()
                .map(CarMapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/{customerId}/lease/{carId}")
    public LeaseResponseDTO startLease(@PathVariable Long customerId, @PathVariable Long carId) {
        return LeaseMapper.toDTO(leaseService.startLease(carId, customerId));
    }

    @PostMapping("/{customerId}/end-lease/{leaseId}")
    public LeaseResponseDTO endLease(@PathVariable Long leaseId) {
        return LeaseMapper.toDTO(leaseService.endLease(leaseId));
    }

    @GetMapping("/{customerId}/leases")
    public List<LeaseResponseDTO> getLeaseHistory(@PathVariable Long customerId) {
        return leaseService.getLeaseHistoryByCustomer(customerId).stream()
                .map(LeaseMapper::toDTO)
                .collect(Collectors.toList());
    }
}
