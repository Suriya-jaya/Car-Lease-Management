package com.trimblecars.service;

import com.trimblecars.entity.User;
import com.trimblecars.enums.Role;

import java.util.List;

public interface UserService {
    User registerUser(User user);
    User getUserById(Long id);
    List<User> getUsersByRole(Role role);
}
