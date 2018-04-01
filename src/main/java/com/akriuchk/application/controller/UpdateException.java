package com.akriuchk.application.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdateException extends RuntimeException{
    public UpdateException(String message) {
        super(message);
    }
}
