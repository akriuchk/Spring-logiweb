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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trucks")
public class TruckController {
    private static final Logger log = LoggerFactory.getLogger(TruckController.class);

    private ITruckService truckService;
    private TruckConverter truckConverter;

    @Autowired
    TruckController(ITruckService truckService, TruckConverter truckConverter) {
        this.truckService = truckService;
        this.truckConverter = truckConverter;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getHello() {
        return "Hello there!";
    }

    @RequestMapping(method = RequestMethod.GET)
    List<Truck> getAllTrucks() {
        return truckService.getAll();
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
    ResponseEntity addNewTruck(@RequestBody TruckDTO inputTruckDTO) {
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
    ResponseEntity<Truck> updateTruck(@PathVariable Long truckId, @RequestBody TruckDTO inputTruckDTO) {
        try {
            Truck newTruck = (Truck) truckConverter.convert(inputTruckDTO, Truck.class);
            Truck truck = truckService.updateTruck(truckId, newTruck);
            return ResponseEntity.accepted().body(truck);
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
        List<TruckDTO> foundTrucksDTO = foundTrucks.stream()
                .map(truck -> truckConverter.convert(truck, TruckDTO.class))
                .collect(Collector.of(
                        () -> new ArrayList<TruckDTO>(),
                        (list, truck) -> list.add((TruckDTO) truck),
                        (list1, list2) -> {
                            list1.addAll(list2);
                            return list1;
                        })
                );
        return ResponseEntity.ok().body(foundTrucksDTO);
    }
}
