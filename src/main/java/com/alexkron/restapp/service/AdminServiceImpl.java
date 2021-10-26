package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl extends UserServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.info("Get all users (count = " + users.size() + ")");
        return users;
    }

    @Override
    public User setUserRole(Long userId, Role role) {
        try {
            User user = userRepository.getById(userId);
            user.setRole(role);
            userRepository.save(user);
            log.info("Set role = " + role.toString() + " to User: " + user.toString());
        } catch (Exception e) {
            log.error("Role change error");
        }
        return null;
    }
}
