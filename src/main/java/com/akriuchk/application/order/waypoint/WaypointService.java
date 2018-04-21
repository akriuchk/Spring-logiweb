package com.akriuchk.application.order.waypoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

@Service
@Transactional
public class WaypointService {
    private final Logger log = LoggerFactory.getLogger(WaypointService.class);

    @Autowired
    public WaypointDao waypointRepository;

    public WaypointService(WaypointDao waypointRepository) {
        this.waypointRepository = waypointRepository;
    }

    public List<Waypoint> getAllPaged(int offset, int size) {
        return waypointRepository.getAllPaged(offset, size);
    }

    public Waypoint getWaypointByID(Long id) {
        return waypointRepository.getByKey(id);
    }

    public long addNewWaypoint(Waypoint waypoint) throws ValidationException {
        //validation
        waypointRepository.saveWaypoint(waypoint);
        return waypoint.getId();
    }

}
