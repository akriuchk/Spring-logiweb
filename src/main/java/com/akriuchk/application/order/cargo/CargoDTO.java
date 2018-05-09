package com.akriuchk.application.order.cargo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {

    private String publicId;

    private String description;

    private int weightKg;

    private String status;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CargoDTO cargo = (CargoDTO) o;
        return weightKg == cargo.weightKg &&
                Objects.equals(description, cargo.description) &&
                Objects.equals(publicId, cargo.getPublicId()) &&
                Objects.equals(status, cargo.status);

    }

    @Override
    public int hashCode() {

        return Objects.hash(publicId, description, weightKg, status);
    }
}
