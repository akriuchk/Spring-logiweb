package com.akriuchk.application.order.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private String publicId;

    private String state;

    private String cargo;

    private String assignedTruckNumber;

    private String loadCity;

    private String uploadCity;
}
