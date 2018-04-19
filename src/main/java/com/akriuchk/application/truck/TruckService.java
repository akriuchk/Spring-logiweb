package com.akriuchk.application.truck;

import com.akriuchk.application.controller.exception.NotFoundException;
import com.akriuchk.application.controller.exception.UpdateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.List;

@Service
@Transactional
public class TruckService {
    private final Logger log = LoggerFactory.getLogger(TruckService.class);

    private TruckDaoRepository truckRepository;

    @Autowired
    public TruckService(TruckDaoRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> getAllPaged(int offset, int size) {
        return truckRepository.getAllPaged(offset, size);
    }

    public Truck getTruckByID(Long id) {
        return truckRepository.getByKey(id);
    }

    /**
     * Adding truck to the available after checking it's registration number
     *
     * @param truck truck to be added
     * @return "true" - successfully, "false" - failed, already registered
     * @throws ParseException truck number has invalid registration number
     */
//    @Override
    public long addTruck(Truck truck) throws ParseException {
        String truckNumber = truck.getRegistrationNumber();
        String validationRegex = "[A-Z0-9]{8}";
        if (!truckNumber.matches(validationRegex)) {
            log.info("[{}] does not matches validation regex {}", truckNumber, validationRegex);
            throw new ParseException("Truck registration number '" + truckNumber + "' is not valid", 0);
        }
        truckRepository.saveTruck(truck);
        log.info("Saved truck {}", truck);
        return truck.getId();
    }

    /**
     * Update track parameters by it's id and passed truck
     *
     * @param id       key to find truck
     * @param updatedTruck incoming truck with updated parameters
     * @return updated Truck object
     * @throws UpdateException if no changes were made, throw and this exception
     */
//    @Override
    public Truck updateTruck(Long id, Truck updatedTruck) throws UpdateException {
        log.info("Processing truck id[{}] update request", id);
        //todo validate incoming request with parameters types
        Truck truck = truckRepository.getByKey(id);
        if (null == truck) {
            throw new UpdateException("Truck id[" + id + "] not found.");
        }

        truck.setCondition(updatedTruck.getCondition());
        truck.setCurrentCity(updatedTruck.getCurrentCity());
        truck.setRegistrationNumber(updatedTruck.getRegistrationNumber());
        return truck;
    }

    /**
     * delete truck from repository
     *
     * @param id key to find truck
     * @return result of repository method
     * @throws NotFoundException throw an error, if repository doesn't have requested truck
     */
//    @Override
    public boolean deleteTruck(Long id) throws NotFoundException {
        log.info("Processing deletion request of Truck id[{}]", id);
        truckRepository.deleteTruckById(id);
        return true;
    }

    /**
     * find 5 new trucks with required capacity
     *
     * @param cargoMaxWeightKg weigth of cargo, which must be transfered
     * @return List of 5 trucks, which have at least required capacity
     */
//    @Override
    public List<Truck> findTruckByCapacity(long cargoMaxWeightKg, int resultSize) {
        double cargoMaxWeightTonnes = cargoMaxWeightKg * 0.001;
        log.info("Search Truck for required capacity: {} (t)", cargoMaxWeightTonnes);

        return truckRepository.findTrucksByCapacity(cargoMaxWeightTonnes, resultSize);
    }
}
