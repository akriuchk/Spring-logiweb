package com.akriuchk.application.truck;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "Trucks", schema = "logiweb")
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Size(min = 6, max = 8)
    @Column(name = "register_number", unique = true)
    private String registerNumber;

    @Column(name = "shift_size")
    private int shiftSize;

    @Column(name = "capacity")
    private int capacity;

    @Column(name = "`condition`")
    private String condition;

    @Column(name = "current_city")
    private String currentCity;

    @CreationTimestamp
    @Column(name = "created")
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated")
    private Date updated;


    public Truck() {
    }

    public Truck(String registerNumber, int shiftSize, int capacity, String condition, String currentCity) {
        this.registerNumber = registerNumber;
        this.shiftSize = shiftSize;
        this.capacity = capacity;
        this.condition = condition;
        this.currentCity = currentCity;
    }

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

    public void setShiftSize(int shiftSize) {
        this.shiftSize = shiftSize;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getCondition() {
        return condition;
    }

    public String getCurrentCity() {
        return currentCity;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
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
        Truck truck = (Truck) o;
        return Objects.equals(id, truck.id) || (shiftSize == truck.shiftSize &&
                capacity == truck.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shiftSize, capacity);
    }

    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", registerNumber='" + registerNumber + '\'' +
                ", shiftSize=" + shiftSize +
                ", capacity=" + capacity +
                ", condition='" + condition + '\'' +
                ", currentCity='" + currentCity + '\'' +
                '}';
    }
}
