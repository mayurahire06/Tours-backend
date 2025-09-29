package com.mh.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for cases where a requested file is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyFileNotFoundException extends RuntimeException {
    public MyFileNotFoundException(String message) {
        super(message);
    }

    public MyFileNotFoundException(String message, Throwable cause) {

        super(message, cause);
    }
}
