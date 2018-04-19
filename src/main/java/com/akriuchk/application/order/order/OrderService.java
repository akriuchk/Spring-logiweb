package com.akriuchk.application.order.order;


import com.akriuchk.application.order.Waypoint;
import com.akriuchk.application.order.cargo.Cargo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    public OrderDao orderRepository;

    public OrderService(OrderDao orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllPaged(int offset, int size) {
        return orderRepository.getAllPaged(offset, size);
    }

    public Order getOrderByID(Long id) {
        return orderRepository.getByKey(id);
    }

    public long addNewOrder(Order order) throws ValidationException {
        //validation
        validateOrder(order);
        orderRepository.saveOrder(order);
        return order.getId();
    }

    private void validateOrder(Order order) throws ValidationException{
        List<Waypoint> waypoints = order.getListOfWaypoints();
        Optional<Cargo> errorCargo = waypoints.stream()
                .map(Waypoint::getCargo)
                .filter(cargo -> cargo.getLoadPoint() == null || cargo.getUnloadPoint() == null)
                .findAny();

        if (errorCargo.isPresent()) {
            Cargo c = errorCargo.get();
            String errorText = "Check cargo %s(%s) - %s point missed";
            if (c.getLoadPoint() == null) {
                errorText = String.format(errorText, c.getId(), c.getName(), "Load");
            }
            if (c.getUnloadPoint() == null) {
                errorText = String.format(errorText, c.getId(), c.getName(), "Unload");
            }
            throw new ValidationException(errorText);
        }
    }

}
