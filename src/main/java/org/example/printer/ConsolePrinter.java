package org.example.printer;

import org.example.command.Printable;

public class ConsolePrinter implements Printer {
    @Override
    public void print(Printable printable) {
        System.out.println(printable.print());
    }
}
