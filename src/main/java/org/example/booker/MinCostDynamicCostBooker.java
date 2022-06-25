package org.example.booker;

import org.example.models.Branch;
import org.example.models.Vehicle;
import org.example.models.VehicleTimeKey;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Booker with the following strategy <br>
 *  1. Books an available vehicle with the minimum price<br>
 *  2. If booked percentage is >= threshold, price is increased by 10%
 */
public class MinCostDynamicCostBooker implements Booker {

    private final double threshold;

    public MinCostDynamicCostBooker(double threshold) {
        this.threshold = 0.8;
    }
    private boolean applicableForDynamicPricing(int total, int available) {
        return (1.0 - (double)available/total) >= this.threshold;
    }

    @Override
    public double book(Branch branch, String vehicleType, int start, int end) {
        List<Vehicle> sameType = branch.getVehiclesOfType(vehicleType);
        int totalVehicleTypeCount = sameType.size();
        // Requested vehicleType not available
        if (totalVehicleTypeCount == 0)
            return -1;
        int maxOverlap = 0;

        // finding max overlap
        // Assume there are bookings for car [1,4], [2,4], [3,4]. Atleast 3 cars are required for these bookings
        // New request is for [1,5]
        // If the branch has only 3 cars this new request cannot be satisfied
        int time = start;
        while (time < end) {
            VehicleTimeKey iterator = new VehicleTimeKey(vehicleType, time);
            maxOverlap = Math.max(branch.getBookings(iterator).size(), maxOverlap);
            time += 1;
        }

        if (maxOverlap >= totalVehicleTypeCount)
            return -1;

        Set<String> currentlyBooked = branch.getCurrentlyBookedVehicleIds(vehicleType, start, end);

        List<Vehicle> minCostList = sameType.stream()
                .filter(vehicle -> !currentlyBooked.contains(vehicle.getId()))
                .sorted(Comparator.comparingInt(Vehicle::getPrice))
                .collect(Collectors.toList());
        if (minCostList.size() == 0)
            return -1;
        Vehicle cheapest = minCostList.get(0);
        branch.updateBookings(cheapest, start, end);
        int price =  cheapest.getPrice() * (end - start);
        // Assumption: 10% is for the total price. Not for the hour.
        // Eg: if price is 400 after dynamic pricing it becomes 440 and not 410
        if(applicableForDynamicPricing(totalVehicleTypeCount, minCostList.size())) {
            return price + .10*price;
        }
        return price;
    }
}
