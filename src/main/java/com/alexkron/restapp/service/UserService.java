package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import org.javamoney.moneta.Money;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    User setUser(User user);

    Phone setPhone(Phone phone);

    Profile setProfile(Profile profile);

    User updateUser(Long userId, String name, LocalDate age, String email);

    Phone updatePhone(Long userId, String phone);

    Profile updateProfile(Long userId, Money cash);

    User getUser(Long userId);

    Role getUserRole(Long userId);

    List<Phone> getPhones(Long userId);

    Phone getPhone(Long phoneId);

    Profile getProfile(Long userId);

    User removeUser(Long userId);

    Phone removePhone(Long phoneId);

    Profile removeProfile(Long userId);
}
