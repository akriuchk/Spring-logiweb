package com.akriuchk.application.truck.local;

import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;
import com.akriuchk.application.truck.ITruckService;
import com.akriuchk.application.truck.Truck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service("localService")
public class TruckServiceLocal implements ITruckService{
    private static final Logger log = LoggerFactory.getLogger(TruckServiceLocal.class);
    private static String validationRegex = "[A-Z0-9]{8}";


    public TruckServiceLocal() {
    }

    public List<Truck> getAll() {
        List<Truck> truckList = TruckRepository.getTrucks();
        return truckList;
    }

    public Truck getTruckByID(Long id) {
        return TruckRepository.getById(id);
    }

    /**
     * Adding truck to the available after checking it's registration number
     *
     * @param truck truck to be added
     * @return "true" - successfully, "false" - failed, already registered
     * @throws ParseException truck number has invalid registration number
     */
    public long addTruck(Truck truck) throws ParseException {
        String truckNumber = truck.getRegistrationNumber();
        if (!truckNumber.matches(validationRegex)) {
            log.info("[{}] does not matches validation regex {}", truckNumber, validationRegex);
            throw new ParseException("Truck registration number '" + truckNumber + "' is not valid", 0);
        }
        return TruckRepository.addTruck(truck).getId();
    }

    /**
     * Update track parameters by it's id and passed truckDTO
     *
     * @param id       key to find truck
     * @param incomingTruck incoming DTO with updated parameters
     * @return updated Truck object
     * @throws UpdateException if no changes were made, throw and this exception
     */
    public Truck updateTruck(Long id, Truck incomingTruck) throws UpdateException {
        log.info("Processing truck id[{}] update request", id);
        //todo validate incoming request with parameters types
        Truck truck = TruckRepository.getById(id);
        if (null == truck) {
            throw new UpdateException("Truck id[" + id + "] not found.");
        }
        Truck newTruck = new Truck(id, incomingTruck.getRegistrationNumber(),
                incomingTruck.getShiftSize(),
                incomingTruck.getCapacity(),
                incomingTruck.getCondition(),
                incomingTruck.getCurrentCity());
        truck = TruckRepository.replaceByID(id, newTruck);

        if (truck.equals(newTruck)) {
            log.info("Truck id[{}] update success", id);
            return truck;
        } else {
            log.info("Truck id[{}] update failed", id);
            throw new UpdateException("Update failed");
        }
    }

    /**
     * delete truck from repository
     *
     * @param id key to find truck
     * @return result of repository method
     * @throws NotFoundException throw an error, if repository doesn't have requested truck
     */
    public boolean deleteTruck(Long id) throws NotFoundException {
        log.info("Processing deletion request of Truck id[{}]", id);
        Truck truck = TruckRepository.getById(id);
        if (truck != null) {
            boolean isDeleted = TruckRepository.deleteTruck(truck);
            log.info("Truck id[{}] deleted: {}",truck.getId(), isDeleted);
            return isDeleted;
        } else {
            log.info("Truck id[{}] not found", id);
            throw new NotFoundException("Truck id[" + id + "] not found.");
        }
    }

    /**
     * find 5 new trucks with required capacity
     *
     * @param cargoMaxWeightKg weigth of cargo, which must be transfered
     * @return List of 5 trucks, which have at least required capacity
     */
    public List<Truck> findTruckByCapacity(long cargoMaxWeightKg, int resultSize) {
        double cargoMaxWeightTonnes = cargoMaxWeightKg * 0.001;
        log.info("Search Truck for required capacity: {} (t)", cargoMaxWeightTonnes);
        String condition = "new";
        List<Truck> foundTrucks = TruckRepository.getByStateCapacityFree(condition, cargoMaxWeightTonnes, resultSize);
        log.info("Found {} Trucks with capacity for order weight {} (t)", foundTrucks.size(), cargoMaxWeightTonnes);

        if (foundTrucks.size() != 0) {
            return foundTrucks;
        } else {
            throw new NotFoundException("No trucks found by parameters - condition:" + condition + "; capacity:" + cargoMaxWeightKg);

        }
    }
}
