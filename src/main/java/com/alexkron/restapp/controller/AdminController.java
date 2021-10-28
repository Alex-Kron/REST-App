package com.alexkron.restapp.controller;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    @Autowired
    @Qualifier("AdminService")
    private AdminService adminService;

    @GetMapping("/get/user/all/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<User> getAllUser(@RequestParam("page") int page,
                          @RequestParam("count") int count) {
        return adminService.getAllUsers(page, count);
    }

    @GetMapping("/get/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    User getUser(@RequestParam("id") Long userId) {
        return adminService.getUser(userId);
    }

    @GetMapping("/get/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Phone getPhone(@RequestParam("phoneId") Long phoneId) {
        return adminService.getPhone(phoneId);
    }

    @GetMapping("/get/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Profile getProfile(@RequestParam("userId") Long userId) {
        return adminService.getProfile(userId);
    }

    @PostMapping("/update/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, User> updateUser(@RequestParam("id") Long id,
                                       @RequestParam("login") String login,
                                       @RequestParam("password") String password,
                                       @RequestParam("name") String name,
                                       @RequestParam("age") String age,
                                       @RequestParam("email") String email,
                                       @RequestParam("role") String role) {
        HashMap<String, User> response = new HashMap<>();
        User user = adminService.updateUser(id, login, password, name, LocalDate.parse(age), email);
        user = adminService.setUserRole(user.getLogin(), adminService.getRoleByName(role));
        response.put("UPDATED", user);
        return response;
    }

    @PostMapping("/update/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Phone> updatePhone(@RequestParam("phoneId") Long phoneId,
                                        @RequestParam("number") String number) {
        HashMap<String, Phone> response = new HashMap<>();
        Phone phone = adminService.updatePhone(phoneId, number);
        response.put("UPDATED", phone);
        return response;
    }

    @PostMapping("/update/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Profile> updateProfile(@RequestParam("userId") Long userId,
                                           @RequestParam("cash")BigDecimal cash) {
        HashMap<String, Profile> response = new HashMap<>();
        Profile profile = adminService.updateProfile(userId, cash);
        response.put("UPDATED", profile);
        return response;
    }

    @PostMapping("/set/user/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, User> setUser(@RequestParam("login") String login,
                                  @RequestParam("password") String password,
                                  @RequestParam("name") String name,
                                  @RequestParam("age") String age,
                                  @RequestParam("email") String email,
                                  @RequestParam("role") String role) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setAge(LocalDate.parse(age));
        user.setEmail(email);
        adminService.setUser(user);
        user = adminService.setUserRole(user.getLogin(), adminService.getRoleByName(role));
        HashMap<String, User> response = new HashMap<>();
        response.put("CREATED", user);
        return response;
    }

    @PostMapping("/set/phone/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, Phone> setPhone(@RequestParam("userId") Long userId,
                                    @RequestParam("number") String number) {
        HashMap<String, Phone> response = new HashMap<>();
        Phone phone = new Phone();
        phone.setUser(adminService.getUser(userId));
        phone.setNumber(number);
        phone = adminService.setPhone(phone);
        response.put("CREATED", phone);
        return response;
    }

    @PostMapping("/set/profile/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, Profile> setProfile(@RequestParam("userId") Long userId,
                                    @RequestParam("cash") BigDecimal cash) {
        HashMap<String, Profile> response = new HashMap<>();
        Profile profile = new Profile();
        profile.setUser(adminService.getUser(userId));
        profile.setCash(cash);
        profile = adminService.setProfile(profile);
        response.put("CREATED", profile);
        return response;
    }

    @GetMapping("/remove/user/")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody
    HashMap<String, User> removeUser(@RequestParam("userId") Long userId) {
        HashMap<String, User> response = new HashMap<>();
        User user = adminService.removeUser(userId);
        response.put("REMOVED", user);
        return response;
    }

    @GetMapping("/remove/phone/")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody
    HashMap<String, Phone> removePhone(@RequestParam("phoneId") Long phoneId) {
        HashMap<String, Phone> response = new HashMap<>();
        Phone phone = adminService.removePhone(phoneId);
        response.put("REMOVED", phone);
        return response;
    }

    @GetMapping("/remove/profile/")
    @ResponseStatus(HttpStatus.OK)
    public  @ResponseBody
    HashMap<String, Profile> removeProfile(@RequestParam("userId") Long userId) {
        HashMap<String, Profile> response = new HashMap<>();
        Profile profile = adminService.removeProfile(userId);
        response.put("REMOVED", profile);
        return response;
    }
}
