package com.akriuchk.application.driver;

import com.akriuchk.application.domain.AbstractDao;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class DriverDao extends AbstractDao<Long, Driver> {

    public void saveDriver(Driver driver) {
        persist(driver);
    }

    public void deleteDriverById(long id) {
        super.delete(getByKey(id));
    }

    public Driver findDriverByNumber(Long number) {
        CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
        CriteriaQuery<Driver> criteriaQuery = criteriaBuilder.createQuery(Driver.class);
        Root<Driver> from = criteriaQuery.from(Driver.class);

        ParameterExpression<Long> numberParameter = criteriaBuilder.parameter(Long.class);
        criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("registration_number"), number));

       return proceedTypedQ(criteriaQuery, numberParameter, number).get(0);
    }


    @SuppressWarnings("unchecked")
    public List<Driver> findDriversByCurrentWorkout(int requiredHours) {
        CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
        CriteriaQuery<Driver> criteriaQuery = criteriaBuilder.createQuery(Driver.class);
        Root<Driver> from = criteriaQuery.from(Driver.class);

        ParameterExpression<Integer> hoursParameter = criteriaBuilder.parameter(Integer.class, "hoursParameter");
        criteriaQuery.select(from).where(criteriaBuilder.lessThan(from.get("hours_in_current_month_works"), requiredHours));

        TypedQuery<Driver> typedQuery = getEntityManager().createQuery(criteriaQuery);
        typedQuery.setParameter(hoursParameter, requiredHours);
        return typedQuery.getResultList();

//        return proceedTypedQ(criteriaQuery, hoursParameter, requiredHours);
    }

}
