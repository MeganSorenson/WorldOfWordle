package ui.popups;

import ui.WordleGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// StartPopUp is a pop up window that shows up when the user open the game
// provides game instructions and asks the user if they want to load the last game or not
public class StartPopUp extends JDialog {
    private WordleGameGUI gameGUI;
    private static final int WINDOW_WIDTH = 780;
    private static final int WINDOW_HEIGHT = 500;

    // EFFECTS: creates a start of game pop up window with the appropriate title,
    //          text display, and load/do not load buttons
    //          this remembers the game gui that initiated itself
    public StartPopUp(JFrame gameWindow, String title, Boolean modal, WordleGameGUI gameGUI) {
        super(gameWindow, title, modal);
        this.gameGUI = gameGUI;

        setUpDialog();
        addImage();
        createText();
        createButtons();
        getContentPane().setBackground(new Color(0xDDD8D0));
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: creates an image icon of the wordle game character and adds it to the pop up window
    //          if the image path does not exist, does nothing and image is not added to pop up
    private void addImage() {
        try {
            ImageIcon wordleImage = new ImageIcon("data/wordleImage.png");
            add(new JLabel(wordleImage));
        } catch (Exception e) {
            // do nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the layout and size of the pop up
    private void setUpDialog() {
        setLayout(new FlowLayout());
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    // MODIFIES: this
    // EFFECTS: creates the text on the pop up that explains the game
    //          and asks whether the user wants to load their previous game
    private void createText() {
        JLabel title = new JLabel("WORLD OF WORDLE: like Wordle but on steroids!");
        addLabelWithAesthetics(title, 20);

        addInstructions();

        JLabel continueGame = new JLabel("Click 'Continue Last Saved Game' if you'd "
                + "like to continue playing from last time.");
        JLabel newGame = new JLabel("Or click 'Play A Whole New Game' if you'd like to start from scratch.");
        addLabelWithAesthetics(continueGame,15);
        addLabelWithAesthetics(newGame, 15);
    }

    // MODIFIES: this
    // EFFECTS: adds the Wordle game instructions to the text on the starting pop up
    private void addInstructions() {
        String welcomeInstructions = "Each board has a secret 5-letter word that you are trying to guess "
                + "using 5-letter words";
        JLabel welcome = new JLabel(welcomeInstructions);
        addLabelWithAesthetics(welcome,15);

        addLetterColorInstructions();

        String middleInstructions = "There are three game difficulties to play:";
        JLabel middle = new JLabel(middleInstructions);
        addLabelWithAesthetics(middle,15);

        addGameDifficultyInstructions();

        String endInstructions = "Maximize your score by guessing the secret word(s) in as little guesses as possible!";
        JLabel end = new JLabel(endInstructions);
        addLabelWithAesthetics(end,15);
    }

    // MODIFIES: this
    // EFFECTS: Adds text label to this with the given text size
    //          and other constant text aesthetics
    private void addLabelWithAesthetics(JLabel textLabel,Integer textSize) {
        textLabel.setFont(new Font("Calibri", Font.BOLD, textSize));
        textLabel.setForeground(new Color(0x433447));
        add(textLabel);
    }

    // MODIFIES: this
    // EFFECTS: adds the Wordle game letter color instructions to the text on the starting pop up
    private void addLetterColorInstructions() {
        String greenInstructions = "- GREEN letters from your guess mean that they are in the correct position";
        String yellowInstructions = "- YELLOW letters mean that they are "
                + "not in the right position but still in the secret word";
        String redInstructions = "- RED letters from your guess mean that they are not in the secret word";

        JLabel green = new JLabel(greenInstructions);
        green.setBackground(new Color(0xA9D8C8));
        green.setOpaque(true);
        addLabelWithAesthetics(green,15);

        JLabel yellow = new JLabel(yellowInstructions);
        yellow.setBackground(new Color(0xE8C95D));
        yellow.setOpaque(true);
        addLabelWithAesthetics(yellow,15);

        JLabel red = new JLabel(redInstructions);
        red.setBackground(new Color(0xD15B54));
        red.setOpaque(true);
        addLabelWithAesthetics(red,15);
    }

    // MODIFIES: this
    // EFFECTS: adds the Wordle game difficulty instructions to the text on the starting pop up
    private void addGameDifficultyInstructions() {
        String easyInstructions = "- EASY games have you trying to guess 1 secret word in 5 guesses";
        String mediumInstructions = "- MEDIUM games have you trying to simultaneously guess 2 secret "
                + "words in 7 guesses";
        String hardInstructions = "       - HARD games have you trying to simultaneously guess 3 "
                + "secret words in 9 guesses";

        JLabel easy = new JLabel(easyInstructions);
        addLabelWithAesthetics(easy,15);
        easy.setBackground(new Color(0xA9D8C8));
        easy.setOpaque(true);

        JLabel medium = new JLabel(mediumInstructions);
        addLabelWithAesthetics(medium, 15);
        medium.setBackground(new Color(0xE8C95D));
        medium.setOpaque(true);

        JLabel hard = new JLabel(hardInstructions);
        addLabelWithAesthetics(hard,15);
        hard.setBackground(new Color(0xD15B54));
        hard.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: creates a functioning load last game and play new game buttons
    private void createButtons() {
        JButton loadGameButton = new JButton("Continue Last Saved Game");
        loadGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.loadGame();
                setVisible(false);
            }
        });

        JButton playNewGameButton = new JButton("Play a Whole New Game");
        playNewGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setButtonAesthetics(loadGameButton, playNewGameButton);

        add(loadGameButton);
        add(playNewGameButton);
    }

    // MODIFIES: JButton
    // EFFECTS: sets the visual aesthetics of the loadgame and donotload buttons
    private void setButtonAesthetics(JButton loadGameButton, JButton doNotLoadButton) {
        loadGameButton.setForeground(new Color(0xDDD8D0));
        loadGameButton.setBackground(new Color(0x433447));
        loadGameButton.setFocusPainted(false);
        loadGameButton.setOpaque(true);
        loadGameButton.setBorderPainted(false);
        loadGameButton.setFont(new Font("Calibri", Font.BOLD, 15));

        doNotLoadButton.setForeground(new Color(0xDDD8D0));
        doNotLoadButton.setBackground(new Color(0x433447));
        doNotLoadButton.setFocusPainted(false);
        doNotLoadButton.setOpaque(true);
        doNotLoadButton.setBorderPainted(false);
        doNotLoadButton.setFont(new Font("Calibri", Font.BOLD, 15));
    }
}
