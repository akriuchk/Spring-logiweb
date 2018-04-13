package com.akriuchk.application.truck;

import java.util.Objects;

public class TruckDTO {

    private long id;
    private String registrationNumber;
    private int shiftSize;
    private int capacity;
    private String condition;
    private String currentCity;

    public TruckDTO() {
    }

    public TruckDTO(String registrationNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
        this.registrationNumber = registrationNumber;
        this.shiftSize = shiftSize;
        this.capacity = initialCapacity;
        this.condition = initialCondition;
        this.currentCity = initialCity;
    }

    public TruckDTO(long id, String registrationNumber, int shiftSize, int capacity, String condition, String currentCity) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.shiftSize = shiftSize;
        this.capacity = capacity;
        this.condition = condition;
        this.currentCity = currentCity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public void setShiftSize(int shiftSize) {
        this.shiftSize = shiftSize;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
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
        TruckDTO truck = (TruckDTO) o;
        return (shiftSize == truck.shiftSize &&
                capacity == truck.capacity &&
                registrationNumber.equals(truck.registrationNumber));
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber, shiftSize, capacity, condition, currentCity);
    }
}
