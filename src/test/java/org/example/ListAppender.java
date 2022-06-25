package org.example;

import org.example.command.Printable;
import org.example.printer.Printer;

import java.util.LinkedList;
import java.util.List;

public class ListAppender implements Printer {
    private final List<String> output = new LinkedList<>();

    @Override
    public void print(Printable printable) {
        output.add(printable.print());
    }

    public List<String> getOutput() {
        return output;
    }
}
