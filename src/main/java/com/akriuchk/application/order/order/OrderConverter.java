package com.akriuchk.application.order.order;

import com.akriuchk.application.truck.TruckService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

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
                    sourceTruck.getCargo().getId(),
                    sourceTruck.getAssignedTruck().getId(),
                    sourceTruck.getCreated(),
                    sourceTruck.getUpdated());
        } else {
            OrderDTO orderDTO = (OrderDTO) source;
            return new Order(
//                    orderDTO.getId(),
                    Order.OrderState.valueOf(orderDTO.getState()),
                    orderService.getOrderByID(orderDTO.getId()).getListOfWaypoints());
        }
    }

    List<Order> convertDtoList(List<OrderDTO> sourceList, Class<?> targetType) {
        if (sourceList.get(0).getClass() == targetType) {
            return Collections.EMPTY_LIST;
        } else {
            return sourceList.stream()
                    .map(orderdto -> convert(orderdto, Order.class))
                    .collect(Collector.of(
                            ArrayList::new,
                            (list, order) -> list.add((Order) order),
                            (list1, list2) -> {
                                list1.addAll(list2);
                                return list1;
                            })
                    );
        }
    }

    List<OrderDTO> convertList(List<Order> sourceList, Class<?> targetType) {
        if (sourceList.get(0).getClass() == targetType) {
            return Collections.EMPTY_LIST;
        } else {
            return sourceList.stream()
                    .map(truck -> convert(truck, OrderDTO.class))
                    .collect(Collector.of(
                            ArrayList::new,
                            (list, order) -> list.add((OrderDTO) order),
                            (list1, list2) -> {
                                list1.addAll(list2);
                                return list1;
                            })
                    );
        }
    }
}
