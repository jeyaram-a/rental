package org.example.command;

import org.example.models.Branch;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddBranchCommandTest {

    Service getService() {
        return new Service("TEST");
    }

    @Test
    void NewBranchReturnsTrue() {
        Service service = getService();
        AddBranchCommand command = new AddBranchCommand("B1", Collections.singletonList("CAR"));
        assertEquals("TRUE", command.execute(service).print());
    }

    @Test
    void BranchExistsReturnFalse() {
        Service service = getService();
        service.addBranch(new Branch("B1", Arrays.asList("CAR")));
        AddBranchCommand command = new AddBranchCommand("B1", Collections.singletonList("CAR"));
        assertEquals("FALSE", command.execute(service).print());
    }

}