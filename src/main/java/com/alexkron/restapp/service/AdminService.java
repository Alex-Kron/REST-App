package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserService, UserDetailsService {

    User setUserRole(String login, Role role);

    User setUser(User user);

    User removeUser(Long userId);

    User getUserByLoginAndPassword(String login, String password);

    Role getRoleByName(String name);

    Page<User> getAllUsers(Integer age, String name, String phone, String email, int page, int count);
}
