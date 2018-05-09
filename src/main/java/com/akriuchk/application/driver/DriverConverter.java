package com.akriuchk.application.driver;

import org.springframework.stereotype.Component;

@Component
public class DriverConverter {
    public DriverConverter() {
        //empty constructor
    }

    Object convert(Object source, Class<?> targetType) {
        if (source.getClass() == targetType) {
            return source;
        } else if (targetType == DriverDto.class) {
            Driver sourceDriver = (Driver) source;
            return new DriverDto(sourceDriver.getFirstName(),
                    sourceDriver.getSurname(),
                    sourceDriver.getRegistrationNumber(),
                    sourceDriver.getHoursInCurrentMonthWorks(),
                    sourceDriver.getStatus(),
                    sourceDriver.getCurrentCity(),
                    sourceDriver.getCurrentTruck()
            );
        } else {
            DriverDto driverDto = (DriverDto) source;
            return new Driver(
                    driverDto.getFirstName(),
                    driverDto.getSurname(),
                    driverDto.getRegistrationNumber(),
                    driverDto.getHoursInCurrentMonthWorks(),
                    driverDto.getStatus(),
                    driverDto.getCurrentCity(),
                    driverDto.getCurrentTruck()
            );
        }
    }
}
