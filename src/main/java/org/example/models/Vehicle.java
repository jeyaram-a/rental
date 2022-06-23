package org.example.models;

public class Vehicle {
    // can be an enum. But the exhaustive list of types is nto provided in question
    String vehicleType;
    String vehicleId;

    int price;

    Vehicle(String vehicleType, String vehicleId, int price) {
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.price = price;
    }

    public Vehicle type(String type) {
        this.vehicleType = type;
        return this;
    }

    public Vehicle vehicleId(String id) {
        this.vehicleId = id;
        return this;
    }

}
