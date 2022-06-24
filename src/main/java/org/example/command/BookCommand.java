package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

import java.util.Objects;

public class BookCommand implements Command {

    String branchId, vehicleType;
    int start, end;

    public BookCommand(String branchId, String vehicleType, int start, int end) {
        if (start >= end) {
            throw new RuntimeException("Start has to be < end");
        }
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

        double result = branch.book(this.vehicleType, this.start, this.end);


        return () -> {
            if(result % 1 == 0) {
                return Integer.toString((int)result);
            } else
                return Double.toString(result);
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookCommand that = (BookCommand) o;
        return start == that.start && end == that.end && Objects.equals(branchId, that.branchId) && Objects.equals(vehicleType, that.vehicleType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchId, vehicleType, start, end);
    }
}
