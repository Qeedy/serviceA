package com.microservice.serviceA.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
@Slf4j
public class BsaeException extends RuntimeException {
    public BsaeException(String message, Throwable cause) {
        log.info(message);
    }

    public BsaeException(String message) {
        super(message);
    }
}
