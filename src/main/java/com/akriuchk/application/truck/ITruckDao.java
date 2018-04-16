package com.akriuchk.application.truck;

import java.util.List;

public interface ITruckDao {
    Truck getByKey(long id);

    void saveTruck(Truck truck);

    void deleteTruckById(long id);

    Truck findTruckByNumber(String number);

    List findTrucksByCapacity(double requiredCapacityKg);
}
