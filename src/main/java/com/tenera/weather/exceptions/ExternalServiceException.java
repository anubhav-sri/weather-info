package com.tenera.weather.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ExternalServiceException extends RuntimeException {
    public ExternalServiceException(HttpStatus statusCode, String details) {
        super(String.format("Got status code %s, with detailed error %s", statusCode, details));
    }
}
