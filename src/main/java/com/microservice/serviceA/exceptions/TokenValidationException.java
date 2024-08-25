package com.microservice.serviceA.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Slf4j
public class TokenValidationException extends RuntimeException {
    public TokenValidationException(String message, Throwable cause) {
        log.info(message);
    }

    public TokenValidationException(String message) {
        super(message);
    }
}
