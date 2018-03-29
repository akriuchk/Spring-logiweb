package com.akriuchk.application.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class HttpApi {

    @RequestMapping(method = RequestMethod.GET)
    public String helloWorld() {
        return "index";
    }
}
