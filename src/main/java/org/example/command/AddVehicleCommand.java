package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

public class AddVehicleCommand implements Command {

    private final String branchName;
    private final String vehicleType;
    private final String vehicleId;
    private final int price;

    public AddVehicleCommand(String branchName, String vehicleType, String vehicleId, int price) {
        this.branchName = branchName;
        this.vehicleType = vehicleType;
        this.vehicleId = vehicleId;
        this.price = price;
    }

    @Override
    public Printable execute(Service service) {
        if (!service.hasBranch(this.branchName)) {
            return () -> Boolean.toString(false);
        }
        Branch branch = service.getBranch(this.branchName);
        if (!branch.hasVehicleType(this.vehicleType)) {
            return () -> Boolean.toString(false);
        }
        branch.addVehicles(this.vehicleType, this.vehicleId, this.price);
        return () -> Boolean.toString(true);
    }

}
