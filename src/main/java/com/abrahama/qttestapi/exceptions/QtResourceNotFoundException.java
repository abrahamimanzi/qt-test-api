package com.abrahama.qttestapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class QtResourceNotFoundException extends RuntimeException {
    public QtResourceNotFoundException(String message){
        super(message);
    }
}
