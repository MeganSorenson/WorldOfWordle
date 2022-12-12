package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Iterator;

// WordleGameManager performs all the major game tasks for World of Wordle
// by creating the appropriate game boards, performing game actions on the boards, and keeping track of game state
public class WordleGameManager implements Writable {
    private String currentGuess = "";
    private int numberGuessesMade = 0;
    private int maxNumberOfGuesses;
    private int score = 0;

    private int difficulty;
    private ArrayList<Board> gameBoards = new ArrayList<>();
    private boolean gameOver = false;

    // EFFECTS: creates a new game manager in easy mode that keeps track of game difficulty,
    //          adds the appropriate amount of boards to the game manager,
    //          game manager remembers the maximum number of guesses based on the difficulty level
    public WordleGameManager() {
        this.difficulty = mapDifficulty("EASY");
        maxNumberOfGuesses = 3 + (2 * this.difficulty);
        createBoards(this.difficulty);

        logEventWithName("Application started.");
    }

    // REQUIRES: difficulty must be one of "EASY", "MEDIUM", "HARD"
    // EFFECTS: creates a new game manager that keeps track of game difficulty,
    //          adds the appropriate amount of boards to the game manager,
    //          game manager remembers the maximum number of guesses based on the given difficulty level
    public WordleGameManager(String difficulty) {
        this.difficulty = mapDifficulty(difficulty);
        maxNumberOfGuesses = 3 + (2 * this.difficulty);
        createBoards(this.difficulty);
    }

    // REQUIRES: called only when application is reopened
    //           and user wants to reload previous game data
    // EFFECTS: game manager remembers the given  the game difficulty, game boards,
    //          number of guesses made, max number of guesses, and score
    public WordleGameManager(int difficulty, ArrayList<Board> gameBoards, int numberGuessesMade,
                             int maxNumberOfGuesses, int score) {
        this.difficulty = difficulty;
        this.gameBoards = gameBoards;
        this.numberGuessesMade = numberGuessesMade;
        this.maxNumberOfGuesses = maxNumberOfGuesses;
        this.score = score;

        logEventWithName("Last game state loaded.");
    }

    // REQUIRES: difficulty must be one of "EASY", "MEDIUM", "HARD"
    // EFFECTS: returns a difficulty level corresponding to the number of boards
    private int mapDifficulty(String difficulty) {
        if (difficulty.equals("HARD")) {
            return 3;
        } else if (difficulty.equals("MEDIUM")) {
            return 2;
        } else {
            return 1;
        }
    }

    // REQUIRES: difficulty must be one of: 1, 2, 3
    // MODIFIES: this
    // EFFECTS: adds the appropriate amount of boards to the game manager based on difficulty of the game
    private void createBoards(int difficulty) {
        for (int i = 0; i < difficulty; i++) {
            Board newBoard = new Board(maxNumberOfGuesses, i + 1);
            gameBoards.add(newBoard);
        }
    }

    // REQUIRES: gameOver must be false
    // MODIFIES: Board, LetterColor
    // EFFECTS: tells each game board what the current word guess is
    //          so that they can update themselves
    //          once done, resets the current guess to get ready for more future guesses
    public void updateBoards() {
        if (!currentGuess.equals("")) {
            for (Board board : gameBoards) {
                board.checkGuess(currentGuess.toUpperCase());
            }
            logEventWithName("User made guess on board(s): " + currentGuess.toUpperCase());
        }

        currentGuess = "";
    }

    // MODIFIES: this
    // EFFECTS: checks the game over state of the game
    //          game over if all boards are guessed or if the maximum number of guesses has been reached
    //          game manager keeps track of score based on game over state
    //          returns true if game over conditions are met and false otherwise
    public boolean isGameOver() {

        boolean allBoardsGuessed = areWordsGuessed();

        if ((numberGuessesMade >= maxNumberOfGuesses) | allBoardsGuessed) {
            gameOver = true;
            logEventWithName("Current game over. Current board(s) cleared.");
        }

        if (gameOver && allBoardsGuessed) {
            score += (difficulty * 1000) - (50 * numberGuessesMade);
            logEventWithName("User guessed the secret word(s).");
        }

        return gameOver;
    }

