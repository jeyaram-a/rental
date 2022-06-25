package org.example;

import org.example.command.Command;
import org.example.command.reader.CommandReader;
import org.example.models.Service;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainTest {

    File getFile(String path) {
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
    }
    @Test
    void TestProvidedTestCase() {
        ListAppender printer = new ListAppender();
        Service service = new Service("Test");
        for(Command command: CommandReader.readFromFile(getFile("test_input1.txt"))) {
            printer.print(command.execute(service));
        }
        List<String> expected = Arrays.asList("TRUE", "TRUE", "TRUE", "TRUE", "TRUE", "FALSE", "-1", "1000", "250", "900", "V2");
        List<String> actual = printer.getOutput();

        assertEquals(actual, expected);
    }

}