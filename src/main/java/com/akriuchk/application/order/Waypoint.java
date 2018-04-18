package com.akriuchk.application.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "waypoints", schema = "logiweb")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Waypoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String city;

    @OneToMany(targetEntity = Cargo.class)
    private List<Cargo> cargoes;

    @Enumerated(EnumType.STRING)
    private OperationType operationType;

    @CreationTimestamp
    @Column(name = "created")
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public enum OperationType implements OperationTypeInterface {

        LOAD("Load"), UNLOAD("Unload");
        private final String type;

        OperationType(String type) {
            this.type = type;
        }

        @Override
        public String getOperationType() {
            return type;
        }
    }

    private interface OperationTypeInterface {
        String getOperationType();
    }
}
