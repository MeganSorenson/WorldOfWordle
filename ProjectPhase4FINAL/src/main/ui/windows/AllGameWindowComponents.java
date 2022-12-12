package ui.windows;

import model.Board;
import model.WordleGameManager;
import ui.WordleGameGUI;
import ui.boards.BoardUI;
import ui.bars.MenuBarUI;
import ui.bars.WordGuessBarUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// AllGameWindowComponents is the user interface side of a World of Wordle game in a Java swing GUI
// mainly sets up game window to incorporate all the front end components
public class AllGameWindowComponents {
    private WordleGameGUI gameGUI;
    private final JFrame gameWindow;
    private JPanel wordGuessPanel;
    private BoardUI boardPanel;
    private MenuBarUI menuPanel;

    // EFFECTS: this remembers all components of the game window and the game gui it belongs to
    //          adds all components to one window
    public AllGameWindowComponents(WordleGameGUI gameGUI) {
        this.gameGUI = gameGUI;
        gameWindow = new MainGameWindow(this, gameGUI);
        wordGuessPanel = new WordGuessBarUI(this);
        boardPanel = new BoardUI();
        menuPanel = new MenuBarUI(this);
        addComponentsToWindow();
        setUpGameWindow();
    }

    // MODIFIES: gameWindow
    // EFFECTS: adds all window components to the window so that they are visible to the user
    private void addComponentsToWindow() {
        gameWindow.add(menuPanel, BorderLayout.NORTH);
        gameWindow.add(boardPanel, BorderLayout.CENTER);
        gameWindow.add(wordGuessPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: gameWindow
    // EFFECTS: sets the game window title, and makes the game window visible to the user
    private void setUpGameWindow() {
        gameWindow.setTitle("World Of Wordle");
        gameWindow.setVisible(true);
    }

    // MODIFIES: WordleGameManager, Board, LetterColor
    // EFFECTS: sends user guess to the game gui to play one turn of the game
    public void sendUserGuess(String userGuess) {
        gameGUI.playOneTurn(userGuess);
    }

    // MODIFIES: this, MainGameWindow
    // EFFECTS: sends game state to the board user interfaces and updates the display
    //          of the boards accordingly
    public void updateBoards(ArrayList<Board> gameBoards) {
        gameWindow.remove(boardPanel);
        boardPanel = new BoardUI(gameBoards);
        repaintBoardPanel();
    }

    // MODIFIES: this, MenuUI
    // EFFECTS: sends the new score to the menu bar and score is visually updated
    public void updateScore(int score) {
        gameWindow.remove(menuPanel);
        menuPanel = new MenuBarUI(this, score);
        repaintMenuPanel();
    }

    // MODIFIES: this, MenuUI
    // EFFECTS: updates the menu panel by repainting it on the window
    private void repaintMenuPanel() {
        gameWindow.add(menuPanel, BorderLayout.NORTH);
        gameWindow.validate();
        gameWindow.repaint();
        gameWindow.setVisible(true);
    }

    // MODIFIES: this, MainGameWindow
    // EFFECTS: updates the visuals of the board by repainting it on the window
    private void repaintBoardPanel() {
        gameWindow.add(boardPanel, BorderLayout.CENTER);
        gameWindow.validate();
        gameWindow.repaint();
        gameWindow.setVisible(true);
    }

    // MODIFIES: this, WordleGameManager, BoardUI
    // EFFECTS: creates a new Wordle Game at the specified difficulty
    //          updates the board visuals accordingly
    public void newGame(String difficulty) {
        WordleGameManager gameManager = gameGUI.getGameManager();
        gameManager.changeDifficulty(difficulty);
        updateBoards(gameManager.getGameBoards());

    }

    // EFFECTS: returns the game window
    public JFrame getGameWindow() {
        return gameWindow;
    }
}
