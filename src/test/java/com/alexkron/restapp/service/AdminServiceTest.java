package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.repository.RoleRepository;
import com.alexkron.restapp.repository.UserRepository;
import com.alexkron.restapp.init.InitializationComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    RoleRepository roleRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    User user;

    @MockBean
    Role role;

    @MockBean
    InitializationComponent initializationComponent;

    @Test
    public void setUserRoleTest() {
        String login = "login";
        Mockito.when(userRepository.findByLogin(login)).thenReturn(user);
        Mockito.when(userRepository.save(ArgumentMatchers.same(user))).thenReturn(user);
        Assertions.assertSame(user, adminService.setUserRole(login, role));
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.same(user));
    }

    @Test
    public void setUserTest() {
        Mockito.when(userRepository.save(ArgumentMatchers.same(user))).thenReturn(user);
        Mockito.when(user.getPassword()).thenReturn("");
        Mockito.when(passwordEncoder.encode(ArgumentMatchers.anyString())).thenReturn(null);
        Mockito.doNothing().when(user).setPassword(Mockito.any(String.class));
        Mockito.doNothing().when(user).setRole(ArgumentMatchers.any(Role.class));
        Assertions.assertSame(user, adminService.setUser(user));
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.same(user));
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(ArgumentMatchers.anyString());
    }

    @Test
    public void removeUserTest() {
        Mockito.when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(user);
        Assertions.assertSame(user, adminService.removeUser(0L));
        Mockito.verify(userRepository, Mockito.times(1)).delete(user);
    }

    @Test
    public void getUserByLoginAndPasswordTest() {
        String login = "login";
        String password = "password";
        Mockito.when(userRepository.findByLogin(login)).thenReturn(user);
        Mockito.when(user.getPassword()).thenReturn(password);
        Mockito.when(passwordEncoder.matches(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(true);
        Assertions.assertSame(user, adminService.getUserByLoginAndPassword(login, password));
    }

    @Test
    public void getRoleByNameTest() {
        Mockito.when(roleRepository.findByRoleName(ArgumentMatchers.anyString())).thenReturn(role);
        Assertions.assertSame(role, adminService.getRoleByName("ROLE_TEAPOT"));
        Mockito.verify(roleRepository, Mockito.times(1)).findByRoleName(ArgumentMatchers.anyString());
    }

    @Test
    public void getAllUsersTest() {
        Pageable pageable = PageRequest.of(0, 1);
        List<User> list = new ArrayList<>();
        list.add(user);
        Page<User> page = new PageImpl<>(list);
        Mockito.when(userRepository.findAllWithFilters(ArgumentMatchers.any(LocalDate.class),
                ArgumentMatchers.any(LocalDate.class),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(pageable))).thenReturn(page);
        Assertions.assertSame(page, adminService.getAllUsers(2000, "", "", "", 0, 1));
        Mockito.verify(userRepository, Mockito.times(1)).findAllWithFilters(ArgumentMatchers.any(LocalDate.class),
                ArgumentMatchers.any(LocalDate.class),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(pageable));
    }
}
