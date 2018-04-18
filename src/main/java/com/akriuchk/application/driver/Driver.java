package com.akriuchk.application.driver;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "drivers", schema = "logiweb")
@Getter @Setter @NoArgsConstructor
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "first_name")
    @NonNull
    private String firstName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "registration_number", unique = true)
    private long registrationNumber;

    @Column(name = "hours_in_current_month_works")
    private int hoursInCurrentMonthWorks;

    @Column(name = "status")
    private String status;

    @Column(name = "current_city")
    private String currentCity;

    @Column(name = "current_truck")
    private String currentTruck;

    @CreationTimestamp
    @Column(name = "created")
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public Driver(String firstName, String surname, long registrationNumber, int hoursInCurrentMonthWorks, String status, String currentCity, String currentTruck) {
        this.firstName = firstName;
        this.surname = surname;
        this.registrationNumber = registrationNumber;
        this.hoursInCurrentMonthWorks = hoursInCurrentMonthWorks;
        this.status = status;
        this.currentCity = currentCity;
        this.currentTruck = currentTruck;
    }

    public Driver(long id, String firstName, String surname, long registrationNumber, int hoursInCurrentMonthWorks, String status, String currentCity, String currentTruck) {
        this(firstName, surname, registrationNumber, hoursInCurrentMonthWorks, status, currentCity, currentTruck);
        this.id = id;
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
