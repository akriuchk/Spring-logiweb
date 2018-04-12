package com.akriuchk.application.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class DriverService {
    private final Logger log = LoggerFactory.getLogger(DriverService.class);

    private DriverDao driverRepository;

    @Autowired
    public DriverService(DriverDao driverRepository) {
        this.driverRepository = driverRepository;
    }

    public List<Driver> getAll() {
        List<Driver> driverList = driverRepository.findAllDrivers();
        return driverList;
    }

    public Driver getByID(long id) {
        return driverRepository.getByKey(id);
    }

    public long addDriver(Driver driver) {
        //todo add validation
        driverRepository.saveDriver(driver);
        log.info("Driver [{},[{}]] saved with id[{}]", driver.getSurname(), driver.getFirstName(), driver.getId());
        return driver.getId();
    }

    public Driver updateDriver(long id, Driver newDriver) {
        Driver driver = driverRepository.getByKey(id);
        //todo how validate?
        driver.setFirstName(newDriver.getFirstName());
        driver.setSurname(newDriver.getSurname());
//        driver.setRegistrationNumber(newDriver.getRegistrationNumber());
        driver.setHoursInCurrentMonthWorks(newDriver.getHoursInCurrentMonthWorks());
        driver.setStatus(newDriver.getStatus());
        driver.setCurrentCity(newDriver.getCurrentCity());
        driver.setCurrentTruck(newDriver.getCurrentTruck());

        return driver;
    }

    public boolean deleteDriver(long id) {
        //todo throw exc
        driverRepository.deleteDriverById(id);
        return true;
    }

    public Driver findByNumber(long id) {
        Driver driver = driverRepository.findDriverByNumber(id);
        return driver;
    }

    public List<Driver> findByWorkingHours(int hours, int resultSize) {
        return driverRepository.findDriversByCurrentWorkout(hours);
    }

}