    //MODIFIES: this
    // EFFECTS: resets the game manager with a new set of boards
    //          and beginning of game attributes
    //          does not affect difficulty or score
    public void resetGame() {
        currentGuess = "";
        numberGuessesMade = 0;
        gameBoards = new ArrayList<>();
        // changed order of next two lines
        maxNumberOfGuesses = 3 + (2 * this.difficulty);
        createBoards(difficulty);
        gameOver = false;
    }

    // REQUIRES: newDifficulty must be one of "EASY", "MEDIUM", "HARD"
    // MODIFIES: this
    // EFFECTS: resets the game manager with a new specified difficulty level
    public void changeDifficulty(String newDifficulty) {
        difficulty = mapDifficulty(newDifficulty);
        resetGame();

        logEventWithName("User has started a new "
                + newDifficulty
                + " game. All previous boards have been cleared.");
    }

    // EFFECTS: returns the board difficulty
    public int getDifficulty() {
        return difficulty;
    }

    // EFFECTS: returns the current guess on the board
    public String getCurrentGuess() {
        return currentGuess;
    }

    // EFFECTS: returns the max number of guesses for the board
    public int getMaxNumberOfGuesses() {
        return maxNumberOfGuesses;
    }

    // EFFECTS: returns the number of guesses made on the board
    public int getNumberOfGuesses() {
        return numberGuessesMade;
    }

    // EFFECTS: returns a list of all the game boards
    public ArrayList<Board> getGameBoards() {
        return gameBoards;
    }

    // REQUIRES: newCurrentGuess must be a valid guess for the game and gameOver must be false
    // MODIFIES: this
    // EFFECTS: this remembers current guess and increments the number of guesses made on the board
    public void setCurrentGuess(String newCurrentGuess) {
        currentGuess = newCurrentGuess;
        numberGuessesMade++;

    }

    // EFFECTS: returns the game's score
    public int getScore() {
        return score;
    }

    // EFFECTS: returns whether the game is over or not
    public boolean getGameOver() {
        return gameOver;
    }

    // MODIFIES: this
    // EFFECTS: sets game over to specified value
    public void setGameOver(boolean gameOverVal) {
        this.gameOver = gameOverVal;
    }

    // REQUIRES: game is not over
    // EFFECTS: returns a JSON representation of the game manager
    //          resets game if game is over before converting to JSON
    public JSONObject toJson() {
        if (isGameOver()) {
            resetGame();
        }
        JSONObject jsonGameManager = new JSONObject();
        jsonGameManager.put("difficulty", difficulty);
        jsonGameManager.put("numberGuessesMade", numberGuessesMade);
        jsonGameManager.put("maxNumberOfGuesses", maxNumberOfGuesses);
        jsonGameManager.put("gameScore", score);
        addBoardsToJson(jsonGameManager);

        logEventWithName("Current game state saved.");

        return jsonGameManager;
    }

    // MODIFIES: JSONObject
    // EFFECTS: adds json array of each board to json object
    private void addBoardsToJson(JSONObject jsonGameManager) {
        for (Board board : gameBoards) {
            JSONArray jsonBoard = new JSONArray();
            jsonBoard.put(board.getAllRowsAsString());
            jsonBoard.put(board.getWordToGuess());
            jsonBoard.put(board.getNumberOfGuessesMade());
            jsonBoard.put(board.getMaxNumberOfGuesses());
            jsonBoard.put(board.getBoardID());
            jsonBoard.put(board.isGuessed());

            String boardKey = "board" + board.getBoardID();
            jsonGameManager.put(boardKey, jsonBoard);
        }
    }

    // EFFECTS: returns true if all words are guessed
    public boolean areWordsGuessed() {
        boolean allBoardsGuessed = true;
        for (Board board : gameBoards) {
            if (!board.isGuessed()) {
                allBoardsGuessed = false;
            }
        }
        return allBoardsGuessed;
    }

    // MODIFIES: EventLog
    // EFFECTS: logs an event in the event log with the given description
    public void logEventWithName(String description) {
        EventLog.getInstance().logEvent(new Event(description));
    }

    // EFFECTS: prints the event log for a single continuous game session on this
    public void printLog() {
        logEventWithName("Application closing.");

        for (Iterator<Event> it = EventLog.getInstance().iterator(); it.hasNext(); ) {
            Event event = it.next();
            System.out.println(event.getDescription());
        }
    }

}
