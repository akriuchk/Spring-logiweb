package com.akriuchk.application.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Waypoint {
    private long id;
    private String city;
    private Cargo cargo;
    private OperationType operationType;

    public enum OperationType implements OperationTypeInterface {

        LOAD("Loading"), UNLOAD("Unload");
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
