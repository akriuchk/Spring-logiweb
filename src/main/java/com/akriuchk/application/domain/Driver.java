package com.akriuchk.application.domain;

public class Driver {
    private String firstName;
    private String surname;
    private int registrationNumber;
    private int hoursInCurrentMonthWorks;
    private String status;
    private String currentCity;
    private String currentTruck;

    public Driver(String firstName, String surname, int registrationNumber, int hoursInCurrentMonthWorks, String status, String currentCity, String currentTruck) {
        this.firstName = firstName;
        this.surname = surname;
        this.registrationNumber = registrationNumber;
        this.hoursInCurrentMonthWorks = hoursInCurrentMonthWorks;
        this.status = status;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
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
}
