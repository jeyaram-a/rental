package org.example.command;

/**
 * Represents some data that can be printed.
 * Since not all classes define <code>toString</code>, this ensures the
 * data can be printed in some valid form to console (anywhere)/
 */
public interface Printable {
    String print();
}
