package com.akriuchk.application.truck;

import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class TruckConverter {

    public TruckConverter() {
    }

    /**
     * Convert source object to new object of targetType clazz
     * @param source source object for converting
     * @param targetType target class of converted object
     * @return new object of targetType clazz
     */
    Object convert(Object source, Class<?> targetType) {
        //todo implement convert() method in DTO class to avoid many if's here
        Objects.equals(source.getClass(), targetType);
        if (source.getClass() == targetType) {
            return source;
        } else if (targetType == TruckDTO.class) {
            Truck sourceTruck = (Truck) source;
            return new TruckDTO(sourceTruck.getId(),
                    sourceTruck.getRegisterNumber(),
                    sourceTruck.getShiftSize(),
                    sourceTruck.getCapacity(),
                    sourceTruck.getCondition(),
                    sourceTruck.getCurrentCity());
        } else {
            TruckDTO truckDTO = (TruckDTO) source;
            return new Truck(
//                    truckDTO.getId(),
                    truckDTO.getRegisterNumber(),
                    truckDTO.getShiftSize(),
                    truckDTO.getCapacity(),
                    truckDTO.getCondition(),
                    truckDTO.getCurrentCity());
        }
    }


}
