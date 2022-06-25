package org.example.models;

import java.util.Objects;

/**
 * Util class to represent a vehicleType book at an hour
 */
class VehicleTimeKey {
    String vehicleType;
    int startHour;
    VehicleTimeKey(String vehicleType, int startHour) {
        this.vehicleType = vehicleType;
        this.startHour = startHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VehicleTimeKey that = (VehicleTimeKey) o;
        return startHour == that.startHour && Objects.equals(vehicleType, that.vehicleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, startHour);
    }
}