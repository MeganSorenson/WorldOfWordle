package ui.boards;


import model.Board;

import javax.swing.*;
import java.util.ArrayList;

// BoardUI is the wordle board components of a World of Wordle game GUI
// creates the appropriate amount of boards and adds them to a single JPanel
public class BoardUI extends JPanel {
    private final int numBoards;

    // EFFECTS: creates a board interface with the right number of boards
    public BoardUI() {
        numBoards = 1;
        addBoards();
    }

    // MODIFIES: this
    // EFFECTS: this remebers the number of current game boards and adds the game boards to the panel
    public BoardUI(ArrayList<Board> gameBoards) {
        this.numBoards = gameBoards.size();
        addBoards(gameBoards);
    }

    // MODIFIES: this
    // EFFECTS: add the appropriate amount of boards to the UI
    private void addBoards() {
        for (int i = 0; i < numBoards; i++) {
            add(new SingleBoardPanel());
        }
    }

    // MODIFIES: this
    // EFFECTS: add the appropriate amount of boards to the UI
    private void addBoards(ArrayList<Board> gameBoards) {
        for (int i = 0; i < numBoards; i++) {
            Board board = gameBoards.get(i);
            add(new SingleBoardPanel(board, board.getRows().size()));
        }
    }
}
