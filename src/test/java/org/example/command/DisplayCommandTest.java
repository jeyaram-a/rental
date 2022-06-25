package org.example.command;

import org.example.booker.MinCostDynamicCostBooker;
import org.example.models.Branch;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DisplayCommandTest {

    Service getService() {
        return new Service("TEST", new MinCostDynamicCostBooker(0.8));
    }

    Branch getBranch() {
        return new Branch("B1", Arrays.asList("CAR", "BIKE", "BUS"));
    }

    void addVehicles(Service service) {
        new AddVehicleCommand("B1", "CAR", "C1", 100).execute(service);
        new AddVehicleCommand("B1", "CAR", "C2", 200).execute(service);
        new AddVehicleCommand("B1", "BIKE", "B1", 100).execute(service);
        new AddVehicleCommand("B1", "BIKE", "B2", 200).execute(service);
        new AddVehicleCommand("B1", "BUS", "BU1", 1000).execute(service);
    }

    @Test
    void BranchNotExistsReturnEmpty() {
        Service service = getService();
        service.addBranch(getBranch());
        DisplayCommand command = new DisplayCommand("B2", 1, 2);
        assertEquals("", command.execute(service).print());
    }

    @Test
    void NoBookingPrintAll() {
        Service service = getService();
        service.addBranch(getBranch());
        addVehicles(service);
        DisplayCommand command = new DisplayCommand("B1", 1, 2);
        assertEquals("C1,B1,C2,B2,BU1", command.execute(service).print());
    }

    @Test
    void BookingTimesOverlappingDisjoint() {
        Service service = getService();
        service.addBranch(getBranch());
        addVehicles(service);
        new BookCommand("B1", "CAR", 2, 5).execute(service);
        assertEquals("C1,B1,C2,B2,BU1", new DisplayCommand("B1", 1, 2).execute(service).print());
        // C1 Already booked
        assertEquals("B1,C2,B2,BU1", new DisplayCommand("B1", 1, 5).execute(service).print());
        // C1 returned @ 5
        assertEquals("C1,B1,C2,B2,BU1", new DisplayCommand("B1", 5, 6).execute(service).print());
        // C2
        new BookCommand("B1", "CAR", 3, 6).execute(service);
        assertEquals("B1,B2,BU1", new DisplayCommand("B1", 3, 7).execute(service).print());
    }

}