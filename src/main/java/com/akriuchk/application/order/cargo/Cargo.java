package com.akriuchk.application.order.cargo;


import com.akriuchk.application.order.Waypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cargos", schema = "logiweb")

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    @Column(name = "weight_kg")
    private int weightKg;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(targetEntity = Waypoint.class)
    private Waypoint loadPoint;

    @OneToOne(targetEntity = Waypoint.class)
    private Waypoint unloadPoint;

    @CreationTimestamp
    @Column(name = "created")
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public enum Status implements StatusTypeInterface {

        PREPARED("Prepared"),
        SHIPPED("Shipped"),
        DELIEVERED("Delievered");
        private final String status;

        Status(String status) {
            this.status = status;
        }

        @Override
        public String getStatus() {
            return status;
        }
    }

    private interface StatusTypeInterface {
        String getStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cargo cargo = (Cargo) o;
        return id == cargo.id &&
                weightKg == cargo.weightKg &&
                Objects.equals(name, cargo.name) &&
                Objects.equals(created, cargo.created);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, weightKg, created);
    }
}
