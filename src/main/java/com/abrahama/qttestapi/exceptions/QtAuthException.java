package com.abrahama.qttestapi.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class QtAuthException extends RuntimeException {
    public QtAuthException(String message){
        super(message);
    }
}
