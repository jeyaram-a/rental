package org.example.models;

import java.util.*;
import java.util.stream.Collectors;


public class Branch {
    // Available vehicle types in this branch
    private final Set<String> availableVehicleTypes = new HashSet<>();
    // All the bookings for each hour of the day
    // All cars that are booked @ 1 can be represented as
    // {CAR, 1} -> [C1, C2]
    private final Map<VehicleTimeKey, List<String>> bookings = new HashMap<>();
    // Map of Vehicle Type and vehicles
    // CAR -> [C1, C2]
    private final Map<String, List<Vehicle>> typeVehiclesMap = new HashMap<>();
    private final String name;

    public Branch(String name, List<String> vehicleTypes) {
        this.name = name;
        this.availableVehicleTypes.addAll(vehicleTypes);
    }

    public String getName() {
        return this.name;
    }

    public boolean hasVehicleType(String s) {
        return availableVehicleTypes.contains(s);
    }

    public void addVehicle(String vehicleType, String vehicleId, int price) {
        this.typeVehiclesMap.compute(vehicleType, (k, v) -> {
            if (v == null)
                v = new LinkedList<>();
            v.add(new Vehicle(vehicleType, vehicleId, price));
            return v;
        });
    }

    public Set<String> getCurrentlyBookedVehicleIds(String vehicleType, int start, int end) {
        VehicleTimeKey iterator = new VehicleTimeKey(vehicleType, start);
        Set<String> currentlyBooked = new HashSet<>();
        while (iterator.startHour < end) {
            currentlyBooked.addAll(bookings.getOrDefault(iterator, new ArrayList<>()));
            iterator.startHour += 1;
        }
        return currentlyBooked;
    }

    public List<Vehicle> getVehiclesOfType(String type) {
        return typeVehiclesMap.getOrDefault(type, new ArrayList<>());
    }

    public List<String> getBookings(VehicleTimeKey key) {
        return bookings.getOrDefault(key, new ArrayList<>());
    }

    public void updateBookings(Vehicle vehicle, int start, int end) {
        while (start < end) {
            VehicleTimeKey iterator = new VehicleTimeKey(vehicle.vehicleType, start);
            bookings.compute(iterator, (k, v) -> {
                if (v == null)
                    v = new LinkedList<>();
                v.add(vehicle.vehicleId);
                return v;
            });
            start += 1;
        }
    }

    public List<String> getAvailableVehicles(int start, int end) {

        Set<String> currentlyBooked = new HashSet<>();
        // Get all currently booked for all vehicleTypes
        for (String type : this.typeVehiclesMap.keySet()) {
            currentlyBooked.addAll(getCurrentlyBookedVehicleIds(type, start, end));
        }

        return typeVehiclesMap.values().stream()
                .flatMap(Collection::stream)
                .filter(vehicle -> !(currentlyBooked.contains(vehicle.vehicleId)))
                .sorted(Comparator.comparingInt(x -> x.price))
                .map(vehicle -> vehicle.vehicleId)
                .collect(Collectors.toList());
    }
}
