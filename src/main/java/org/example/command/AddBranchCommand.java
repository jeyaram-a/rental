package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

import java.util.List;
import java.util.Objects;

public class AddBranchCommand implements Command {
    String branchName;
    List<String> vehicleTypes;

    public AddBranchCommand(String branchName, List<String> vehicleTypes) {
        this.branchName = branchName;
        this.vehicleTypes = vehicleTypes;
    }

    @Override
    public Printable execute(Service service) {

        if (service.hasBranch(this.branchName)) {
            return () -> "FALSE";
        }
        Branch newBranch = new Branch(this.branchName, this.vehicleTypes);
        service.addBranch(newBranch);

        return () -> "TRUE";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddBranchCommand that = (AddBranchCommand) o;
        return Objects.equals(branchName, that.branchName) && Objects.equals(vehicleTypes, that.vehicleTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchName, vehicleTypes);
    }
}
