package com.alexkron.restapp.service;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.Role;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.repository.PhoneRepository;
import com.alexkron.restapp.repository.ProfileRepository;
import com.alexkron.restapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public User setUser(User user) {
        try {
            User savedUser = userRepository.save(user);
            log.info("User added:\n" + user.toString());
            return savedUser;
        } catch (IllegalArgumentException e) {
            log.error("User adding error");
        }
        return null;
    }

    @Override
    public Phone setPhone(Phone phone) {
        try {
            Phone savedPhone = phoneRepository.save(phone);
            log.info("Phone added:\n" + phone.toString());
            return savedPhone;
        } catch (IllegalArgumentException e) {
            log.error("Phone adding error");
        }
        return null;
    }

    @Override
    public Profile setProfile(Profile profile) {
        try {
            Profile savedProfile = profileRepository.save(profile);
            log.info("Profile added:\n" + profile.toString());
            return savedProfile;
        } catch (IllegalArgumentException e) {
            log.error("Profile adding error");
        }
        return null;
    }

    @Override
    public User updateUser(Long userId, String name, LocalDate age, String email) {
        try {
            User user = userRepository.getById(userId);
            user.setName(name);
            user.setAge(age);
            user.setEmail(email);
            userRepository.save(user);
            log.info("User updated:\n" + user.toString());
        } catch (IllegalArgumentException e) {
            log.error("User updating error");
        }
        return null;
    }

    @Override
    public Phone updatePhone(Long phoneId, String number) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            phone.setNumber(number);
            phoneRepository.save(phone);
            log.info("Phone updated:\n" + phone.toString());
        } catch (IllegalArgumentException e) {
            log.error("Phone updating error");
        }
        return null;
    }

    @Override
    public Profile updateProfile(Long userId, Money cash) {
        try {
            Profile profile = profileRepository.findByUserId(userId);
            profile.setCash(cash);
            profileRepository.save(profile);
            log.info("Profile updated:\n" + profile.toString());
        } catch (IllegalArgumentException e) {
            log.error("Profile updating error");
        }
        return null;
    }

    @Override
    public User getUser(Long userId) {
        try {
            User user = userRepository.getById(userId);
            log.info("Get User with id = " + userId + "\n" + user.toString());
            return user;
        } catch (EntityNotFoundException e) {
            log.error("User with id = " + userId + " not found");
        }
        return null;
    }

    @Override
    public List<Phone> getPhones(Long userId) {
        try {
            List<Phone> phones = phoneRepository.findByUserId(userId);
            log.info("Get phones with userId = " + userId + "\n" + phones.toString());
            return phones;
        } catch (EntityNotFoundException e) {
            log.error("Phones with userId = " + userId + " not found");
        }
        return null;
    }

    @Override
    public Phone getPhone(Long phoneId) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            log.info("Get phone with id = " + phoneId + "\n" + phone.toString());
            return  phone;
        } catch (EntityNotFoundException e) {
            log.error("Phone with id = " + phoneId + " not found");
        }
        return null;
    }

    @Override
    public Profile getProfile(Long userId) {
        try {
            Profile profile = profileRepository.findByUserId(userId);
            log.info("Get profile with userId = " + userId + "\n" + profile.toString());
            return profile;
        } catch (EntityNotFoundException e) {
            log.error("Profile with userId = " + userId + " not found");
        }
        return null;
    }

    @Override
    public Role getUserRole(Long userId) {
        try {
            User user = userRepository.getById(userId);
            log.info("Get user role by userId = " + userId + "\n" + user.getRole());
            return user.getRole();
        } catch (Exception e) {
            log.error("Error getting user role by userId = " + userId);
        }
        return null;
    }

    @Override
    public User removeUser(Long userId) {
        try {
            User user = userRepository.getById(userId);
            userRepository.delete(user);
            log.info("User with id = " + userId + " deleted");
            return user;
        } catch (IllegalArgumentException e) {
            log.error("User with id = " + userId + " not found");
        }
        return  null;
    }

    @Override
    public Phone removePhone(Long phoneId) {
        try {
            Phone phone = phoneRepository.getById(phoneId);
            phoneRepository.delete(phone);
            log.info("Phone with id = " + phoneId + " deleted");
            return phone;
        } catch (IllegalArgumentException e) {
            log.error("Phone with id = " + phoneId + " not found");
        }
        return null;
    }

    @Override
    public Profile removeProfile(Long userId) {
        try {
            Profile profile = profileRepository.findByUserId(userId);
            profileRepository.delete(profile);
            log.info("Profile with userId = " + userId + " deleted");
            return profile;
        } catch (IllegalArgumentException e) {
            log.error("Profile with userId = " + userId + " not found");
        }
        return null;
    }
}
