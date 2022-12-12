package ui.bars;

import ui.windows.AllGameWindowComponents;
import ui.buttons.ResetWordGuessButton;
import ui.buttons.SendWordGuessButton;

import javax.swing.*;
import java.awt.*;

// WordGuessBarUI is the part of a World of Wordle Game GUI where the user
// can input word guesses and send them to the game boards
// creates the text input and buttons necessary for these user actions and combines them into a single panel
public class WordGuessBarUI extends JPanel {
    private AllGameWindowComponents gameWindow;
    private JButton makeWordGuessButton;
    private JButton resetWordGuessTextButton;

    private JTextField wordGuessTextInput;

    // EFFECTS: creates a word guess panel composed of a text field and buttons
    //          this remembers the game window that it belongs to
    public WordGuessBarUI(AllGameWindowComponents gameWindow) {
        this.gameWindow = gameWindow;
        createWordGuessTextField();
        createWordGuessButtons();
    }

    // MODIFIES: this
    // EFFECTS: creates the 'make word guess' and 'reset' buttons for user word guess input
    //          and adds buttons to the word guess input panel
    private void createWordGuessButtons() {
        makeWordGuessButton = new SendWordGuessButton("Make Word Guess", this);
        resetWordGuessTextButton = new ResetWordGuessButton("Reset", this);
        add(makeWordGuessButton);
        add(resetWordGuessTextButton);
    }

    // MODIFIES: this
    // EFFECTS: creates the text input for user word guesses and labels it with instructions
    //          and adds text input to the word guess input panel
    private void createWordGuessTextField() {
        JLabel wordGuessLabel = new JLabel("Enter a 5-letter Word Guess:");
        wordGuessLabel.setFont(new Font("Calibri", Font.BOLD, 20));
        wordGuessLabel.setForeground(new Color(0x433447));
        wordGuessTextInput = new JTextField(5);

        add(wordGuessLabel);
        add(wordGuessTextInput);
    }

    // REQUIRES: wordGuessTextInput must be active
    // EFFECTS: returns the text in the text input box
    public String getTextInput() {
        return wordGuessTextInput.getText();
    }

    // MODIFIES: JTextField
    // EFFECTS: sets the text input box's content to an empty string
    public void setTextInput(String text) {
        wordGuessTextInput.setText(text);
    }

    // EFFECTS: returns the game window that contains this panel
    public AllGameWindowComponents getGameWindow() {
        return gameWindow;
    }
}
