package com.akriuchk.application.order;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    private long id;
    private String name;
    private int weightKg;
    private Status status;

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status implements StatusTypeInterface {

        PREPARED("Prepared"),
        SHIPPED("Shipped"),
        DELIEVERED("Delievered");
        private final String status;

        Status(String status) {
            this.status = status;
        }

        @Override
        public String getStatus() {
            return status;
        }
    }

    private interface StatusTypeInterface {
        String getStatus();
    }
}
