package com.akriuchk.application.order.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private long id;

    private String state;

    private List<Long> waypoints;

    private long assignedTruck;

    private java.sql.Timestamp created;

    private java.sql.Timestamp updated;
}
