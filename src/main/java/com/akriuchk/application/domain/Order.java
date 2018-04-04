package com.akriuchk.application.domain;

import com.akriuchk.application.truck.Truck;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final Long id;
    private OrderState state;
    private List<Waypoint> listOfWaypoints;
    private Truck assignedTruck;


    public Order(Long id, OrderState state, List<Waypoint> listOfWaypoints) {
        this.id = id;
        this.state = state;
        this.listOfWaypoints = listOfWaypoints;
    }

    public Long getId() {
        return id;
    }

    public OrderState getState() {
        return state;
    }

    public List<Waypoint> getListOfWaypoints() {
        return new ArrayList<>(listOfWaypoints);
    }

    public Truck getAssignedTruck() {
        return assignedTruck;
    }

    public void setAssignedTruck(Truck assignedTruck) {
        this.assignedTruck = assignedTruck;
    }

    private interface OrderStateInterface {
        String getOrderState();
    }

    public enum OrderState implements OrderStateInterface {

        COMPLETED("Completed"),
        INWORK("InWork")
        ;
        private final String type;

        OrderState(String type) {
            this.type = type;
        }

        @Override
        public String getOrderState() {
            return type;
        }
    }

}


