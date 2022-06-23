package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

public class BookCommand implements Command {

    String branchId, vehicleType;
    int start, end;

    public BookCommand(String branchId, String vehicleType, int start, int end) {
        this.branchId = branchId;
        this.vehicleType = vehicleType;
        this.start = start;
        this.end = end;
    }

    @Override
    public Printable execute(Service service) {
        if (!service.hasBranch(this.branchId)) {
            return () -> Integer.toString(-1);
        }

        Branch branch = service.getBranch(this.branchId);

        if (!branch.hasVehicleType(this.vehicleType)) {
            return () -> Integer.toString(-1);
        }

        int result = branch.book(this.vehicleType, this.start, this.end);


        return () -> Integer.toString(result);
    }

}
