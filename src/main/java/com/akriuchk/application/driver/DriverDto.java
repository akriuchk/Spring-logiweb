package com.akriuchk.application.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverDto {

    private long id;

    private String firstName;

    private String surname;

    private long registrationNumber;

    private int hoursInCurrentMonthWorks;

    private String status;

    private String currentCity;

    private String currentTruck;
}
