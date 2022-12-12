package ui;

//import model.Board;
import model.Board;
import model.WordleGameManager;
import persistence.GameManagerJsonReader;
import persistence.JsonWriter;
import ui.windows.AllGameWindowComponents;
import ui.popups.LoserPopUp;
import ui.popups.WinnerPopUp;

import java.io.IOException;

// WordleGameGUI is the middle man that runs a World of Wordle Game by interacting with
// a WordleGameManager and a AllGameWindowComponents
// runs a game turn, produces visual output, updates game boards, creates pop ups in game over situations
public class WordleGameGUI {
    private WordleGameManager gameManager;
    private AllGameWindowComponents gameWindow;

    private static final String PERSISTENCE_FILE = "data/savedWordleGame.json";
    private final JsonWriter gameWriter = new JsonWriter(PERSISTENCE_FILE);
    private final GameManagerJsonReader gameReader = new GameManagerJsonReader(PERSISTENCE_FILE);


    // EFFECTS: this remembers the game manager and game window for the game
    public WordleGameGUI() {
        this.gameManager = new WordleGameManager();
        this.gameWindow = new AllGameWindowComponents(this);

    }

    // MODIFIES: WordleGameManager, Board, LetterColor, AllGameWindowComponents, JFrame, BoardUI
    // EFFECTS: plays one turn of the game if the user's guess is valid, otherwise does not
    //          performs game over actions if game over, otherwise does not
    public void playOneTurn(String userGuess) {
        if (isGuessValid(userGuess)) {
            gameManager.setCurrentGuess(userGuess);
            gameManager.updateBoards();
            gameManager.isGameOver();
            gameWindow.updateBoards(gameManager.getGameBoards());
            gameWindow.updateScore(gameManager.getScore());
        }

        if (gameManager.getGameOver()) {
            gameOverActions();
        }

    }

    // EFFECTS: returns true if the user guess is a valid guess
    //          otherwise false
    private boolean isGuessValid(String userGuess) {
        return userGuess.length() == 5;
    }

    // EFFECTS: returns the game manager associated with the wordle game
    public WordleGameManager getGameManager() {
        return gameManager;
    }

    // REQUIRES: called only if the game is over
    // MODIFIES: WordleGameManager, Board, LetterColor, AllGameWindowComponents, JFrame, BoardUI
    // EFFECTS: produces a winner or loser pop up depending on if the user guessed the words or not
    //          and resets the game board to a new game of the same difficulty
    //          if loser pop up created, send the words that were wrongly guessed to the pop ups
    public void gameOverActions() {
        if (gameManager.areWordsGuessed()) {

            new WinnerPopUp(gameWindow.getGameWindow(), "Winner!", true);
        } else {
            new LoserPopUp(gameWindow.getGameWindow(), "Sorry!", true, getWords());
        }
        resetGame();
    }

    // EFFECTS: returns all words from the current game board(s)
    private String getWords() {
        String words = "";
        for (Board board : gameManager.getGameBoards()) {
            words += "Board " + board.getBoardID() + " word: " + board.getWordToGuess() + "    ";
        }

        return words;
    }

    // REQUIRES: called after a game is over
    // MODIFIES: this, WordleGameManager, Board, LetterColor,
    //           AllGameWindowComponents, BoardUI
    // EFFECTS: resets the wordle game including the game visuals
    private void resetGame() {
        gameManager.resetGame();
        gameWindow.updateBoards(gameManager.getGameBoards());
        gameWindow.updateScore(gameManager.getScore());
    }

    // REQUIRES: called only at the beginning of the game when the user hits the load game
    //           button in the start pop up window
    // MODIFIES: this, WordleGameManager, Board, LetterColor,
    //           AllGameWindowComponents, BoardUI
    // EFFECTS: loads the last saved game and updates the boards and their visuals
    //          if file path for loading does not exist, does not load
    public void loadGame() {
        try {
            gameManager = gameReader.read();
            gameWindow.updateBoards(gameManager.getGameBoards());
            gameWindow.updateScore(gameManager.getScore());
        } catch (IOException e) {
            // do nothing
        }
    }

    // REQUIRES: called only at the end of the game when the user hits the save game
    //           button in the closing pop up window
    // MODIFIES: this, WordleGameManager, Board, LetterColor,
    //           AllGameWindowComponents, BoardUI
    // EFFECTS: saves the last game to a file for next time
    //          if file path for saving does not exist, does not save
    public void saveGame() {
        try {
            gameWriter.openWriter();
            gameWriter.writeGameManager(gameManager);
            gameWriter.closeWriter();

        } catch (IOException e) {
            // do nothing
        }
    }
}
