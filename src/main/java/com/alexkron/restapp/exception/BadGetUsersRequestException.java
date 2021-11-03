package com.alexkron.restapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadGetUsersRequestException extends Exception{
    public BadGetUsersRequestException(String message) {
        super(message);
    }
}
