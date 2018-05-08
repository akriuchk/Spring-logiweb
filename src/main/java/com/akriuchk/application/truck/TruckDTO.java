package com.akriuchk.application.truck;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class TruckDTO {

    private String registrationNumber;
    private int shiftSize;
    private int capacity;
    private String condition;
    private String currentCity;

    public TruckDTO(String registrationNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
        this.registrationNumber = registrationNumber;
        this.shiftSize = shiftSize;
        this.capacity = initialCapacity;
        this.condition = initialCondition;
        this.currentCity = initialCity;
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
