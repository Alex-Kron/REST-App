package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.RoleNotFoundException;
import com.alexkron.restapp.exception.UserNotFoundException;
import com.alexkron.restapp.repository.RoleRepository;
import com.alexkron.restapp.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@Qualifier("AdminService")
public class AdminServiceImpl extends UserServiceImpl implements AdminService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s);
        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getRoleName())));
    }

    @SneakyThrows
    @Override
    public Page<User> getAllUsers(int pageNum, int count) {
        try {
            Pageable page = PageRequest.of(pageNum, count);
            Page<User> users = userRepository.findAll(page);
            log.info("Get users (count = " + users.getTotalElements() + ")");
            return users;
        } catch (Exception e) {
            log.error("Error getting the list of users");
            throw new UserNotFoundException("User list is not available");
        }
    }

    @SneakyThrows
    @Override
    public User setUserRole(String login, Role role) {
        try {
            User user = userRepository.findByLogin(login);
            user.setRole(role);
            User savedUser = userRepository.save(user);
            log.info("Set role=" + role.toString() + " to User: " + user.toString());
            return savedUser;
        } catch (Exception e) {
            log.error("Role change error");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public User setUser(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRole(getRoleUser());
            User savedUser = userRepository.save(user);
            log.info("User successfully added:\n" + user.toString());
            return savedUser;
        } catch (IllegalArgumentException e) {
            log.error("User adding error");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public User removeUser(Long userId) {
        try {
            User user = userRepository.getById(userId);
            userRepository.delete(user);
            log.info("User with id=" + userId + " successfully deleted");
            return user;
        } catch (IllegalArgumentException e) {
            log.error("User with id=" + userId + " not found");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public User getUserByLoginAndPassword(String login, String password) {
        try {
            User user = userRepository.findByLogin(login);
            assert user != null;
            if (passwordEncoder.matches(password, user.getPassword())) {
                log.info("User by login=" + login + " and password=" + password + " successfully found");
                return user;
            } else {
                throw new UserNotFoundException("User not found");
            }
        } catch (Exception e) {
            log.error("User by login=" + login + " and password=" + password + " not found");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public Role getRoleAdmin() {
        Role role = roleRepository.findByRoleName("ROLE_ADMIN");
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return role;
    }

    @SneakyThrows
    @Override
    public Role getRoleUser() {
        Role role = roleRepository.findByRoleName("ROLE_USER");
        if (role == null) {
            throw new RoleNotFoundException("Role not found");
        }
        return role;
    }

    @SneakyThrows
    @Override
    public Role getRoleByName(String name) {
        try {
            return roleRepository.findByRoleName(name);
        } catch (Exception e) {
            log.error("Role by name=" + name + " not found");
            throw new RoleNotFoundException("Role not found");
        }
    }
}
