package com.mh.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception for cases where a requested resource is not found in the database.
 * The @ResponseStatus annotation causes Spring Boot to respond with the specified
 * HTTP status code (404 NOT FOUND) whenever this exception is thrown from a controller.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
