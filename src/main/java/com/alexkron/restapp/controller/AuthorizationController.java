package com.alexkron.restapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {
    @GetMapping("/")
    public String authorization() {
        return "authorization";
    }
}
