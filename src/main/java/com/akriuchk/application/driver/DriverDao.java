package com.akriuchk.application.driver;

import com.akriuchk.application.domain.AbstractDao;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DriverDao extends AbstractDao<Long, Driver> {

    public void saveDriver(Driver driver) {
        persist(driver);
    }

    public void deleteDriverById(long id) {
        Query query = getSession().createQuery("delete from Driver where id = :driverid");
        query.setParameter("driverid", id);
        query.executeUpdate();
    }

    @SuppressWarnings("unchecked")
    public List<Driver> findAllDrivers() {
        Criteria criteria = createEntityCriteria();
        return (List<Driver>) criteria.list();
    }

    public Driver findDriverByNumber(long number) {
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("registration_number", number));
        return (Driver) criteria.uniqueResult();
    }


    @SuppressWarnings("unchecked")
    public List<Driver> findDriversByCurrentWorkout(int requiredHours) {
        Criteria criteria = createEntityCriteria();
        return criteria.add(Restrictions.le("hours_in_current_month_works", requiredHours)).list();
    }

}
