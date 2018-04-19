package com.akriuchk.application.order.order;

import com.akriuchk.application.order.Waypoint;
import com.akriuchk.application.truck.TruckService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.stream.Collectors;

@Component
@NoArgsConstructor
public class OrderConverter {

    @Autowired
    private OrderService orderService;

    @Autowired
    private TruckService truckService;

    Object convert(Object source, Class<?> targetType) {
        Objects.equals(source.getClass(), targetType);
        if (source.getClass() == targetType) {
            return source;
        } else if (targetType == OrderDTO.class) {
            Order sourceTruck = (Order) source;
            return new OrderDTO(sourceTruck.getId(),
                    sourceTruck.getState().getOrderState(),
                    sourceTruck.getListOfWaypoints().stream().map(Waypoint::getId).collect(Collectors.toList()),
                    sourceTruck.getAssignedTruck().getId(),
                    sourceTruck.getCreated(),
                    sourceTruck.getUpdated());
        } else {
            OrderDTO orderDTO = (OrderDTO) source;
            return new Order(
                    orderDTO.getId(),
                    Order.OrderState.valueOf(orderDTO.getState()),
                    orderService.getOrderByID(orderDTO.getId()).getListOfWaypoints());
        }
    }
}
