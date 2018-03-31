package com.akriuchk.application.truck;

import java.util.Objects;

public class TruckDTO {

    private String registerNumber;
    private int shiftSize;
    private int capacity;
    private String condition;
    private String currentCity;

    public TruckDTO() {
    }

    public TruckDTO(String registerNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
        this.registerNumber = registerNumber;
        this.shiftSize = shiftSize;
        this.capacity = initialCapacity;
        this.condition = initialCondition;
        this.currentCity = initialCity;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public void setShiftSize(int shiftSize) {
        this.shiftSize = shiftSize;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TruckDTO truckDto = (TruckDTO) o;
        return Objects.equals(registerNumber, truckDto.registerNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerNumber, shiftSize, capacity, condition, currentCity);
    }
}
