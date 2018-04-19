package com.akriuchk.application.order.order;

import com.akriuchk.application.domain.AbstractDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDao extends AbstractDao<Long, Order> {

    public void saveOrder(Order order) {
        persist(order);
    }

    public void deleteOrderById(long id) {
        super.delete(getByKey(id));
    }


    @SuppressWarnings("unchecked")
    public List<Order> findOrdersByState(Order.OrderState state, int resultSize, String sortBy) {
        List<Order> orders = getEntityManager().createNamedQuery("getOrdersByState")
                .setParameter("reqState", state)
                .setMaxResults(resultSize)
                .getResultList();
        return orders;
    }

}
