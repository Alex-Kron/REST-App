package com.alexkron.restapp;

import com.alexkron.restapp.entity.User;
import com.alexkron.restapp.service.AdminService;
import com.alexkron.restapp.service.AdminServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class RestAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestAppApplication.class, args);
    }

}
