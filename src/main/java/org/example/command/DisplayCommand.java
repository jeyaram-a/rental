package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

import java.util.Objects;

public class DisplayCommand implements Command {
    String branchName;
    int start, end;

    public DisplayCommand(String branch, int start, int end) {
        if (start >= end) {
            throw new RuntimeException("Start has to be < end");
        }
        this.branchName = branch;
        this.start = start;
        this.end = end;

    }

    @Override
    public Printable execute(Service service) {
        if (!service.hasBranch(this.branchName)) {
            return () -> "";
        }

        Branch branch = service.getBranch(branchName);
        return () -> String.join(",", branch.getAvailableVehicles(start, end));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DisplayCommand that = (DisplayCommand) o;
        return start == that.start && end == that.end && Objects.equals(branchName, that.branchName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(branchName, start, end);
    }
}
