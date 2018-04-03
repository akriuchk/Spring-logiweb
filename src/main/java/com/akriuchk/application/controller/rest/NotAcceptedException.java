package com.akriuchk.application.controller.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotAcceptedException extends RuntimeException{
    public NotAcceptedException(String message) {
        super(message);
    }
}
