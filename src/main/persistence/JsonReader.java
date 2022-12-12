package persistence;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// JSonReader is a reader that reads JSON data stored in a file
// class structure and some implementation (also for subclasses) inspired by Paul Carter's JsonReader sourced from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java
public abstract class JsonReader {
    protected String sourceFile;

    // EFFECTS: sets the source file directories for the JSON reader
    public JsonReader(String sourceFile) {
        this.sourceFile = sourceFile;

    }

    // EFFECTS: reads info from a file and returns the info as a string
    //          throws exception if sourceFile does not exist
    // method body heavily inspired by Paul Carter's readFile() from the above sourced JsonReader class
    public String readFile(String sourceFile) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(sourceFile), StandardCharsets.UTF_8)) {
            stream.forEach(stringBuilder::append);
        }
        return stringBuilder.toString();
    }

}
