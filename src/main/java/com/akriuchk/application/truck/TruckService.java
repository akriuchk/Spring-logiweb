package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.UpdateException;
import com.akriuchk.application.controller.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
@Transactional
public class TruckService implements ITruckService {
    private final Logger log = LoggerFactory.getLogger(TruckService.class);
    private static String validationRegex = "[A-Z0-9]{8}";

    private TruckDao truckRepository;

    @Autowired
    public TruckService(TruckDao truckRepository) {
        this.truckRepository = truckRepository;
    }

    @Override
    public List<Truck> getAll() {
        List<Truck> truckList = truckRepository.findAllTrucks();
        return truckList;
    }

    @Override
    public Truck getTruckByID(Long id) {
        return truckRepository.getById(id);
    }

    /**
     * Adding truck to the available after checking it's registration number
     *
     * @param truck truck to be added
     * @return "true" - successfully, "false" - failed, already registered
     * @throws ParseException truck number has invalid registration number
     */
    @Override
    public void addTruck(Truck truck) throws ParseException {
        String truckNumber = truck.getRegisterNumber();
        if (!truckNumber.matches(validationRegex)) {
            log.info("[{}] does not matches validation regex {}", truckNumber, validationRegex);
            throw new ParseException("Truck registration number '" + truckNumber + "' is not valid", 0);
        }
        truckRepository.saveTruck(truck);
        log.info("Save truck", truck);
    }

    /**
     * Convert TruckDTO to Truck domain and add it to repo
     *
     * @param truckDTO incoming truck domain
     * @return ID of newly added truck
     * @throws ParseException truck number has invalid registration number
     */
    @Override
    public long addTruck(TruckDTO truckDTO) throws ParseException {
        log.info("Incoming new truck register number[{}]", truckDTO.getRegisterNumber());

        Truck newTruck = new Truck(truckDTO.getRegisterNumber(),
                truckDTO.getShiftSize(),
                truckDTO.getCapacity(),
                truckDTO.getCondition(),
                truckDTO.getCurrentCity());
        addTruck(newTruck);
        return newTruck.getId();
    }

    /**
     * Update track parameters by it's id and passed truckDTO
     *
     * @param id       key to find truck
     * @param truckDTO incoming DTO with updated parameters
     * @return updated Truck object
     * @throws UpdateException if no changes were made, throw and this exception
     */
    @Override
    public Truck updateTruck(Long id, TruckDTO truckDTO) throws UpdateException {
        log.info("Processing truck id[{}] update request", id);
        //todo validate incoming request with parameters types
        Truck truck = truckRepository.getById(id);
        if (null == truck) {
            throw new UpdateException("Truck id[" + id + "] not found.");
        }

        truck.setCondition(truckDTO.getCondition());
        truck.setCurrentCity(truckDTO.getCurrentCity());
        truck.setRegisterNumber(truckDTO.getRegisterNumber());

        return truck;
    }

    /**
     * delete truck from repository
     *
     * @param id key to find truck
     * @return result of repository method
     * @throws NotFoundException throw an error, if repository doesn't have requested truck
     */
    @Override
    public boolean deleteTruck(Long id) throws NotFoundException {
        log.info("Processing deletion request of Truck id[{}]", id);
        truckRepository.deleteTruckBySsn(id);
        return true;
    }

    /**
     * find 5 new trucks with required capacity
     *
     * @param cargoMaxWeightKg weigth of cargo, which must be transfered
     * @return List of 5 trucks, which have at least required capacity
     */
    @Override
    public List<Truck> findTruckByCapacity(double cargoMaxWeightKg) {
        double cargoMaxWeightTonnes = cargoMaxWeightKg * 0.001;
        log.info("Search Truck for required capacity: {} (t)", cargoMaxWeightTonnes);

        return truckRepository.findTrucksByCapacity(cargoMaxWeightTonnes);
    }
}
