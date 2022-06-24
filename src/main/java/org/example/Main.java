package org.example;

import org.example.command.Command;
import org.example.command.reader.CommandReader;
import org.example.models.Service;
import org.example.printer.ConsolePrinter;
import org.example.printer.Printer;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Service service = new Service("Test");
        if (args.length < 1) {
            throw new RuntimeException("No file path provided for commands");
        }
        String filePath = args[0];
        Printer printer = new ConsolePrinter();
        for (Command x : CommandReader.readFromFile(new File(filePath))) {
            printer.print(x.execute(service));
        }

    }
}