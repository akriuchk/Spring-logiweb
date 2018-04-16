package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.NotAcceptedException;
import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;
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

    private TruckService truckService;
    private TruckConverter truckConverter;

    @Autowired
    TruckController(TruckService truckService, TruckConverter truckConverter) {
        this.truckService = truckService;
        this.truckConverter = truckConverter;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getAllTrucks() {
        return "redirect:/api/trucks/search" + "/offset=0";
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<TruckDTO>> getDriversPaged(@RequestParam int offset,
                                                   @RequestParam(defaultValue = "20") int size) {
        List<Truck> resultList = truckService.getAllPaged(offset, size);

        return ResponseEntity.ok().body(truckConverter.convertList(resultList, TruckDTO.class));
    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.GET)
    ResponseEntity<TruckDTO> getTruckById(@PathVariable Long truckId) {
        Truck truck = truckService.getTruckByID(truckId);
        if (null != truck) {
            TruckDTO newTruckDto = (TruckDTO) truckConverter.convert(truck, TruckDTO.class);
            return ResponseEntity.ok(newTruckDto);
        } else {
            throw new NotFoundException("Truck id[" + truckId + "] not found");
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity<TruckDTO> addNewTruck(@RequestBody TruckDTO inputTruckDTO) {
        Long truckId;

        try {
            Truck newTruck = (Truck) truckConverter.convert(inputTruckDTO, Truck.class);
            truckId = truckService.addTruck(newTruck);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(truckId).toUri();
            log.info("Generated URI for new truck id[{}]: {}", truckId, location);
            return ResponseEntity.created(location).build();
        } catch (ParseException e) {
            throw new NotAcceptedException(e.getMessage());
        }
    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.PUT)
    ResponseEntity<TruckDTO> updateTruck(@PathVariable Long truckId, @RequestBody TruckDTO inputTruckDTO) {
        try {
            Truck newTruck = (Truck) truckConverter.convert(inputTruckDTO, Truck.class);
            Truck truck = truckService.updateTruck(truckId, newTruck);
            return ResponseEntity.accepted().body((TruckDTO) truckConverter.convert(truck, TruckDTO.class));
        } catch (UpdateException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/{truckId}", method = RequestMethod.DELETE)
    ResponseEntity deleteTruck(@PathVariable Long truckId) {
        try {
            truckService.deleteTruck(truckId);
        } catch (NotFoundException e) {
            throw e;
        }
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    ResponseEntity<List<TruckDTO>> getTruckByMinCapacity(@RequestParam int minCapacityKg,
                                                         @RequestParam(defaultValue = "1") int resultSize) {
        List<Truck> foundTrucks = truckService.findTruckByCapacity(minCapacityKg, resultSize);
        List<TruckDTO> foundTrucksDTO = truckConverter.convertList(foundTrucks, TruckDTO.class);
        return ResponseEntity.ok().body(foundTrucksDTO);
    }
}
