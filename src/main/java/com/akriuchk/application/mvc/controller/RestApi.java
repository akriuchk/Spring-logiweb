package com.akriuchk.application.mvc.controller;

import com.akriuchk.application.mvc.model.persistence.servicePOJOs.Truck;
import com.akriuchk.application.mvc.model.service.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApi {

    @Autowired
    TruckService truckService;

    @RequestMapping(value = "/trucks", method = RequestMethod.GET)
    public List<Truck> getAllTrucks() {
        return truckService.getAll();
    }

}
