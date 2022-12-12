package persistence;

import model.Board;
import model.FeedbackCharacter;
import model.WordleGameManager;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

// GameManagerJsonReader is a JSON file reader that de-serializes the game state stored in the file and creates
// a new WordleGameManager that reflects the serialized data
public class GameManagerJsonReader extends JsonReader {

    public GameManagerJsonReader(String sourceFile) {
        super(sourceFile);
    }

    // EFFECTS: reads all info from source file
    //          returns a game manager that incorporates the file's game state data
    //          throws an exception if file does not exist
    public WordleGameManager read() throws IOException {
        String jsonData = readFile(sourceFile);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGameManagerData(jsonObject);
    }

    // EFFECTS: parses a JSON object and creates a new game manager that reflects the read file's data
    //          returns the new game manager
    private WordleGameManager parseGameManagerData(JSONObject jsonObject) {
        int difficulty = jsonObject.getInt("difficulty");
        ArrayList<Board> gameBoards = parseAllBoardData(jsonObject);
        int numberGuessesMade = jsonObject.getInt("numberGuessesMade");
        int maxNumberOfGuesses = jsonObject.getInt("maxNumberOfGuesses");
        int gameScore = jsonObject.getInt("gameScore");

        WordleGameManager gameManager = new WordleGameManager(difficulty, gameBoards, numberGuessesMade,
                maxNumberOfGuesses, gameScore);
        return gameManager;
    }

    // EFFECTS: parses a JSON object and creates a list of game boards based on the read file's data
    //          returns a list of all boards saved in the file
    private ArrayList<Board> parseAllBoardData(JSONObject jsonObject) {
        ArrayList<Board> gameBoards = new ArrayList<>();
        try {
            JSONArray boardOneData = jsonObject.getJSONArray("board1");
            gameBoards.add(parseBoardData(boardOneData));
            JSONArray boardTwoData = jsonObject.getJSONArray("board2");
            gameBoards.add(parseBoardData(boardTwoData));
            JSONArray boardThreeData = jsonObject.getJSONArray("board3");
            gameBoards.add(parseBoardData(boardThreeData));
        } catch (Exception e) {
            // do nothing once one of the boards isn't found
        }

        return gameBoards;
    }

    // EFFECTS: parses a list of boards and creates new boards based on the read file's data
    //          returns a board that incorporates all saved data
    private Board parseBoardData(JSONArray jsonArray) {
        ArrayList<ArrayList<FeedbackCharacter>> rows = createBoardRows(jsonArray.getString(0));
        String wordToGuess = jsonArray.getString(1);
        int numberGuessesMade = jsonArray.getInt(2);
        int maxNumberOfGuesses = jsonArray.getInt(3);
        int boardID = jsonArray.getInt(4);
        boolean guessed = jsonArray.getBoolean(5);

        Board board = new Board(rows, wordToGuess, numberGuessesMade, guessed, maxNumberOfGuesses, boardID);
        return board;
    }

    // EFFECTS: creates a list of board rows based on the saved row data
    //          returns a list of a single board's rows
    private ArrayList<ArrayList<FeedbackCharacter>> createBoardRows(String allRowsAsString) {
        allRowsAsString = allRowsAsString.replace("|", ",");
        ArrayList<ArrayList<FeedbackCharacter>> boardRows = new ArrayList<>();
        String[] rowStrings = allRowsAsString.substring(1).split(",");
        for (String row : rowStrings) {
            ArrayList<FeedbackCharacter> boardRow = new ArrayList<>();
            String[] rowString = row.substring(1).split("/");
            createRow(rowString, boardRow);
            boardRows.add(boardRow);
        }

        return boardRows;
    }

    // REQUIRES: boardRow is empty
    // MODIFIES: boardRow
    // EFFECTS: creates the letter feedback of a row based on the saved row data
    //          returns a list letter feedback representing a single board row
    //          boardRow will be of length 5 when returned
    private void createRow(String[] rowString, ArrayList<FeedbackCharacter> boardRow) {
        if (rowString.length == 0) {
            for (int i = 0; i < 5; i++) {
                FeedbackCharacter letterColor = new FeedbackCharacter();
                boardRow.add(letterColor);
            }
        } else {
            for (String letterFeedbackString : rowString) {
                FeedbackCharacter letterColor = new FeedbackCharacter();
                letterColor.setLetter(letterFeedbackString.substring(0, 1));
                letterColor.setColor(letterFeedbackString.substring(1));
                boardRow.add(letterColor);
            }
        }
    }

}
