package com.trimblecars.service.impl;

import com.trimblecars.entity.User;
import com.trimblecars.enums.Role;
import com.trimblecars.exception.BusinessRuleException;
import com.trimblecars.exception.UserNotFoundException;
import com.trimblecars.repository.UserRepository;
import com.trimblecars.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public List<User> getUsersByRole(Role role) {
        List<User> users = userRepository.findAll().stream()
                .filter(u -> u.getRole() == role)
                .toList();

        if (users.isEmpty()) {
            throw new BusinessRuleException("No users found with role: " + role);
        }
        return users;
    }

}
