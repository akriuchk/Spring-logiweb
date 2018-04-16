package com.akriuchk.application.order;

import com.akriuchk.application.truck.Truck;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
public class Order {
    private long id;
    private OrderState state;
    private List<Waypoint> listOfWaypoints;
    private Truck assignedTruck;


    public Order(Long id, OrderState state, List<Waypoint> listOfWaypoints) {
        this.id = id;
        this.state = state;
        this.listOfWaypoints = listOfWaypoints;
    }

    public enum OrderState implements OrderStateInterface {

        COMPLETED("Completed"),
        INWORK("InWork");
        private final String type;

        OrderState(String type) {
            this.type = type;
        }

        @Override
        public String getOrderState() {
            return type;
        }
    }

    private interface OrderStateInterface {
        String getOrderState();
    }

}


