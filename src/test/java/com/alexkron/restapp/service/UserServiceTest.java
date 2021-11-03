package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.repository.PhoneRepository;
import com.alexkron.restapp.repository.ProfileRepository;
import com.alexkron.restapp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    @Qualifier("UserService")
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    ProfileRepository profileRepository;

    @MockBean
    PhoneRepository phoneRepository;

    @MockBean
    PasswordEncoder passwordEncoder;

    @MockBean
    User user;

    @MockBean
    Role role;

    @MockBean
    Phone phone;

    @MockBean
    Profile profile;

    @Test
    public void setPhoneTest() {
        Mockito.when(phoneRepository.save(ArgumentMatchers.eq(phone))).thenReturn(phone);
        Assertions.assertEquals(phone, userService.setPhone(phone));
        Mockito.verify(phoneRepository, Mockito.times(1)).save(ArgumentMatchers.eq(phone));
    }

    @Test
    public void setProfileTest() {
        Mockito.when(profileRepository.save(ArgumentMatchers.eq(profile))).thenReturn(profile);
        Assertions.assertEquals(profile, userService.setProfile(profile));
        Mockito.verify(profileRepository, Mockito.times(1)).save(ArgumentMatchers.eq(profile));
    }
    
    @Test
    public void updateUserTest() {
        Long id = 0L;
        String login = "login";
        String password = "password";
        String name = "Teapot";
        LocalDate age = LocalDate.now();
        String email = "teapot@email.com";
        Mockito.when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(user);
        Mockito.when(userRepository.save(ArgumentMatchers.eq(user))).thenReturn(user);
        Mockito.when(passwordEncoder.encode(password)).thenReturn(password);
        Assertions.assertEquals(user, userService.updateUser(id, login, password,name, age, email));
        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.eq(user));
        Mockito.verify(user, Mockito.times(1)).setLogin(ArgumentMatchers.eq(login));
        Mockito.verify(user, Mockito.times(1)).setPassword(ArgumentMatchers.eq(password));
        Mockito.verify(user, Mockito.times(1)).setName(ArgumentMatchers.eq(name));
        Mockito.verify(user, Mockito.times(1)).setAge(ArgumentMatchers.eq(age));
        Mockito.verify(user, Mockito.times(1)).setEmail(ArgumentMatchers.eq(email));
    }

    @Test
    public void updatePhoneTest() {
        Long id = 0L;
        String number = "+70000000000";
        Mockito.when(phoneRepository.getById(ArgumentMatchers.anyLong())).thenReturn(phone);
        Mockito.when(phoneRepository.save(ArgumentMatchers.eq(phone))).thenReturn(phone);
        Assertions.assertEquals(phone, userService.updatePhone(id, number));
        Mockito.verify(phoneRepository, Mockito.times(1)).save(ArgumentMatchers.eq(phone));
        Mockito.verify(phone, Mockito.times(1)).setNumber(ArgumentMatchers.eq(number));
    }

    @Test
    public void updateProfileTest() {
        Long id = 0L;
        BigDecimal cash = BigDecimal.ONE;
        Mockito.when(profileRepository.findByUserUserId(ArgumentMatchers.anyLong())).thenReturn(profile);
        Mockito.when(profileRepository.save(ArgumentMatchers.eq(profile))).thenReturn(profile);
        Assertions.assertEquals(profile, userService.updateProfile(id, cash));
        Mockito.verify(profileRepository, Mockito.times(1)).save(ArgumentMatchers.eq(profile));
        Mockito.verify(profile, Mockito.times(1)).setCash(ArgumentMatchers.eq(cash));
    }
    
    @Test
    public void getUserTest() {
        Long id = 0L;
        Mockito.when(userRepository.getById(ArgumentMatchers.anyLong())).thenReturn(user);
        Assertions.assertEquals(user, userService.getUser(id));
        Mockito.verify(userRepository, Mockito.times(1)).getById(ArgumentMatchers.eq(id));
    }

    @Test
    public void getUserByLoginTest() {
        String login = "login";
        Mockito.when(userRepository.findByLogin(ArgumentMatchers.eq(login))).thenReturn(user);
        Assertions.assertEquals(user, userService.getUserByLogin(login));
        Mockito.verify(userRepository, Mockito.times(1)).findByLogin(ArgumentMatchers.eq(login));
    }
    
    @Test
    public void getPhonesTest() {
        List<Phone> list = new ArrayList<>();
        list.add(phone);
        Long id = 0L;
        Mockito.when(phoneRepository.findByUserUserId(ArgumentMatchers.eq(id))).thenReturn(list);
        Assertions.assertEquals(list, userService.getPhones(id));
        Mockito.verify(phoneRepository, Mockito.times(1)).findByUserUserId(ArgumentMatchers.eq(id));
    }

    @Test
    public void getPhoneTest() {
        Long id = 0L;
        Mockito.when(phoneRepository.getById(ArgumentMatchers.eq(id))).thenReturn(phone);
        Assertions.assertEquals(phone, userService.getPhone(id));
        Mockito.verify(phoneRepository, Mockito.times(1)).getById(ArgumentMatchers.eq(id));
    }

    @Test
    public void getProfileTest() {
        Long id = 0L;
        Mockito.when(profileRepository.findByUserUserId(ArgumentMatchers.eq(id))).thenReturn(profile);
        Assertions.assertEquals(profile, userService.getProfile(id));
        Mockito.verify(profileRepository, Mockito.times(1)).findByUserUserId(ArgumentMatchers.eq(id));
    }

    @Test
    public void getUserRoleTest() {
        Long id = 0L;
        Mockito.when(userRepository.getById(ArgumentMatchers.eq(id))).thenReturn(user);
        Mockito.when(user.getRole()).thenReturn(role);
        Assertions.assertEquals(role, userService.getUserRole(id));
        Mockito.verify(userRepository, Mockito.times(1)).getById(ArgumentMatchers.eq(id));
        Mockito.verify(user, Mockito.times(1)).getRole();
    }

    @Test
    public void removePhoneTest() {
        Long id = 0L;
        Mockito.when(phoneRepository.getById(ArgumentMatchers.eq(id))).thenReturn(phone);
        Assertions.assertEquals(phone, userService.removePhone(id));
        Mockito.verify(phoneRepository, Mockito.times(1)).delete(phone);
        Mockito.verify(phoneRepository, Mockito.times(1)).getById(ArgumentMatchers.eq(id));
    }
    
    @Test
    public  void removeProfileTest() {
        Long id = 0L;
        Mockito.when(profileRepository.findByUserUserId(ArgumentMatchers.eq(id))).thenReturn(profile);
        Assertions.assertEquals(profile, userService.removeProfile(id));
        Mockito.verify(profileRepository, Mockito.times(1)).delete(ArgumentMatchers.eq(profile));
        Mockito.verify(profileRepository, Mockito.times(1)).findByUserUserId(ArgumentMatchers.eq(id));
    }
}
