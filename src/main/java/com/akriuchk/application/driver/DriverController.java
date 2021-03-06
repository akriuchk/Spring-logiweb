package com.akriuchk.application.driver;


import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private static final Logger log = LoggerFactory.getLogger(DriverController.class);

    private DriverService driverService;
    private DriverConverter driverConverter;

    @Autowired
    public DriverController(DriverService driverService, DriverConverter driverConverter) {
        this.driverService = driverService;
        this.driverConverter = driverConverter;
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.GET)
    ResponseEntity<DriverDto> getDriverById(@PathVariable Long driverId) {
        Driver driver = driverService.getByID(driverId);
        if (null != driver) {
            DriverDto newDriverDto = (DriverDto) driverConverter.convert(driver, DriverDto.class);
            return ResponseEntity.ok(newDriverDto);
        } else {
            return ResponseEntity.badRequest().body(new DriverDto());
//            throw new NotFoundException("Driver id[" + driverId + "] not found");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    ResponseEntity<List<DriverDto>> getDriversPaged(@RequestParam(defaultValue = "0") int offset, //todo sort
                                                 @RequestParam(defaultValue = "20") int size) {
        List<Driver> entityList = driverService.getAllPaged(offset, size);
        List<DriverDto> list = new ArrayList<>();
        for (Driver driver : entityList) {
            list.add((DriverDto) driverConverter.convert(driver, DriverDto.class));
        }

        return ResponseEntity.ok(list);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    ResponseEntity addNewDriver(@RequestBody DriverDto inputDriverDTO) {
        Long driverId;

//        try {
            Driver newDriver = (Driver) driverConverter.convert(inputDriverDTO, Driver.class);
            driverId = driverService.addDriver(newDriver);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(driverId).toUri();
            log.info("Generated URI for new driver id[{}]: {}", driverId, location);
            return ResponseEntity.created(location).build();
//        } catch (ParseException e) {
//            throw new NotAcceptedException(e.getMessage());
//        }
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.PUT)
    ResponseEntity<Driver> updateDriver(@PathVariable Long driverId, @RequestBody DriverDto inputDriverDTO) {
        try {
            Driver newDriver = (Driver) driverConverter.convert(inputDriverDTO, Driver.class);
            Driver driver = driverService.updateDriver(driverId, newDriver);
            return ResponseEntity.accepted().body(driver);
        } catch (UpdateException e) {
            throw e;
        }
    }

    @RequestMapping(value = "/{driverId}", method = RequestMethod.DELETE)
    ResponseEntity deleteDriver(@PathVariable Long driverId) {
        try {
            driverService.deleteDriver(driverId);
        } catch (NotFoundException e) {
            throw e;
        }
        return ResponseEntity.noContent().build();
    }
}
