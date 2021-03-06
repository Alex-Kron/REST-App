package com.alexkron.restapp.controller;

import com.alexkron.restapp.entity.Phone;
import com.alexkron.restapp.entity.Profile;
import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.BadGetUsersRequestException;
import com.alexkron.restapp.exception.ProfileIsAlreadyExistException;
import com.alexkron.restapp.service.AdminService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Validated
@Tag(name = "Administrator controller", description = "The controller implements actions available to users with the role ROLE_ADMIN")
public class AdminController {
    @Hidden
    @Autowired
    @Qualifier("AdminService")
    private AdminService adminService;

    @Hidden
    @Min(1900)
    private Integer age;

    @Hidden
    @Min(0)
    private int page;

    @Hidden
    @Min(1)
    private int count;

    @Operation(summary = "Get all users", description = "Returns the users page. 'page' and 'count' parameters are required. The parameters 'age' (year of birth), 'phone', 'name' (Name like)  and 'email' are filters (optional)")
    @SneakyThrows
    @GetMapping("/get/user/all/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Page<User> getAllUser(@RequestParam
                          @Parameter(description = "Required parameters: page (value >= 0), count (value > 0). Optional parameters: name (like) , age (year), phone, email.")
                                  HashMap<String, String> parameters) {
        String phone = "";
        String name = "";
        String email = "";
        if (parameters.containsKey("page")) {
            page = Integer.parseInt(parameters.get("page"));
        } else {
            throw new BadGetUsersRequestException("Parameter \"page\" is missing");
        }

        if (parameters.containsKey("count")) {
            count = Integer.parseInt(parameters.get("count"));
        } else {
            throw new BadGetUsersRequestException("Parameter \"count\" is missing");
        }

        log.info("ADMIN REQUEST: get all users by page=" + page + " count=" + count);

        if (parameters.containsKey("age")) {
            age = Integer.parseInt(parameters.get("age"));
            log.info("SET FILTER: age=" + age);
        }

        if (parameters.containsKey("phone")) {
            phone = parameters.get("phone");
            log.info("SET FILTER: phone: " + phone);
        }

        if (parameters.containsKey("name")) {
            name = parameters.get("name");
            log.info("SET FILTER: name like: " + name);
        }

        if (parameters.containsKey("email")) {
            email = parameters.get("email");
            log.info("SET FILTER: email: " + email);
        }
        return adminService.getAllUsers(age, name, phone, email, page, count);
    }

    @Operation(summary = "Get User by id")
    @GetMapping("/get/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    User getUser(@RequestParam("id") @Min(value = 0) @Parameter(description = "User ID") Long userId) {
        log.info("ADMIN REQUEST: get user by id=" + userId);
        return adminService.getUser(userId);
    }

    @Operation(summary = "Get Phone by phoneId")
    @GetMapping("/get/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Phone getPhone(@RequestParam("phoneId") @Min(value = 0) @Parameter(description = "Phone ID") Long phoneId) {
        log.info("ADMIN REQUEST: get phone by id");
        return adminService.getPhone(phoneId);
    }

    @Operation(summary = "Get profile by userId")
    @GetMapping("/get/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    Profile getProfile(@RequestParam("userId") @Min(value = 0) @Parameter(description = "User ID") Long userId) {
        log.info("ADMIN REQUEST: get profile by userId=" + userId);
        return adminService.getProfile(userId);
    }

    @Operation(summary = "Update User by id")
    @PostMapping("/update/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> updateUser(@RequestParam("id") @Min(value = 0) Long id,
                                       @RequestParam("login") @Pattern(regexp = "\\w{4,20}") String login,
                                       @RequestParam("password") @Pattern(regexp = ".{8,60}") String password,
                                       @RequestParam("name") @Pattern(regexp = "[??-??][??-??]+\\s[??-??][??-??]+\\s[??-??][??-??]+") String name,
                                       @RequestParam("age") String age,
                                       @RequestParam("email") @Email(message = "Email should be valid") String email,
                                       @RequestParam("role") @Pattern(regexp = "ROLE_\\D*") String role) {
        log.info("ADMIN REQUEST: update user by id=" + id);
        HashMap<String, Object> response = new HashMap<>();
        User user = adminService.updateUser(id, login, password, name, LocalDate.parse(age), email);
        user = adminService.setUserRole(user.getLogin(), adminService.getRoleByName(role));
        response.put("UPDATED", user);
        return response;
    }

