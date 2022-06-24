package org.example.command.reader;

import org.example.command.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommandReaderTest {

    File getFile(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
    }

    @Test
    void CommandCountShouldMatch() {
        List<Command> read = new LinkedList<>();
        for (Command command : CommandReader.readFromFile(getFile("test_input1.txt"))) {
            read.add(command);
        }
        assertEquals(11, read.size());
    }

    @Test
    void ValidAddBranchCommandReadNoError() {
        Command command = CommandReader.read("ADD_BRANCH B1 CAR,BIKE,VAN");
        List<String> vehicleTypes = Arrays.asList("CAR", "BIKE", "VAN");
        AddBranchCommand addBranchCommand = new AddBranchCommand("B1", vehicleTypes);
        assertEquals(command, addBranchCommand);
    }

    @Test
    void InvalidAddBranchCommandReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("ADD_BRANCH CAR,BIKE,VAN");
        });
        assertEquals(exception.getMessage(), "Invalid command format ADD_BRANCH. Requires 3 params");
    }

    @Test
    void ValidAddVehicleCommandReadNoError() {
        Command command = CommandReader.read("ADD_VEHICLE B1 CAR V1 500");

        AddVehicleCommand addVehicleCommand = new AddVehicleCommand("B1", "CAR", "V1", 500);
        assertEquals(command, addVehicleCommand);
    }

    @Test
    void InvalidAddVehicleCommandReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("ADD_VEHICLE B1 CAR V1");
        });
        assertEquals(exception.getMessage(), "Invalid command format ADD_VEHICLE. Requires 5 params");
    }

    @Test
    void ValidBookCommandReadNoError() {
        Command command = CommandReader.read("BOOK B1 CAR 1 3");

        BookCommand bookCommand = new BookCommand("B1", "CAR", 1, 3);
        assertEquals(command, bookCommand);
    }

    @Test
    void InvalidBookCommandFormatReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("BOOK B1 CAR 1");
        });
        assertEquals(exception.getMessage(), "Invalid command format BOOK. Requires 5 params");
    }

    @Test
    void InvalidBookCommandTimeReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("BOOK B1 CAR 2 1");
        });
        assertEquals(exception.getMessage(), "Start has to be < end");
    }

    @Test
    void ValidDisplayCommandReadNoError() {
        Command command = CommandReader.read("DISPLAY_VEHICLES B1 1 5");

        DisplayCommand displayCommand = new DisplayCommand("B1", 1, 5);
        assertEquals(command, displayCommand);
    }

    @Test
    void InvalidDisplayCommandFormatReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("DISPLAY_VEHICLES B1 1");
        });
        assertEquals(exception.getMessage(), "Invalid command format DISPLAY_VEHICLES. Requires 4 params");
    }

    @Test
    void InvalidDisplayCommandTimeReadThrowsError() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CommandReader.read("DISPLAY_VEHICLES B1 5 1");
        });
        assertEquals(exception.getMessage(), "Start has to be < end");
    }


}