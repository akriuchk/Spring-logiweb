package com.akriuchk.application.truck;

import java.util.Objects;

public class Truck implements Comparable {

    private final Long id;
    private String registerNumber;
    private final int shiftSize;
    private int capacity;
    private String condition;
    private String currentCity;

    public Truck(Long id, String registerNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
        this.id = id;
        this.registerNumber = registerNumber;
        this.shiftSize = shiftSize;
        this.capacity = initialCapacity;
        this.condition = initialCondition;
        this.currentCity = initialCity;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public int getShiftSize() {
        return shiftSize;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCondition() {
        return condition;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void updateCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void updateCondition(String condition) {
        this.condition = condition;
    }

    public void updateCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public Long getId() {
        return id;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Truck truck = (Truck) o;
//        return Objects.equals(registerNumber, truck.registerNumber);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id) || (shiftSize == truck.shiftSize &&
                capacity == truck.capacity &&
                Objects.equals(registerNumber, truck.registerNumber) &&
                Objects.equals(condition, truck.condition) &&
                Objects.equals(currentCity, truck.currentCity));
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerNumber, shiftSize, capacity, condition, currentCity);
    }


    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
