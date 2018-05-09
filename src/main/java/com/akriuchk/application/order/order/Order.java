package com.akriuchk.application.order.order;

import com.akriuchk.application.order.cargo.Cargo;
import com.akriuchk.application.truck.Truck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;

@NamedQueries({
        @NamedQuery(
                name = "getOrdersByState",
                query = "select ord from Order ord where ord.state >= :reqState"
        )}
)

@Entity
@Table(name = "orders", schema = "logiweb")
@Getter @Setter
@NoArgsConstructor
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    private String publicId;

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToOne(targetEntity = Cargo.class)
    private Cargo cargo;

    @OneToOne(targetEntity = Truck.class)
    private Truck assignedTruck;

    private String loadCity;

    private String uploadCity;

    @CreationTimestamp
    @Column(name = "created")
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public Order(OrderState state, Cargo cargo) {
        this.state = state;
        this.cargo = cargo;
    }
    public Order(Long id, OrderState state, Cargo cargo) {
        this(state, cargo);
        this.id = id;
    }

    public Order(String publicId, OrderState state, Cargo cargo, Truck assignedTruck, String loadCity, String uploadCity) {
        this.publicId = publicId;
        this.state = state;
        this.cargo = cargo;
        this.assignedTruck = assignedTruck;
        this.loadCity = loadCity;
        this.uploadCity = uploadCity;
    }

    public enum OrderState implements OrderStateInterface {

        COMPLETED("Completed"),
        PROCESSING("Processing");
        private final String type;

        OrderState(String type) {
            this.type = type;
        }

        @Override
        public String getOrderState() {
            return type;
        }
    }

    private interface OrderStateInterface {
        String getOrderState();
    }

}


