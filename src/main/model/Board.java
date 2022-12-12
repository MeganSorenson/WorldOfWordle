package model;

import java.io.IOException;
import java.util.ArrayList;

// Board represents a Wordle game board that has a word that a user will be trying to guess,
// keeps track of user guesses and checks user guesses against its own word, and knows whether it has been
// guessed or not
public class Board {

    private String wordToGuess;
    private final int boardID;
    private boolean guessed = false;
    private boolean reachedMaxGuesses = false;
    private static final int WORD_LENGTH = 5;
    private int numberOfGuessesMade = 0;
    private final int maxNumberOfGuesses;
    private ArrayList<ArrayList<FeedbackCharacter>> rows = new ArrayList<>();
    private static String wordFileName = "data/fiveLetterWords.txt";

    // EFFECTS: creates a board with the appropriate amount of rows
    //          board remembers the given max number of guesses
    //          assigns a random word to the board to be guessed
    public Board(int maxNumberOfGuesses, int id) {
        this.maxNumberOfGuesses = maxNumberOfGuesses;
        createRows();
        boardID = id;

        WordDict wordDict;
        try {
            wordDict = new WordDict(wordFileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        wordToGuess = wordDict.getRandomWord();

    }

    // REQUIRES: called only when application is reopened
    //           and user wants to reload previous game data
    // EFFECTS: board remembers the given rows, word to guess, number of guesses made,
    //          max number of guesses, board id, and whether it is guessed
    public Board(ArrayList<ArrayList<FeedbackCharacter>> rows, String wordToGuess, int numberGuessesMade,
                 boolean guessed, int maxNumberOfGuesses, int boardID) {
        this.rows = rows;
        this.wordToGuess = wordToGuess;
        this.numberOfGuessesMade = numberGuessesMade;
        this.maxNumberOfGuesses = maxNumberOfGuesses;
        this.boardID = boardID;
        this.guessed = guessed;

    }

    // MODIFIES: this
    // EFFECTS: creates board rows that contains a null representation of a word guess
    //          number of rows according to board's difficulty
    private void createRows() {
        for (int i = 0; i < maxNumberOfGuesses; i++) {
            ArrayList<FeedbackCharacter> row = new ArrayList<>();
            for (int characterIndex = 0; characterIndex < WORD_LENGTH; characterIndex++) {
                FeedbackCharacter nullFeedbackCharacter = new FeedbackCharacter();
                row.add(nullFeedbackCharacter);
            }
            rows.add(row);
        }
    }

    // REQUIRES: wordGuess needs to be a five-letter word in all caps
    // MODIFIES: this
    // EFFECTS: adds a valid word guess to a board row
    //          unless the board's word has been guessed or if there are no more available guesses for the board
    //          board keeps track of if the word guess has guessed the board's word to guess
    //          and how many valid guesses have been made on the board
    public void checkGuess(String wordGuess) {
        if (!hasReachedMaxGuesses()) {
            if (!guessed) {
                char[] guessLetters = wordGuess.toCharArray();
                updateRows(guessLetters);
                numberOfGuessesMade++;

                if (numberOfGuessesMade >= maxNumberOfGuesses) {
                    reachedMaxGuesses = true;
                }
            }

            if (wordGuess.equals(wordToGuess)) {
                guessed = true;
            }
        }
    }

    // REQUIRES: guessLetters to be an array list of five single uppercase characters
    // MODIFIES: this, FeedBackCharacter
    // EFFECTS: adds the letter feedback of a valid word guess to a board row
    private void updateRows(char[] guessLetters) {
        char[] targetLetters = wordToGuess.toCharArray();
        ArrayList<FeedbackCharacter> row = new ArrayList<>();

        for (int characterIndex = 0; characterIndex < WORD_LENGTH; characterIndex++) {
            FeedbackCharacter feedbackCharacter = new FeedbackCharacter();
            String guessLetter = Character.toString(guessLetters[characterIndex]);
            String targetLetter = Character.toString(targetLetters[characterIndex]);
            feedbackCharacter.setLetter(guessLetter);

            if (guessLetter.equals(targetLetter)) {
                feedbackCharacter.setColor("G");
            } else if (wordToGuess.contains(guessLetter)) {
                feedbackCharacter.setColor("Y");
            } else {
                feedbackCharacter.setColor("R");
            }
            row.add(feedbackCharacter);
        }
        rows.set(numberOfGuessesMade, row);
    }

    // EFFECTS: returns a string representation of the board rows
    //          new row beginning with |
    //          new letter feedback beginning with /
    //          letter feedback shown as (L)etter feedback (C)olor -> LC
    //          for testing and writing to JSON
    public String getAllRowsAsString() {
        StringBuilder allRows = new StringBuilder();
        for (ArrayList<FeedbackCharacter> row : rows) {
            allRows.append("|");
            for (FeedbackCharacter feedbackCharacter : row) {
                allRows.append("/");
                allRows.append(feedbackCharacter.getLetter());
                allRows.append(feedbackCharacter.getColor());
            }
        }
        return allRows.toString();
    }

    // EFFECTS: returns the board word trying to be guessed
    public String getWordToGuess() {
        return wordToGuess;
    }

    // MODIFIES: this
    // EFFECTS: sets the board word trying to be guessed
    //          solely for testing purposes
    public void setWordToGuess(String newWord) {
        wordToGuess = newWord;
    }

    // EFFECTS: returns a list of the board's rows
    public ArrayList<ArrayList<FeedbackCharacter>> getRows() {
        return rows;
    }

    // EFFECTS: returns the boardID
    public int getBoardID() {
        return boardID;
    }

    // EFFECTS: returns whether the board's word has been guessed or not
    public boolean isGuessed() {
        return guessed;
    }

    // EFFECTS: returns whether the board has reached the max number of guesses or not
    public boolean hasReachedMaxGuesses() {
        return reachedMaxGuesses;
    }

    // EFFECTS: returns the number of guesses made on the board
    public int getNumberOfGuessesMade() {
        return numberOfGuessesMade;
    }

    // EFFECTS: returns the max number of guesses that can be made on the board
    public int getMaxNumberOfGuesses() {
        return maxNumberOfGuesses;
    }

    // MODIFIES: this
    //EFFECTS: sets the file name of the list of possible words
    //         solely for testing purposes
    public void setWordFileName(String fileName) {
        wordFileName = fileName;
    }

}
