package org.example.command;

import org.example.models.Service;

public interface Command {
    Printable execute(Service service);
}