    @Operation(summary = "Update Phone by phoneId")
    @PostMapping("/update/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> updatePhone(@RequestParam("phoneId") @Min(value = 0) Long phoneId,
                                        @RequestParam("number") @Pattern(regexp = "\\+7\\d{10}") String number) {
        log.info("ADMIN REQUEST: update phone by id=" + phoneId);
        HashMap<String, Object> response = new HashMap<>();
        Phone phone = adminService.updatePhone(phoneId, number);
        response.put("UPDATED", phone);
        return response;
    }

    @Operation(summary = "Update Profile by userId")
    @PostMapping("/update/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> updateProfile(@RequestParam("userId") @Min(value = 0) Long userId,
                                          @RequestParam("cash") @DecimalMin(value = "0.0") @Digits(integer = 6, fraction = 2) BigDecimal cash) {
        log.info("ADMIN REQUEST: update profile by id=" + userId);
        HashMap<String, Object> response = new HashMap<>();
        Profile profile = adminService.updateProfile(userId, cash);
        response.put("UPDATED", profile);
        return response;
    }

    @Operation(summary = "Create new User")
    @PostMapping("/set/user/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, Object> setUser(@RequestParam("login") @Pattern(regexp = "\\w{4,20}") String login,
                                    @RequestParam("password") @Pattern(regexp = ".{8,60}") String password,
                                    @RequestParam("name") @Pattern(regexp = "[??-??][??-??]+\\s[??-??][??-??]+\\s[??-??][??-??]+") String name,
                                    @RequestParam("age") String age,
                                    @RequestParam("email") @Email(message = "Email should be valid") String email,
                                    @RequestParam("role") @Pattern(regexp = "ROLE_\\D*") String role) {
        log.info("ADMIN REQUEST: set new user");
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(name);
        user.setAge(LocalDate.parse(age));
        user.setEmail(email);
        adminService.setUser(user);
        user = adminService.setUserRole(user.getLogin(), adminService.getRoleByName(role));
        HashMap<String, Object> response = new HashMap<>();
        response.put("CREATED", user);
        return response;
    }

    @Operation(summary = "Create new Phone for User by userId")
    @PostMapping("/set/phone/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, Object> setPhone(@RequestParam("userId") @Min(value = 0) Long userId,
                                     @RequestParam("number") @Pattern(regexp = "\\+7\\d{10}") String number) {
        log.info("ADMIN REQUEST: set new phone");
        HashMap<String, Object> response = new HashMap<>();
        Phone phone = new Phone();
        phone.setUser(adminService.getUser(userId));
        phone.setNumber(number);
        phone = adminService.setPhone(phone);
        response.put("CREATED", phone);
        return response;
    }

    @Operation(summary = "Create new Profile for User by userId")
    @SneakyThrows
    @PostMapping("/set/profile/")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    HashMap<String, Object> setProfile(@RequestParam("userId") @Min(value = 0) Long userId,
                                       @RequestParam("cash") @DecimalMin(value = "0.0") @Digits(integer = 6, fraction = 2) BigDecimal cash) {
        log.info("ADMIN REQUEST: set new profile");
        HashMap<String, Object> response = new HashMap<>();
        User user = adminService.getUser(userId);
        if (adminService.getProfile(userId) != null) {
            log.error("User already has a profile");
            throw new ProfileIsAlreadyExistException("User already has a profile");
        }
        Profile profile = new Profile();
        profile.setCash(cash);
        profile.setUser(user);
        //user.setProfile(profile);
        profile = adminService.setProfile(profile);
        response.put("CREATED", profile);
        return response;
    }

    @Operation(summary = "Delete user by userId")
    @GetMapping("/remove/user/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> removeUser(@RequestParam("userId") @Min(value = 0) Long userId) {
        log.info("ADMIN REQUEST: remove user by id=" + userId);
        HashMap<String, Object> response = new HashMap<>();
        User user = adminService.removeUser(userId);
        response.put("REMOVED", user);
        return response;
    }

    @Operation(summary = "Delete phone by phoneId")
    @GetMapping("/remove/phone/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> removePhone(@RequestParam("phoneId") @Min(value = 0) Long phoneId) {
        log.info("ADMIN REQUEST: remove phone by id=" + phoneId);
        HashMap<String, Object> response = new HashMap<>();
        Phone phone = adminService.removePhone(phoneId);
        response.put("REMOVED", phone);
        return response;
    }

    @Operation(summary = "Delete profile by userId")
    @GetMapping("/remove/profile/")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    HashMap<String, Object> removeProfile(@RequestParam("userId") @Min(value = 0) Long userId) {
        log.info("ADMIN REQUEST: remove profile by id=" + userId);
        HashMap<String, Object> response = new HashMap<>();
        Profile profile = adminService.removeProfile(userId);
        response.put("REMOVED", profile);
        return response;
    }
}
