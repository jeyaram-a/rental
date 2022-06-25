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

    // Threshold for dynamic pricing
    private double dynamicPricingThreshold=0.8;

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

    private Set<String> getCurrentlyBookedVehicleIds(String vehicleType, int start, int end) {
        VehicleTimeKey iterator = new VehicleTimeKey(vehicleType, start);
        Set<String> currentlyBooked = new HashSet<>();
        while (iterator.startHour < end) {
            currentlyBooked.addAll(bookings.getOrDefault(iterator, new ArrayList<>()));
            iterator.startHour += 1;
        }
        return currentlyBooked;
    }

    private void updateBookings(Vehicle vehicle, int start, int end) {
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

    private boolean applicableForDynamicPricing(int total, int available) {
        return (1.0 - (double)available/total) >= this.dynamicPricingThreshold;
    }

    public double book(String vehicleType, int start, int end) {
        List<Vehicle> sameType = typeVehiclesMap.getOrDefault(vehicleType, new ArrayList<>());
        int totalVehicleTypeCount = sameType.size();
        // Requested vehicleType not available
        if (totalVehicleTypeCount == 0)
            return -1;
        int maxOverlap = 0;
        VehicleTimeKey iterator = new VehicleTimeKey(vehicleType, start);
        // finding max overlap
        // Assume there are bookings for car [1,4], [2,4], [3,4]. Atleast 3 cars are required for these bookings
        // New request is for [1,5]
        // If the branch has only 3 cars this new request cannot be satisfied
        while (iterator.startHour < end) {
            maxOverlap = Math.max(bookings.getOrDefault(iterator, new ArrayList<>()).size(), maxOverlap);
            iterator.startHour += 1;
        }

        if (maxOverlap >= totalVehicleTypeCount)
            return -1;

        Set<String> currentlyBooked = getCurrentlyBookedVehicleIds(vehicleType, start, end);

        List<Vehicle> minCostList = sameType.stream()
                .filter(vehicle -> !currentlyBooked.contains(vehicle.vehicleId))
                .sorted(Comparator.comparingInt(x -> x.price))
                .collect(Collectors.toList());
        if (minCostList.size() == 0)
            return -1;
        Vehicle cheapest = minCostList.get(0);
        updateBookings(cheapest, start, end);
        int price =  cheapest.price * (end - start);
        // Assumption: 10% is for the total price. Not for the hour.
        // Eg: if price is 400 after dynamic pricing it becomes 440 and not 410
        if(applicableForDynamicPricing(totalVehicleTypeCount, minCostList.size())) {
            return price + .10*price;
        }
        return price;
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
