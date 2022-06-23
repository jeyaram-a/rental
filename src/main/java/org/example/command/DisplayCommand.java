package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;

public class DisplayCommand implements Command {
    String branchName;
    int start, end;

    public DisplayCommand(String branch, int start, int end) {
        this.branchName = branch;
        this.start = start;
        this.end = end;

    }

    @Override
    public Printable execute(Service service) {
        if (!service.hasBranch(this.branchName)) {
            return () -> "";
        }
        if (this.start >= this.end) {
            throw new RuntimeException("For Display command start has to be < end");
        }
        Branch branch = service.getBranch(branchName);
        return () -> String.join(",", branch.getAvailableVehicles(start, end));
    }

}
