package org.example.command.reader;

class CommandReaderTest {

//    File getFile(String path) {
//        ClassLoader classLoader = getClass().getClassLoader();
//        return new File(Objects.requireNonNull(classLoader.getResource(path)).getFile());
//    }
//    @Test
//    void CommandCountShouldMatch() {
//        CommandReader reader = new CommandReader(getFile("test_input1.txt"));
//        List<Command> read = new LinkedList<>();
//        for(Command command: reader) {
//            read.add(command);
//        }
//        assertEquals(4, read.size());
//    }
//
//    @Test
//    void ValidAddBranchCommandReadNoError() {
//        CommandReader reader = new CommandReader(getFile("add_branch/test_add_branch.txt"));
//        List<Command> read = new LinkedList<>();
//        for(Command command: reader) {
//            read.add(command);
//        }
//        assertEquals(1, read.size());
//        List<String> vehicleTyps = Arrays.asList("CAR","BIKE","VAN");
//        AddBranchCommand addBranchCommand = new AddBranchCommand("B1", vehicleTyps);
//        assertEquals(read.get(0), addBranchCommand);
//    }
//
//    @Test
//    void InvalidAddBranchCommandReadThrowsError() {
//        CommandReader reader = new CommandReader(getFile("add_branch/test_add_branch_error.txt"));
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            for(Command command: reader) {
//
//            }
//        });
//        assertEquals(exception.getMessage(), "Invalid command format ADD_BRANCH. Requires 3 params");
//    }
//
//    @Test
//    void ValidAddVehicleCommandReadNoError() {
//        CommandReader reader = new CommandReader(getFile("add_branch/test_add_branch.txt"));
//        List<Command> read = new LinkedList<>();
//        for(Command command: reader) {
//            read.add(command);
//        }
//        assertEquals(1, read.size());
//        List<String> vehicleTyps = Arrays.asList("CAR","BIKE","VAN");
//        AddBranchCommand addBranchCommand = new AddBranchCommand("B1", vehicleTyps);
//        assertEquals(read.get(0), addBranchCommand);
//    }
//
//    @Test
//    void InvalidAddVehicleCommandReadThrowsError() {
//        CommandReader reader = new CommandReader(getFile("add_branch/test_add_branch_error.txt"));
//        Exception exception = assertThrows(RuntimeException.class, () -> {
//            for(Command command: reader) {
//
//            }
//        });
//        assertEquals(exception.getMessage(), "Invalid command format ADD_BRANCH. Requires 3 params");
//    }
}