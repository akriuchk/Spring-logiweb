package com.akriuchk.application.truck;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;

@Component
public class TruckConverter {

    public TruckConverter() {
        //empty constructor
    }

    /**
     * Convert source object to new object of targetType clazz
     *
     * @param source     source object for converting
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
                    sourceTruck.getRegistrationNumber(),
                    sourceTruck.getShiftSize(),
                    sourceTruck.getCapacity(),
                    sourceTruck.getCondition(),
                    sourceTruck.getCurrentCity());
        } else {
            TruckDTO truckDTO = (TruckDTO) source;
            return new Truck(
//                    truckDTO.getId(),
                    truckDTO.getRegistrationNumber(),
                    truckDTO.getShiftSize(),
                    truckDTO.getCapacity(),
                    truckDTO.getCondition(),
                    truckDTO.getCurrentCity());
        }
    }

    List<Truck> convertDtoList(List<TruckDTO> sourceList, Class<?> targetType) {
        if (sourceList.get(0).getClass() == targetType) {
            return Collections.EMPTY_LIST;
        } else {
            return sourceList.stream()
                    .map(truckdto -> convert(truckdto, Truck.class))
                    .collect(Collector.of(
                            ArrayList::new,
                            (list, truck) -> list.add((Truck) truck),
                            (list1, list2) -> {
                                list1.addAll(list2);
                                return list1;
                            })
                    );
        }
    }

    List<TruckDTO> convertList(List<Truck> sourceList, Class<?> targetType) {
        if (sourceList.get(0).getClass() == targetType) {
            return Collections.EMPTY_LIST;
        } else {
            return sourceList.stream()
                    .map(truck -> convert(truck, TruckDTO.class))
                    .collect(Collector.of(
                            ArrayList::new,
                            (list, truck) -> list.add((TruckDTO) truck),
                            (list1, list2) -> {
                                list1.addAll(list2);
                                return list1;
                            })
                    );
        }
    }
}
