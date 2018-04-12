package com.akriuchk.application.driver;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "drivers", schema = "logiweb")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "registration_number", unique = true)
    private int registrationNumber;

    @Column(name = "hours_in_current_month_works")
    private int hoursInCurrentMonthWorks;

    @Column(name = "status")
    private String status;

    @Column(name = "current_city")
    private String currentCity;

    @Column(name = "current_truck")
    private String currentTruck;

    public Driver() {
    }

    public Driver(String firstName, String surname, int registrationNumber, int hoursInCurrentMonthWorks, String status, String currentCity, String currentTruck) {
        this.firstName = firstName;
        this.surname = surname;
        this.registrationNumber = registrationNumber;
        this.hoursInCurrentMonthWorks = hoursInCurrentMonthWorks;
        this.status = status;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    public Driver(long id, String firstName, String surname, int registrationNumber, int hoursInCurrentMonthWorks, String status, String currentCity, String currentTruck) {
        this(firstName, surname, registrationNumber, hoursInCurrentMonthWorks, status, currentCity, currentTruck);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setRegistrationNumber(int registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getSurname() {
        return surname;
    }

    public int getRegistrationNumber() {
        return registrationNumber;
    }

    public int getHoursInCurrentMonthWorks() {
        return hoursInCurrentMonthWorks;
    }

    public void setHoursInCurrentMonthWorks(int hoursInCurrentMonthWorks) {
        this.hoursInCurrentMonthWorks = hoursInCurrentMonthWorks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentTruck() {
        return currentTruck;
    }

    public void setCurrentTruck(String currentTruck) {
        this.currentTruck = currentTruck;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Driver driver = (Driver) o;
        return id == driver.id ||
                registrationNumber == driver.registrationNumber ||
                Objects.equals(firstName, driver.firstName) &&
                Objects.equals(surname, driver.surname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, firstName, surname, registrationNumber);
    }
}
