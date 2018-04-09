package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;

import java.text.ParseException;
import java.util.List;

public interface ITruckService {
    List<Truck> getAll();

    Truck getTruckByID(Long id);

    void addTruck(Truck truck) throws ParseException;

    long addTruck(TruckDTO truckDTO) throws ParseException;

    Truck updateTruck(Long id, TruckDTO truckDTO) throws UpdateException;

    boolean deleteTruck(Long id) throws NotFoundException;

    List<Truck> findTruckByCapacity(double cargoMaxWeightKg);
}
