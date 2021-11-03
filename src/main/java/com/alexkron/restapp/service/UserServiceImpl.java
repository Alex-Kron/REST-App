package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.PhoneNotFoundException;
import com.alexkron.restapp.exception.ProfileNotFoundException;
import com.alexkron.restapp.exception.UserNotFoundException;
import com.alexkron.restapp.repository.PhoneRepository;
import com.alexkron.restapp.repository.ProfileRepository;
import com.alexkron.restapp.repository.UserRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@Qualifier("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ProfileRepository profileRepository;


    @SneakyThrows
    @Override
    public Phone setPhone(Phone phone) {
        try {
            Phone savedPhone = phoneRepository.save(phone);
            log.info("Phone added:\n" + phone.toString());
            return savedPhone;
        } catch (IllegalArgumentException e) {
            log.error("Phone adding error");
            throw new PhoneNotFoundException("Saved Phone is null");
        }
    }

    @SneakyThrows
    @Override
    public Profile setProfile(Profile profile) {
        try {
            Profile savedProfile = profileRepository.save(profile);
            log.info("Profile added:\n" + profile.toString());
            return savedProfile;
        } catch (IllegalArgumentException e) {
            log.error("Profile adding error");
            throw new ProfileNotFoundException("Saved Profile is null");
        }
    }

    @SneakyThrows
    @Override
    public User updateUser(Long userId, String login, String password, String name, LocalDate age, String email) {
        try {
            User user = userRepository.getById(userId);
            user.setLogin(login);
            user.setPassword(passwordEncoder.encode(password));
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            User savedUser = userRepository.save(user);
            log.info("User updated:\n" + user.toString());
            return savedUser;
        } catch (IllegalArgumentException e) {
            log.error("User updating error");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public Phone updatePhone(Long phoneId, String number) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            phone.setNumber(number);
            Phone savedPhone = phoneRepository.save(phone);
            log.info("Phone updated:\n" + phone.toString());
            return savedPhone;
        } catch (IllegalArgumentException e) {
            log.error("Phone updating error");
            throw new PhoneNotFoundException("Phone not found");
        }
    }

    @SneakyThrows
    @Override
    public Profile updateProfile(Long userId, BigDecimal cash) {
        try {
            Profile profile = profileRepository.findByUserUserId(userId);
            profile.setCash(cash);
            Profile savedProfile = profileRepository.save(profile);
            log.info("Profile updated:\n" + profile.toString());
            return savedProfile;
        } catch (IllegalArgumentException e) {
            log.error("Profile updating error");
            throw new ProfileNotFoundException("Profile not found");
        }
    }

    @SneakyThrows
    @Override
    public User getUser(Long userId) {
        try {
            User user = userRepository.getById(userId);
            log.info("Get User with id = " + userId + "\n" + user.toString());
            return user;
        } catch (EntityNotFoundException e) {
            log.error("User with id = " + userId + " not found");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public User getUserByLogin(String login) {
        try {
            User user = userRepository.findByLogin(login);
            log.info(("Found user:\n" + user.toString() + "\nby login: " + login));
            return user;
        } catch (Exception e) {
            log.error("User by login=" + login + " not found");
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public List<Phone> getPhones(Long userId) {
        try {
            List<Phone> phones = phoneRepository.findByUserUserId(userId);
            log.info("Get phones with userId = " + userId + "\n" + phones.toString());
            return phones;
        } catch (EntityNotFoundException e) {
            log.error("Phones with userId = " + userId + " not found");
            throw new PhoneNotFoundException("Phones not found");
        }
    }

    @SneakyThrows
    @Override
    @Transactional
    public Phone getPhone(Long phoneId) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            log.info("Get phone with id = " + phoneId + "\n" + phone.toString());
            return  phone;
        } catch (EntityNotFoundException e) {
            log.error("Phone with id = " + phoneId + " not found");
            throw new PhoneNotFoundException("Phone not found");
        }
    }

    @SneakyThrows
    @Override
    public Profile getProfile(Long userId) {
        try {
            Profile profile = profileRepository.findByUserUserId(userId);
            if (profile != null) {
                log.info("Get profile with userId = " + userId + "\n" + profile.toString());
            }
            return profile;
        } catch (Exception e) {
            log.error("Profile with userId = " + userId + " not found");
            throw new ProfileNotFoundException("Profile not found");
        }
    }

    @SneakyThrows
    @Override
    public Role getUserRole(Long userId) {
        try {
            User user = userRepository.getById(userId);
            Role role = user.getRole();
            log.info("Get user role by userId = " + userId + "\n" + role);
            return role;
        } catch (Exception e) {
            log.error("Error getting user role by userId = " + userId);
            throw new UserNotFoundException("User not found");
        }
    }

    @SneakyThrows
    @Override
    public Phone removePhone(Long phoneId) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            phoneRepository.delete(phone);
            log.info("Phone with id = " + phoneId + " deleted");
            return phone;
        } catch (IllegalArgumentException e) {
            log.error("Phone with id = " + phoneId + " not found");
            throw new ProfileNotFoundException("Phone not found");
        }
    }

    @SneakyThrows
    @Override
    public Profile removeProfile(Long userId) {
        try {
            Profile profile = profileRepository.findByUserUserId(userId);
            profileRepository.delete(profile);
            log.info("Profile with userId = " + userId + " deleted");
            return profile;
        } catch (IllegalArgumentException e) {
            log.error("Profile with userId = " + userId + " not found");
            throw new ProfileNotFoundException("Profile not found");
        }
    }
}
