package com.akriuchk.application.order.order;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ValidationException;
import java.util.List;

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

    private void validateOrder(Order order) throws ValidationException {
        String errorText = "Check order %s - %s point missed";
        if (order.getLoadCity().isEmpty() || order.getLoadCity() == null) {
            errorText = String.format(errorText, order.getPublicId(), "Load");
            throw new ValidationException(errorText);
        }
        if (order.getUploadCity().isEmpty() || order.getUploadCity() == null) {
            errorText = String.format(errorText, order.getPublicId(), "Upload");
            throw new ValidationException(errorText);
        }
    }

}
