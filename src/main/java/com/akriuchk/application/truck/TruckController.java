package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.UpdateException;
import com.akriuchk.application.controller.exception.NotAcceptedException;
import com.akriuchk.application.controller.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {
    private static final Logger log = LoggerFactory.getLogger(TruckController.class);

    private TruckDao truckService;

    @Autowired
    TruckController(TruckDao truckService) {
        this.truckService = truckService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getHello() {
        return "Hello there!";
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Truck> getAllTrucks() {
        return truckService.findAllTrucks();
    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.GET)
    ResponseEntity<Truck> getTruckById(@PathVariable Long truckId) {
        Truck truck = truckService.getById(truckId);
        if (null != truck) {
            return ResponseEntity.ok(truck);
        } else {
            throw new NotFoundException("Truck id[" + truckId + "] not found");
        }
    }

//    @RequestMapping(value = "/", method = RequestMethod.POST)
//    ResponseEntity addNewTruck(@RequestBody TruckDTO inputTruckDTO) {
//        Long truckId;
//        try {
//            truckId = truckService.saveTruck(inputTruckDTO);
//            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
//                    .path("/{id}")
//                    .buildAndExpand(truckId).toUri();
//            log.info("Generated URI for new truck id[{}]: {}", truckId, location);
//            return ResponseEntity.created(location).build();
//        } catch (ParseException e) {
//            throw new NotAcceptedException(e.getMessage());
//        }
//    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.PUT)
    ResponseEntity<Truck> updateTruck(@PathVariable Long truckId, @RequestBody TruckDTO inputTruckDTO) {
        try {
            Truck truck = truckService.(truckId, inputTruckDTO);
            return ResponseEntity.accepted().body(truck);
        } catch (UpdateException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.DELETE)
    ResponseEntity deleteTruck(@PathVariable Long truckId) {
        try {
            truckService.deleteTruckBySsn(truckId);
        } catch (NotFoundException e) {
            throw e;
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<List<Truck>> getTruckByMinCapacity(@RequestParam int minCapacityKg) {
        List<Truck> foundTrucks = truckService.findTrucksByCapacity(minCapacityKg);
        return ResponseEntity.ok().body(foundTrucks);
    }
}
