package com.akriuchk.application.truck;

import com.akriuchk.application.domain.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository()
public class TruckDaoRepository extends AbstractDao<Long, Truck> implements ITruckDao {


    @Override
    public Truck getByKey(long id) {
        return super.getByKey(id);
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
        criteria.add(Restrictions.eq("register_number", number));
        return (Truck) criteria.uniqueResult();
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Truck> findTrucksByCapacity(double requiredCapacityTonnes) {
        Integer tonnes =  (int)Math.ceil(requiredCapacityTonnes);
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.ge("capacity", tonnes)).list();
    }
}
