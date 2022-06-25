package org.example.command;

import org.example.booker.MinCostDynamicCostBooker;
import org.example.models.Branch;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddVehicleCommandTest {

    Service getService() {
        return new Service("TEST", new MinCostDynamicCostBooker(0.8));
    }

    Branch getBranch() {
        return new Branch("B1", Arrays.asList("CAR", "BIKE"));
    }

    @Test
    void BranchExistsVehicleTypeExistsReturnTrue() {
        Service service = getService();
        service.addBranch(getBranch());
        AddVehicleCommand command = new AddVehicleCommand("B1", "CAR", "V1", 500);
        assertEquals("TRUE", command.execute(service).print());
    }

    @Test
    void BranchNotExistsReturnFalse() {
        Service service = getService();
        service.addBranch(getBranch());
        AddVehicleCommand command = new AddVehicleCommand("B2", "CAR", "V1", 500);
        assertEquals("FALSE", command.execute(service).print());
    }

    @Test
    void VehicleTypeNotExistsReturnFalse() {
        Service service = getService();
        service.addBranch(getBranch());
        AddVehicleCommand command = new AddVehicleCommand("B2", "BUS", "V1", 500);
        assertEquals("FALSE", command.execute(service).print());
    }

}