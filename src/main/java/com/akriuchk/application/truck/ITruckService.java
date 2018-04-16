package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;

import java.text.ParseException;
import java.util.List;

public interface ITruckService {

    Truck getTruckByID(Long id);

    long addTruck(Truck truck) throws ParseException;

    Truck updateTruck(Long id, Truck truck) throws UpdateException;

    boolean deleteTruck(Long id) throws NotFoundException;

    List<Truck> findTruckByCapacity(long cargoMaxWeightKg, int resultSize);
}
