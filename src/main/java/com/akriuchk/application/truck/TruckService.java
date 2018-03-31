package com.akriuchk.application.truck;

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

}
