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

}
