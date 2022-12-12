package ui.boards;

import model.Board;
import model.FeedbackCharacter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// SingleBoardPanel is the user interface for a single board in a World of Wordle game GUI
// adds the appropriate amount of rows depending on the game difficulty and creates visuals for the word guesses
public class SingleBoardPanel extends JPanel {
    private static final int NUM_COLS = 5;
    private static final int NUM_ROWS = 5;
    private int numRows;

    // EFFECTS: creates a single board interface with the right number of rows
    //          by adding a grid of labels for each word guess
    public SingleBoardPanel() {
        super(new GridLayout(NUM_ROWS, NUM_COLS));
        addLetters();

        setPreferredSize(new Dimension(300, 600));
        setBackground(new Color(0xDDD8D0));
    }

    // MODIFIES: this
    // EFFECTS: creates a single board interface with the right number of rows
    //          by adding a grid of images for each word guess
    public SingleBoardPanel(Board board, int numRows) {
        super(new GridLayout(numRows, NUM_COLS));
        this.numRows = numRows;
        addLetters(board.getRows());

        setPreferredSize(new Dimension(300, 600));
        setBackground(new Color(0xDDD8D0));
    }

    // MODIFIES: this
    // EFFECTS: adds the appropriate letter feedback to the Boards for the user to see
    //          in an initial game state (no active board rows)
    private void addLetters() {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int i2 = 0; i2 < NUM_COLS; i2++) {
                add(new LetterFeedbackUI(new FeedbackCharacter()));
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds the appropriate letter feedback to the Boards for the user to see
    //          based on already existing board rows
    private void addLetters(ArrayList<ArrayList<FeedbackCharacter>> boardRows) {
        for (int i = 0; i < numRows; i++) {
            for (int i2 = 0; i2 < NUM_COLS; i2++) {
                add(new LetterFeedbackUI(boardRows.get(i).get(i2)));
            }
        }
    }
}
