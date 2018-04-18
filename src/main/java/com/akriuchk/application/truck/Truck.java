package com.akriuchk.application.truck;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(
                name = "getTrucksByCapacity",
                query = "select tr from Truck tr where tr.capacity >= :reqCapacity"
        ),
        @NamedQuery(
                name = "getTrucksByCondition",
                query = "select tr from Truck tr where tr.condition = :reqCondition"
        )}
)

@Entity
@Table(name = "trucks", schema = "logiweb")
@Getter
@Setter
@NoArgsConstructor
public class Truck {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Size(min = 6, max = 8)
    @Column(name = "register_number", unique = true)
    private String registrationNumber;

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
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public Truck(String registrationNumber, int shiftSize, int capacity, String condition, String currentCity) {
        this.registrationNumber = registrationNumber;
        this.shiftSize = shiftSize;
        this.capacity = capacity;
        this.condition = condition;
        this.currentCity = currentCity;
    }

    public Truck(Long id, String registrationNumber, int shiftSize, int initialCapacity, String initialCondition, String initialCity) {
        this(registrationNumber, shiftSize, initialCapacity, initialCondition, initialCity);
        this.id = id;
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
                ", registrationNumber='" + registrationNumber + '\'' +
                ", shiftSize=" + shiftSize +
                ", capacity=" + capacity +
                ", condition='" + condition + '\'' +
                ", currentCity='" + currentCity + '\'' +
                '}';
    }
}
