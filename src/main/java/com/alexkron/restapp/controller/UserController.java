package com.alexkron.restapp.controller;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.PhoneNotFoundException;
import com.alexkron.restapp.filter.JwtFilter;
import com.alexkron.restapp.security.JwtProvider;
import com.alexkron.restapp.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private JwtFilter jwtFilter;

    @Qualifier("UserService")
    @Autowired
    private UserService userService;

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> getThisUser(HttpServletRequest request) {
        String login = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        User user = userService.getUserByLogin(login);
        List<Phone> phones = userService.getPhones(user.getUserId());
        Profile profile = userService.getProfile(user.getUserId());
        HashMap<String, Object> response = new HashMap<>();
        response.put("USER", user);
        response.put("PROFILE", profile);
        response.put("PHONES", phones);
        log.info("Get all info about user:" + user);
        return response;
    }

    @PostMapping("/update/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    User updateUser(@RequestParam("login") @Pattern(regexp = "\\w{4,20}") String login,
                    @RequestParam("password") @Pattern(regexp = ".{8,60}") String password,
                    @RequestParam("name") @Pattern(regexp = "[А-Я][а-я]+\\s[А-Я][а-я]+\\s[А-Я][а-я]+") String name,
                    @RequestParam("age") String age,
                    @RequestParam("email") @Email(message = "Email should be valid") String email,
                    HttpServletRequest request) {
        String oldLogin = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        Long id = userService.getUserByLogin(oldLogin).getUserId();
        return userService.updateUser(id, login, password, name, LocalDate.parse(age), email);
    }

    @SneakyThrows
    @PostMapping("/update/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Phone updatePhone(@RequestParam("phoneId") @Min(value = 0) Long phoneId,
                      @RequestParam("phone") @Pattern(regexp = "\\+7\\d{10}") String newPhone,
                      HttpServletRequest request) {
        String login = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        Long userId = userService.getUserByLogin(login).getUserId();
        Phone phone = userService.getPhone(phoneId);
        if (!userId.equals(phone.getUser().getUserId())) {
            throw new PhoneNotFoundException("Phone not found");
        }
        return userService.updatePhone(phoneId, newPhone);
    }

    @PostMapping("/update/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Profile updateProfile(@RequestParam("cash") @DecimalMin(value = "0.0") @Digits(integer = 6, fraction = 2) BigDecimal cash, HttpServletRequest request) {
        String login = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        Long userId = userService.getUserByLogin(login).getUserId();
        return userService.updateProfile(userId, cash);
    }

    @PostMapping("/set/phone/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Phone setPhone(@RequestParam("phone") @Pattern(regexp = "\\+7\\d{10}") String number, HttpServletRequest request) {
        String login = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        User user = userService.getUserByLogin(login);
        Phone phone = new Phone();
        phone.setNumber(number);
        phone.setUser(user);
        return userService.setPhone(phone);
    }

    @PostMapping("/remove/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> removePhone(@RequestParam("phoneId") @Min(value = 0) Long phoneId,
                                        HttpServletRequest request) throws PhoneNotFoundException {
        String login = jwtProvider.getLoginFromToken(jwtFilter.getTokenFromRequest(request));
        User user = userService.getUserByLogin(login);
        Phone phone = userService.getPhone(phoneId);
        if (!user.equals(phone.getUser())) {
            throw new PhoneNotFoundException("Phone not found");
        }
        phone = userService.removePhone(phoneId);
        HashMap<String, Object> response = new HashMap<>();
        response.put("REMOVED", phone);
        return response;
    }
}
