package com.akriuchk.application.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestApi {

    @RequestMapping(method = RequestMethod.GET)
    public String getHello() {
        return "Hello there!";
    }
}
