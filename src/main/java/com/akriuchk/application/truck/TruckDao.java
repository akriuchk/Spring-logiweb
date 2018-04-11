package com.akriuchk.application.truck;

import java.util.List;

public interface TruckDao {
    Truck getById(long id);

    void saveTruck(Truck truck);

    void deleteTruckById(long id);

    List<Truck> findAllTrucks();

    Truck findTruckByNumber(String number);

    List<Truck> findTrucksByCapacity(double requiredCapacity);
}
