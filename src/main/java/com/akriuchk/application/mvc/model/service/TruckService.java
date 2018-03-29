package com.akriuchk.application.mvc.model.service;

import com.akriuchk.application.mvc.model.persistence.repository.TruckRepository;
import com.akriuchk.application.mvc.model.persistence.servicePOJOs.Truck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TruckService {
    private static final Logger log = LoggerFactory.getLogger(TruckService.class);


    public TruckService() {
    }

    public List<Truck> getAll() {
        log.info("TruckService -> getAll from Repository");
        List<Truck> truckList = TruckRepository.getTrucks();
        return truckList;
    }
}
