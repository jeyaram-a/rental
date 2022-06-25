package org.example.booker;

import org.example.command.AddVehicleCommand;
import org.example.command.BookCommand;
import org.example.models.Branch;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class MinCostDynamicCostBookerTest {

    Service getService() {
        return new Service("TEST", new MinCostDynamicCostBooker(0.8));
    }

    Branch getBranch(String branchName) {
        return new Branch(branchName, Arrays.asList("CAR", "BIKE"));
    }

    void addVehicles(Service service, String branchName) {
        new AddVehicleCommand(branchName, "CAR", "C1", 100).execute(service);
        new AddVehicleCommand(branchName, "CAR", "C2", 200).execute(service);
    }
    void add5Cars(Service service, String branchName) {
        new AddVehicleCommand(branchName, "CAR", "C1", 9).execute(service);
        new AddVehicleCommand(branchName, "CAR", "C2", 9).execute(service);
        new AddVehicleCommand(branchName, "CAR", "C3", 9).execute(service);
        new AddVehicleCommand(branchName, "CAR", "C4", 9).execute(service);
        new AddVehicleCommand(branchName, "CAR", "C5", 9).execute(service);
    }


    @Test
    void WithThresholdBonusChargeNotApplied() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        addVehicles(service, "B1");
        assertEquals(400.0, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
    }
    @Test
    void WithThresholdBonusChargeApplied() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        add5Cars(service, "B1");
        assertEquals(36.0, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
        assertEquals(36.0, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
        assertEquals(36.0, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
        assertEquals(36.0, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
        assertEquals(39.6, service.getBooker().book(service.getBranch("B1"), "CAR", 1, 5));
    }


}