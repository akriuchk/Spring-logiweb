package com.akriuchk.application.mvc.model.persistence.servicePOJOs;

public class Truck {

    private final String registerNumber;
    private final int shiftSize;
    private int capacity;
    private String condition;
    private String currentCity;

    public Truck(String registerNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
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

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    //todo equal and hashcode!
}
