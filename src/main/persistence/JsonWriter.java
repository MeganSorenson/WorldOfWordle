package persistence;

import model.WordleGameManager;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// JSonWriter is a writer that writes JSON data to a file
// class structure and some implementation (also for subclasses) inspired by Paul Carter's JsonWriter sourced from:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonWriter.java
public class JsonWriter {
    protected static final int TAB = 4;
    protected PrintWriter writer;
    protected String destinationFile;

    // REQUIRES: destination to be the directory for a json file
    // EFFECTS: writer remembers the given destination file
    public JsonWriter(String destinationFile) {
        this.destinationFile = destinationFile;
    }

    // MODIFIES: this
    // EFFECTS: opens a new writer for the destination file
    //          throws an exception if the destination file is not found
    public void openWriter() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationFile));
    }

    // MODIFIES: this
    // EFFECTS: closes the writer
    public void closeWriter() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes the info from a game manager to a json object
    public void writeGameManager(WordleGameManager gameManager) {
        JSONObject jsonGameManager = gameManager.toJson();
        writeToFile(jsonGameManager.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: writes a json representation of a game manager to a file
    public void writeToFile(String jsonGameManager) {
        writer.print(jsonGameManager);
    }

}
