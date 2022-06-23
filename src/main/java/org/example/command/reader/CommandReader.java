package org.example.command.reader;

import org.example.command.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CommandReader {

    static Command read(String s) {
        String[] parts = s.trim().split(" ");
        String commandName = parts[0];
        if ("ADD_BRANCH".equals(commandName)) {
            if (parts.length != 3)
                throw new RuntimeException("Invalid command format ADD_BRANCH. Requires 3 params");
            String branchName = parts[1];
            List<String> vehicleTypes = Arrays.stream(parts[2].split(",")).collect(Collectors.toList());
            return new AddBranchCommand(branchName, vehicleTypes);
        } else if ("ADD_VEHICLE".equals(commandName)) {
            if (parts.length != 5)
                throw new RuntimeException("Invalid command format ADD_VEHICLE. Requires 5 params");
            String branchName = parts[1];
            String vehicleType = parts[2];
            String vehicleId = parts[3];
            int price = Integer.parseInt(parts[4]);
            return new AddVehicleCommand(branchName, vehicleType, vehicleId, price);
        } else if ("BOOK".equals(commandName)) {
            if (parts.length != 5)
                throw new RuntimeException("Invalid command format BOOK. Requires 5 params");
            String branchName = parts[1];
            String vehicleType = parts[2];
            int start = Integer.parseInt(parts[3]);
            int end = Integer.parseInt(parts[4]);
            return new BookCommand(branchName, vehicleType, start, end);
        } else {
            // Display
            if (parts.length != 4)
                throw new RuntimeException("Invalid command format DISPLAY_VEHICLES. Requires 4 params");
            String branchName = parts[1];
            int start = Integer.parseInt(parts[2]);
            int end = Integer.parseInt(parts[3]);
            return new DisplayCommand(branchName, start, end);
        }
    }

    public static FileCommandIterator readFromFile(String filePath) {
        FileReader fr;
        List<String> lines;
        try {
            fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            lines = br.lines().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            return new FileCommandIterator(lines);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error in reading input file");
        }
    }

    static Iterator<Command> getIterator(List<String> lines) {

        Iterator<String> iterator = lines.iterator();

        return new Iterator<Command>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public Command next() {
                String first = iterator.next();
                return read(first);
            }
        };
    }

    static public class FileCommandIterator implements Iterable<Command> {

        List<String> lines;

        FileCommandIterator(List<String> lines) {
            this.lines = lines;
        }

        @Override
        public Iterator<Command> iterator() {
            return getIterator(this.lines);
        }
    }
}
