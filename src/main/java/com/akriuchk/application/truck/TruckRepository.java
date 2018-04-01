package com.akriuchk.application.truck;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TruckRepository {
    private static List<Truck> truckRepo = Arrays.asList(
            new Truck(new Random().nextLong(), "7PPDUAVC", 2, 20, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "XGTBW4BJ", 2, 20, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "TF8PLK8H", 2, 20, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "AYJ32ZAK", 2, 20, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "Y6WYZ5E8", 2, 20, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "P9VNMJ88", 2, 20, "new", "Saint-Petersburg")
    );
    private static ArrayList<Truck> truckArrayList = new ArrayList<>(truckRepo);

    static List<Truck> getTrucks() {
        return truckArrayList;
    }

    /**
     * Method adds new truck to repo,
     * new truck checked by registerNumber
     *
     * @param truck new truck to be added
     * @return code about result of addition
     * return "1" - successfully
     * return "-1" - truck already registered in repository
     */
    static int addTruck(Truck truck) {
        if (!truckArrayList.contains(truck)) {
            truckArrayList.add(truck);
            return 1;
        } else {
            return -1;
        }
    }


    static Truck getById(Long id) {
        return truckArrayList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().orElse(null);
    }

    static Truck replaceByID(Long id, Truck newTruck) {
//        Optional<Truck> truck = truckArrayList.stream()
//                .filter(t -> t.getId().equals(id))
//                .findAny();
//        if (truck.isPresent()) {
        Truck truck = getById(id);
        if (null != truck) {
            int index = truckArrayList.indexOf(truck);
            truckArrayList.set(index, newTruck);
            return truckArrayList.get(index);
        }
        return truck;
    }

    static boolean deleteTruck(Truck truck) {
        return truckArrayList.remove(truck);
    }

}
