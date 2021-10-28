package com.alexkron.restapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.security.auth.message.AuthException;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid login or password")
public class JwtAuthException extends AuthException {
    public JwtAuthException(String msg) {
        super(msg);
    }
}
