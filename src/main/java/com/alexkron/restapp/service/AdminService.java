package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;

import java.util.List;

public interface AdminService extends UserService {
    List<User> getAllUsers();

    User setUserRole(Long userId, Role role);
}
