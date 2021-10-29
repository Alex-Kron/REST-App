package com.alexkron.restapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProfileIsAlreadyExistException extends Exception {
    public ProfileIsAlreadyExistException(String message) {
        super(message);
    }
}
