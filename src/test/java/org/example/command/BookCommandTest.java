package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BookCommandTest {

    Service getService() {
        return new Service("TEST");
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
    void BranchNotExistsReturnFalse() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        BookCommand command = new BookCommand("B2", "CAR", 1, 2);
        assertEquals("-1", command.execute(service).print());
    }

    @Test
    void VehicleTypeNotExistsReturnFalse() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        BookCommand command = new BookCommand("B1", "BUS", 1, 2);
        assertEquals("-1", command.execute(service).print());
    }

    @Test
    void BookingTimesOverlappingDisjoint() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        addVehicles(service, "B1");
        // car C1 100*4
        assertEquals("400", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        // car C2 200*4
        assertEquals("800", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        // none are available
        assertEquals("-1", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        // car C1
        assertEquals("100", new BookCommand("B1", "CAR", 5, 6).execute(service).print());
        // car C1
        assertEquals("100", new BookCommand("B1", "CAR", 6, 7).execute(service).print());
        // car C2
        assertEquals("400", new BookCommand("B1", "CAR", 5, 7).execute(service).print());
    }

    @Test
    void BookingOverThresholdIncreasesPriceBy10Percent() {
        Service service = getService();
        service.addBranch(getBranch("B1"));
        add5Cars(service, "B1");
        assertEquals("36", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        assertEquals("36", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        assertEquals("36", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        assertEquals("36", new BookCommand("B1", "CAR", 1, 5).execute(service).print());
        assertEquals("39.6", new BookCommand("B1", "CAR", 1, 5).execute(service).print());



    }


}