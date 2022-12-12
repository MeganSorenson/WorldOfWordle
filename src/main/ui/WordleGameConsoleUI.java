package ui;

import model.Board;
import model.FeedbackCharacter;
import model.WordleGameManager;
import persistence.GameManagerJsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


// WordleGameConsoleUI is the user interface side of a World of Wordle game in the terminal
// plays the main game loop, asks for user input when required, and prints the game objects onto the console
public class WordleGameConsoleUI {
    private WordleGameManager gameManager;
    private final Scanner userInputScanner = new Scanner(System.in);
    private boolean userWantsToQuit = false;

    private static final String PERSISTENCE_FILE = "data/savedWordleGame.json";
    private final JsonWriter gameWriter = new JsonWriter(PERSISTENCE_FILE);
    private final GameManagerJsonReader gameReader = new GameManagerJsonReader(PERSISTENCE_FILE);

    // EFFECTS: WordleGameConsoleUI gets assigned a game manager
    public WordleGameConsoleUI(WordleGameManager gameManager) {
        this.gameManager = gameManager;
    }

    // MODIFIES: WordleGameManager, Board, LetterColor
    // EFFECTS: plays a game loop of World of Wordle by;
    //          - getting user guess/quit sequence
    //          if user does not want to quit the game, continues;
    //          - updating the game boards
    //          - checking if the game is over
    //          - if the game is over, asks user if they want to play again
    //            and asks which difficulty to play, then resets game at that difficulty
    //          otherwise ends game
    public void playGameLoop() {
        printInstructions();
        askToLoadPreviousGame();
        printGameBoards();
        System.out.println("Score: " + gameManager.getScore());
        while (!gameManager.getGameOver()) {
            getUserGuess();
            if (!userWantsToQuit) {
                gameManager.updateBoards();
                gameManager.isGameOver();
                printGameBoards();
                System.out.println("Score: " + gameManager.getScore());
                gameOverOptions();
            } else {
                gameManager.setGameOver(true);
            }
        }
    }

    // EFFECTS: prints game instructions for World of Wordle
    private void printInstructions() {
        System.out.println("WORLD OF WORDLE: like Wordle but on steroids!");
        System.out.println("Each Wordle Board has a secret 5-letter word that you are trying to guess");
        System.out.println("Each turn, you will guess a 5-letter word, and the Wordle Board(s) "
                + "will show you feedback on your guess:");
        System.out.println("       - GREEN letters mean that they are in the correct position for the secret word");
        System.out.println("       - YELLOW letters mean that they are in the secret word, "
                + "but not in the right position");
        System.out.println("       - RED letters mean that they are not in the secret word");
        System.out.println();
        System.out.println();
        System.out.println("There are three game difficulties to play:");
        System.out.println("       - EASY games have you trying to guess 1 secret word in 5 guesses");
        System.out.println("       - MEDIUM games have you trying to simultaneously guess 2 secret words in 7 guesses");
        System.out.println("       - HARD games have you trying to simultaneously guess 3 secret word in 9 guesses");
        System.out.println();
        System.out.println();
        System.out.println("Maximize your score by guessing the secret word(s) in as little guesses as possible!");
        System.out.println("Good Luck!");
        System.out.println();
        System.out.println();
    }

    // REQUIRES: called only before starting any games for the current game manager
    // MODIFIES: this
    // EFFECTS: asks user if they want to load their game from when they last closed the application
    //          if yes, this remembers the de-serialized game manager
    //          otherwise, asks user for game difficulty and resets game manager for given difficulty
    private void askToLoadPreviousGame() {
        System.out.println("Would you like to load your last saved World of Wordle Game? (Y/n) >     ");
        String userAnswer = userInputScanner.next();

        if (userAnswer.equalsIgnoreCase("Y")) {
            try {
                gameManager = gameReader.read();
                System.out.println("Game loaded! Keep at it!");
            } catch (IOException e) {
                System.out.println("Sorry, last game not found but ou can still play a new game!");
            }
        } else {
            gameManager.changeDifficulty(askDifficulty());
            gameManager.resetGame();
        }
    }

    // MODIFIES: WordleGameManager
    // EFFECTS: asks the user in the console for a word guess or if they want to quit
    //          if user wants to quit, asks to save and responds accordingly
    //          otherwise, checks if input is valid and if it is, send the guess to the game manager
    //          and if not valid prints an error message for the user
    public void getUserGuess() {
        int numberGuessesLeft = gameManager.getMaxNumberOfGuesses() - gameManager.getNumberOfGuesses();
        System.out.println(numberGuessesLeft + " Word Guesses left.");
        System.out.println("Make a 5-letter Word Guess or enter Q to quit >     ");
        String userGuess = userInputScanner.next().toUpperCase();

        if (userGuess.equalsIgnoreCase("Q")) {
            userWantsToQuit = true;
            askToSave();
        } else if (isGuessValid(userGuess) && !gameManager.isGameOver()) { // HERE IS THE PROBLEM
            gameManager.setCurrentGuess(userGuess);
        } else {
            System.out.print("INVALID GUESS: must be 5-letter word");
        }
    }

