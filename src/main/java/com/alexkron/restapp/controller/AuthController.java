package com.alexkron.restapp.controller;

import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.exception.JwtAuthException;
import com.alexkron.restapp.security.JwtProvider;
import com.alexkron.restapp.service.AdminService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.util.HashMap;

@RestController
@Slf4j
@RequestMapping("/")
public class AuthController {
    @Autowired
    @Qualifier("AdminService")
    private AdminService adminService;

    @Autowired
    private JwtProvider jwtProvider;

    @SneakyThrows
    @PostMapping("auth/")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody
    HashMap<String, String> auth(@RequestParam("login") @Pattern(regexp = "\\w{4,20}") String login,
                                 @RequestParam("password") @Pattern(regexp = ".{8,60}") String password) {
        try {
            User user = adminService.getUserByLoginAndPassword(login, password);
            assert user != null;
            String token = jwtProvider.generateToken(user.getLogin());
            HashMap<String, String> response = new HashMap<>();
            response.put("TOKEN", token);
            log.info("Token issued: " + token);
            return response;
        } catch (Exception e) {
            log.error("Wrong login or password");
            throw new JwtAuthException("Authentication error");
        }
    }
}