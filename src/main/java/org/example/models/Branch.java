package org.example.models;

import java.util.*;
import java.util.stream.Collectors;

public class Branch {
    private final Set<String> availableVehicleTypes = new HashSet<>();
    private final Map<VehicleTimeKey, List<String>> bookings = new HashMap<>();
    private final Map<String, List<Vehicle>> typeVehiclesMap = new HashMap<>();
    private String name;

    public Branch(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addAvailableTypes(List<String> vehicleTypes) {
        availableVehicleTypes.addAll(vehicleTypes);
    }

    public boolean hasVehicleType(String s) {
        return availableVehicleTypes.contains(s);
    }

    public void addVehicles(String vehicleType, String vehicleId, int price) {
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

    public int book(String vehicleType, int start, int end) {
        List<Vehicle> sameType = typeVehiclesMap.getOrDefault(vehicleType, new ArrayList<>());
        int totalVehicleTypeCount = sameType.size();
        if (totalVehicleTypeCount == 0)
            return -1;
        int maxBooked = 0;
        VehicleTimeKey iterator = new VehicleTimeKey(vehicleType, start);

        while (iterator.startHour < end) {
            maxBooked = Math.max(bookings.getOrDefault(iterator, new ArrayList<>()).size(), maxBooked);
            iterator.startHour += 1;
        }

        if (maxBooked >= totalVehicleTypeCount)
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
        return cheapest.price * (end - start);
    }

    public List<String> getAvailableVehicles(int start, int end) {

        Set<String> currentlyBooked = new HashSet<>();

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
