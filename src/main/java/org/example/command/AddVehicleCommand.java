package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

import java.util.Objects;

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
            return () -> "FALSE";
        }
        Branch branch = service.getBranch(this.branchName);
        if (!branch.hasVehicleType(this.vehicleType)) {
            return () -> "FALSE";
        }
        branch.addVehicle(this.vehicleType, this.vehicleId, this.price);
        return () -> "TRUE";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddVehicleCommand that = (AddVehicleCommand) o;
        return price == that.price && Objects.equals(branchName, that.branchName) && Objects.equals(vehicleType, that.vehicleType) && Objects.equals(vehicleId, that.vehicleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchName, vehicleType, vehicleId, price);
    }
}
