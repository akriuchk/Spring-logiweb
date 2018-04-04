package com.akriuchk.application.domain;

public class Cargo {
    private final Long id;
    private String name;
    private int weightKg;
    private Status status;


    public Cargo(Long id, String name, int weightKg, Status status) {
        this.id = id;
        this.name = name;
        this.weightKg = weightKg;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getWeightKg() {
        return weightKg;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    private interface StatusTypeInterface {
        String getOperationType();
    }

    public enum Status implements StatusTypeInterface {

        PREPARED("Prepared"),
        SHIPPED("Shipped"),
        DELIEVERED("Delievered");
        private final String type;

        Status(String type) {
            this.type = type;
        }

        @Override
        public String getOperationType() {
            return type;
        }
    }
}
