package com.akriuchk.application.controller.rest;

import com.akriuchk.application.truck.Truck;
import com.akriuchk.application.truck.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApi {

    @Autowired
    private TruckService truckService;

    @RequestMapping(method = RequestMethod.GET)
    public String getHello() {
        return "Hello there!";
    }

    @RequestMapping(value = "/trucks", method = RequestMethod.GET)
    public List<Truck> getAllTrucks() {
        return truckService.getAll();
    }
}
