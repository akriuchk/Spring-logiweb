package com.akriuchk.application.order.cargo;


public class CargoService {

    public static Cargo getByID(long id) {
        Cargo c = new Cargo();
        c.setId(1);
        c.setName("Big box");
        c.setWeightKg(10);

        return c;
    }

}
