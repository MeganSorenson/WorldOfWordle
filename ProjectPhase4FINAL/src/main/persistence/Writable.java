package persistence;

import org.json.JSONObject;

public interface Writable {

    // REQUIRES: associated World of Wordle game is not over
    // EFFECTS: returns a JSON representation of the caller
    JSONObject toJson();
}
