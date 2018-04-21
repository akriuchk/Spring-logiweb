package com.akriuchk.application.order.waypoint;

import com.akriuchk.application.domain.AbstractDao;
import org.springframework.stereotype.Repository;

@Repository
public class WaypointDao extends AbstractDao<Long, Waypoint> {

    public void saveWaypoint(Waypoint waypoint) {
        persist(waypoint);
    }

    public void deleteWaypointById(long id) {
        super.delete(getByKey(id));
    }


//    @SuppressWarnings("unchecked")
//    public List<Waypoint> findWaypointsByState(Waypoint state, int resultSize, String sortBy) {
//        List<Waypoint> waypoints = getEntityManager().createNamedQuery("getWaypointsByState")
//                .setParameter("reqState", state)
//                .setMaxResults(resultSize)
//                .getResultList();
//        return waypoints;
//    }

}
