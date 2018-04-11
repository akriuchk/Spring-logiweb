package com.akriuchk.application.truck;

import com.akriuchk.application.domain.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public class TruckDaoRepository extends AbstractDao<Long, Truck> implements TruckDao {


    @Override
    public Truck getById(long id) {
        return getByKey(id);
    }

    @Override
    public void saveTruck(Truck truck) {
        persist(truck);
    }

    @Override
    public void deleteTruckById(long id) {
        Query query = getSession().createQuery("delete from Truck where id = :truckid");
        query.setParameter("truckid", id);
        query.executeUpdate();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Truck> findAllTrucks() {
        Criteria criteria = createEntityCriteria();
        return (List<Truck>) criteria.list();
    }

    @Override
    public Truck findTruckByNumber(String number) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("registerNumber", number));
        return (Truck) criteria.uniqueResult();
    }


    @Override
    public List<Truck> findTrucksByCapacity(double requiredCapacityTonnes) {


        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.ge("capacity", Math.ceil(requiredCapacityTonnes))).list();
    }
}