    // EFFECTS: returns true if the guess is a five-letter word, otherwise false
    private boolean isGuessValid(String guess) {
        return guess.length() == 5;
    }

    // REQUIRES: gameOver must be true
    // EFFECTS: asks the user in the console if they want to play another game after the current game is over
    //          returns true if they want to play again, otherwise false
    private boolean askPlayAgain() {
        System.out.println("Would you like to play another World of Wordle game? (Y/n) >     ");
        String userAnswer = userInputScanner.next();

        return userAnswer.equalsIgnoreCase("Y");
    }

    // REQUIRES: gameOver is true and askPlayAgain is true
    // EFFECTS: asks the user in the console what difficulty game they want to play
    //          returns "EASY" "MEDIUM" or "HARD" depending on user input
    //          if user input is not "E/e". "M/m", OR "H/h", returns the current
    //          difficulty of the game
    public String askDifficulty() {
        System.out.println("Select game difficulty (E/M/H) >     ");
        String userAnswer = userInputScanner.next();

        int currentGameDifficulty = gameManager.getDifficulty();

        if (userAnswer.equalsIgnoreCase("E")) {
            return "EASY";
        } else if (userAnswer.equalsIgnoreCase("M")) {
            return "MEDIUM";
        } else if (userAnswer.equalsIgnoreCase("H")) {
            return "HARD";
        } else if (currentGameDifficulty == 1) {
            return "EASY";
        } else if (currentGameDifficulty == 2) {
            return "MEDIUM";
        } else {
            return "HARD";
        }
    }

    // EFFECTS: prints all game boards in the console
    public void printGameBoards() {
        for (Board board : gameManager.getGameBoards()) {
            printBoard(board);
        }
    }

    // EFFECTS: prints a single board onto the console
    private void printBoard(Board board) {
        System.out.println("\nWordle Board " + board.getBoardID() + ":");
        System.out.println("-----------");
        for (ArrayList<FeedbackCharacter> row : board.getRows()) {
            for (FeedbackCharacter feedbackCharacter : row) {
                if (feedbackCharacter.getLetter().equals("")) {
                    System.out.print("| ");
                } else {
                    System.out.print("|" + getColorCode(feedbackCharacter.getColor())
                            + feedbackCharacter.getLetter() + "\u001B[0m");
                }
            }
            System.out.print("|\n");
        }
        System.out.println("-----------");
    }

    // EFFECTS: gets the color code for the console output of letter feedback
    private String getColorCode(String color) {
        if (color.equals("G")) {
            return "\u001B[32m";
        } else if (color.equals("Y")) {
            return "\u001B[33m";
        } else if (color.equals("R")) {
            return "\u001B[31m";
        } else {
            return "\u001B[30m";
        }
    }

    // EFFECTS: if game is over, asks user if they want to play again
    //          and asks which difficulty to play, then resets game at that difficulty
    private void gameOverOptions() {
        if (gameManager.getGameOver()) {
            System.out.println("Secret Word(s): ");
            for (Board board : gameManager.getGameBoards()) {
                System.out.println(board.getWordToGuess());
            }
            gameManager.resetGame();
            if (askPlayAgain()) {
                gameManager.changeDifficulty(askDifficulty());
                gameManager.resetGame();
            } else {
                gameManager.setGameOver(true);
                System.out.println("Game Over! Were you a Wordle Champion?");
                System.out.println("Final World of Wordle Score: " + gameManager.getScore());
            }
        }
    }

    // REQUIRES: gameOver must be false, has to be called after user inputs quitting sequence
    // MODIFIES: JSONWriter
    // EFFECTS: asks user if they want to save their game state before quitting the application
    //          if yes, serializes game state, otherwise does not save user's game
    //          throws exception if file being written does not exist
    private void askToSave() {
        System.out.println("Would you like to save your game for next time? (Y/n) >     ");
        String userAnswer = userInputScanner.next();

        if (!userAnswer.equalsIgnoreCase("Y")) {
            gameManager.resetGame();
            System.out.println("See you next time!");
        } else {
            System.out.println("See you next time! File saved at " + PERSISTENCE_FILE);
        }
        try {
            gameWriter.openWriter();
            gameWriter.writeGameManager(gameManager);
            gameWriter.closeWriter();
        } catch (IOException e) {
            System.out.println("Sorry, file not found and game could not be saved.");
        }
    }
}
