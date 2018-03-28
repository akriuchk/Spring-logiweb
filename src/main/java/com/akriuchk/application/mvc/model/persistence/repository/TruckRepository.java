package com.akriuchk.application.mvc.model.persistence.repository;


import com.akriuchk.application.mvc.model.persistence.servicePOJOs.Truck;

import java.util.Arrays;
import java.util.List;

public class TruckRepository {
    private static List<Truck> truckRepo = Arrays.asList(
            new Truck("7PPDUAVC", 2, 20, "new", "Saint-Petersburg"),
            new Truck("XGTBW4BJ", 2, 20, "new", "Saint-Petersburg"),
            new Truck("TF8PLK8H", 2, 20, "new", "Saint-Petersburg"),
            new Truck("AYJ32ZAK", 2, 20, "new", "Saint-Petersburg"),
            new Truck("Y6WYZ5E8", 2, 20, "new", "Saint-Petersburg"),
            new Truck("P9VNMJ88", 2, 20, "new", "Saint-Petersburg")
    );

    public static List<Truck> getTrucks() {
        return truckRepo;
    }

}
