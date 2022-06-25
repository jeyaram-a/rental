package org.example.command;

import org.example.models.Service;


/**
 * Represents an operation on the service.
*/
public interface Command {
    /**
     * @param service Service the command executes upon.
     * @return Result of execution
     */
    Printable execute(Service service);
}
