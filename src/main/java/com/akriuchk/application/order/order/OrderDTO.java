package com.akriuchk.application.order.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private long id;

    private String state;

    private long cargo;

    private long assignedTruck;

    private java.sql.Timestamp created;

    private java.sql.Timestamp updated;
}
