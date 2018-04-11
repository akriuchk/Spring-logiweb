package com.akriuchk.application.truck.local;

import com.akriuchk.application.truck.Truck;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("localRepo")
public class TruckRepository {
    private static List<Truck> truckRepo = Arrays.asList(
            new Truck(new Random().nextLong(), "7PPDUAVC", 2, 5, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "XGTBW4BJ", 2, 7, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "TF8PLK8H", 2, 10, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "AYJ32ZAK", 2, 12, "new", "Saint-Petersburg"),
            new Truck(new Random().nextLong(), "Y6WYZ5E8", 2, 15, "new", "Saint-Petersburg"),
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
     * return Truck: with ID != 0 - successfully
     *               with ID == 0 - failed
     */
    static Truck addTruck(Truck truck) {
        if (!truckArrayList.contains(truck)) {
            Long id = new Random().nextLong();
            id = (id < 0) ? id * (-1) : id;
            truck.setId(id);
            truckArrayList.add(truck);
            return truck;
        } else {
            return truck;
        }
    }

    /**
     * Method return Truck, found by its id or null
     *
     * @param id Truck id
     * @return existed truck form repository or null
     */
    static Truck getById(Long id) {
        return truckArrayList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst().orElse(null);
    }

    /**
     * Method to replace truck in repository by id with new truck
     *
     * @param id old Truck id
     * @param newTruck new Truck object
     * @return
     */
    static Truck replaceByID(Long id, Truck newTruck) {
        Truck truck = getById(id);
        int index = truckArrayList.indexOf(truck);
        if (null != truck) {
            truckArrayList.set(index, newTruck);
            return truckArrayList.get(index);
        }
        return truck;
    }

    static boolean deleteTruck(Truck truck) {
        return truckArrayList.remove(truck);
    }

    static List<Truck> getByStateCapacityFree(String condition, double capacity, int resultSize) {
        return truckArrayList.stream()
                .filter(truck -> truck.getCondition().equals(condition) && truck.getCapacity() >= capacity)
                .limit(resultSize)
                .collect(Collectors.toList());
    }

}
