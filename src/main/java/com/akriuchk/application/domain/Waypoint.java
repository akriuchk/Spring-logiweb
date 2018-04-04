package com.akriuchk.application.domain;

public class Waypoint {
    private Long id;
    private String city;
    private Cargo cargo;
    private OperationType operationType;

    public Waypoint(Long id, String city, Cargo cargo, OperationType operationType) {
        this.id = id;
        this.city = city;
        this.cargo = cargo;
        this.operationType = operationType;
    }

    public Long getId() {
        return id;
    }

    public String getCity() {
        return city;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    private interface OperationTypeInterface {
        String getOperationType();
    }

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
}
