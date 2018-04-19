package com.akriuchk.application.order.order;

import com.akriuchk.application.order.Waypoint;
import com.akriuchk.application.truck.Truck;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.List;

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

    @Enumerated(EnumType.STRING)
    private OrderState state;

    @OneToMany(targetEntity = Waypoint.class)
    private List<Waypoint> listOfWaypoints;

    @OneToOne(targetEntity = Truck.class)
    private Truck assignedTruck;

    @CreationTimestamp
    @Column(name = "created")
    private java.sql.Timestamp created;

    @UpdateTimestamp
    @Column(name = "updated")
    private java.sql.Timestamp updated;

    public Order(Long id, OrderState state, List<Waypoint> listOfWaypoints) {
        this.id = id;
        this.state = state;
        this.listOfWaypoints = listOfWaypoints;
    }

    public enum OrderState implements OrderStateInterface {

        COMPLETED("Completed"),
        INWORK("Processing");
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


