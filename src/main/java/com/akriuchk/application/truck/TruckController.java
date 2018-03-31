package com.akriuchk.application.truck;

import com.akriuchk.application.controller.rest.NotAcceptedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {

    private TruckService truckService;

    @Autowired
    TruckController(TruckService truckService) {
        this.truckService = truckService;
    }

    @RequestMapping(method = RequestMethod.GET)
    String getHello() {
        return "Hello there!";
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    List<Truck> getAllTrucks() {
        return truckService.getAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity addNewTruck(@RequestBody TruckDTO inputTruckDTO) {
        Long truckId;
        try {
            truckId = truckService.addTruck(inputTruckDTO);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .buildAndExpand(truckId).toUri();
            return ResponseEntity.created(location).build();
        } catch (ParseException e) {
            throw new NotAcceptedException(e.getMessage());
        }

    }
}
