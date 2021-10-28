package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.RoleNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminService extends UserService, UserDetailsService {
    Page<User> getAllUsers(int page, int count);

    User setUserRole(String login, Role role);

    User setUser(User user);

    User removeUser(Long userId);

    User getUserByLoginAndPassword(String login, String password);

    Role getRoleByName(String name);

    Role getRoleAdmin() throws RoleNotFoundException;

    Role getRoleUser();
}
