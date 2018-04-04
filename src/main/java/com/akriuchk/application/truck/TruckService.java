package com.akriuchk.application.truck;

import com.akriuchk.application.controller.UpdateException;
import com.akriuchk.application.controller.rest.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Random;

@Service
public class TruckService {
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);
    private static String validationRegex = "[A-Z0-9]{8}";


    public TruckService() {
    }

    public List<Truck> getAll() {
        List<Truck> truckList = TruckRepository.getTrucks();
        return truckList;
    }

    public Truck getTruck(Long id) {
        return TruckRepository.getById(id);
    }

    /**
     * Adding truck to the available after checking it's registration number
     *
     * @param truck truck to be added
     * @return "1" - successfully, "-1" - failed, already registered
     * @throws ParseException truck number has invalid registration number
     */
    public int addTruck(Truck truck) throws ParseException {
        String truckNumber = truck.getRegisterNumber();
        if (!truckNumber.matches(validationRegex)) {
            log.info("[{}] does not matches validation regex {}", truckNumber, validationRegex);
            throw new ParseException("Truck registration number '" + truckNumber + "' is not valid", 0);
        }
        return TruckRepository.addTruck(truck);
    }

    /**
     * Convert TruckDTO to Truck domain and add it to repo
     *
     * @param truckDTO incoming truck domain
     * @return ID of newly added truck
     * @throws ParseException truck number has invalid registration number
     */
    public Long addTruck(TruckDTO truckDTO) throws ParseException {
        Long id = new Random().nextLong();
        id = (id < 0) ? id * (-1) : id;
        log.info("Generated id for {} is {}", truckDTO.getRegisterNumber(), id);

        Truck newTruck = new Truck(id, truckDTO.getRegisterNumber(),
                truckDTO.getShiftSize(),
                truckDTO.getCapacity(),
                truckDTO.getCondition(),
                truckDTO.getCurrentCity());
        if (addTruck(newTruck) == -1) {
            throw new ParseException("Truck [" + truckDTO.getRegisterNumber() + "] already registered", 0);
        }
        return id;
    }

    /**
     * Update track parameters by it's id and passed truckDTO
     *
     * @param id        key to find truck
     * @param truckDTO  incoming DTO with updated parameters
     * @return  updated Truck object
     * @throws UpdateException if no changes were made, throw and this exception
     */
    public Truck updateTruck(Long id, TruckDTO truckDTO) throws UpdateException {
        log.info("Processing truck id[{}] update request", id);
        //todo validate incoming request with parameters types
        Truck truck = TruckRepository.getById(id);
        if (null == truck) {
            throw new UpdateException("Truck id[" + id + "] not found.");
        }
        Truck newTruck = new Truck(id, truckDTO.getRegisterNumber(),
                truckDTO.getShiftSize(),
                truckDTO.getCapacity(),
                truckDTO.getCondition(),
                truckDTO.getCurrentCity());
        truck = TruckRepository.replaceByID(id, newTruck);

        if (truck.equals(newTruck)) {
            log.info("Truck id[{}] update success{}");
            return truck;
        } else {
            log.info("Truck id[{}] update failed{}");
            throw new UpdateException("Update failed");
        }
    }

    /**
     * delete truck from repository
     *
     * @param id key to find truck
     * @return  result of repository method
     * @throws NotFoundException throw an error, if repository doesn't have requested truck
     */
    public boolean deleteTruck(Long id) throws NotFoundException{
        log.info("Processing deletion request of Truck id[{}]", id);
        Truck truck = TruckRepository.getById(id);
        if (truck != null) {
            boolean isDeleted = TruckRepository.deleteTruck(truck);
            log.info("Truck id[{}] deleted: {}", isDeleted);
            return isDeleted;
        } else {
            log.info("Truck id[{}] not found", id);
            throw new NotFoundException("Truck id[" + id + "] not found.");
        }
    }
}
