package com.alexkron.restapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PhoneNotFoundException extends Exception {
    public PhoneNotFoundException(String message) {
        super(message);
    }
}
