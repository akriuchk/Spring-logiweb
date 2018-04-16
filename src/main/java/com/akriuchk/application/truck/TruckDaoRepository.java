package com.akriuchk.application.truck;

import com.akriuchk.application.domain.AbstractDao;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository()
public class TruckDaoRepository extends AbstractDao<Long, Truck> {

    public Truck getByKey(long id) {
        return super.getByKey(id);
    }

    public void saveTruck(Truck truck) {
        persist(truck);
    }

    public void deleteTruckById(long id) {
        super.delete(getByKey(id));
    }

    public Truck findTruckByNumber(String number) {
        CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
        CriteriaQuery<Truck> criteriaQuery = criteriaBuilder.createQuery(Truck.class);
        Root<Truck> from = criteriaQuery.from(Truck.class);

        ParameterExpression<String> numberParameter = criteriaBuilder.parameter(String.class);
        criteriaQuery.select(from).where(criteriaBuilder.equal(from.get("register_number"), number));

        return proceedTypedQ(criteriaQuery, numberParameter, number).get(0);
    }


    @SuppressWarnings("unchecked")
    public List<Truck> findTrucksByCapacity(double requiredCapacityTonnes) {
        CriteriaBuilder criteriaBuilder = createCriteriaBuilder();
        CriteriaQuery<Truck> criteriaQuery = criteriaBuilder.createQuery(Truck.class);
        Root<Truck> from = criteriaQuery.from(Truck.class);

        ParameterExpression<Integer> numberParameter = criteriaBuilder.parameter(Integer.class, "numberParameter");
        Integer tonnes = (int) Math.ceil(requiredCapacityTonnes);
        criteriaQuery.select(from).where(criteriaBuilder.greaterThan(from.get("capacity"), tonnes));

        return proceedTypedQ(criteriaQuery, numberParameter, tonnes);
    }

    @SuppressWarnings("unchecked")
    public List<Truck> findTrucksByCapacity1(double requiredCapacityTonnes) {
        Integer tonnes = (int) Math.ceil(requiredCapacityTonnes);
        List<Truck> trucks = getEntityManager().createNamedQuery("getTrucksByCapacity")
                .setParameter("reqCapacity", tonnes)
                .getResultList();
        return trucks;

    }

}
