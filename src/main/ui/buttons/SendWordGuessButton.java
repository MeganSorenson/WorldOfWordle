package ui.buttons;

import ui.bars.WordGuessBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// SendWordGuessButton is a button in the user word guess panel of a World of Wordle game GUI
// when pressed, sends the user guess to the boards to change the game state via making a word guess on the boards
public class SendWordGuessButton extends JButton implements ActionListener {
    private final WordGuessBarUI panel;

    // EFFECTS: creates a button with the proper label, makes sure that the button is
    //          actively listening for user input, and sets the aesthetics of the button
    public SendWordGuessButton(String buttonName, WordGuessBarUI panel) {
        super(buttonName);
        this.panel = panel;
        addActionListener(this);

        setButtonAesthetics();
    }

    // MODIFIES: this
    // EFFECTS: sets button color, text color, help message, etc.
    private void setButtonAesthetics() {
        setToolTipText("Click this button to send a 5-letter word guess to the Wordle board(s).");
        setBackground(new Color(0xA9D8C8));
        setOpaque(true);
        setBorderPainted(false);
        setForeground(new Color(0x433447));
        setFocusPainted(false);
        setFont(new Font("Calibri", Font.BOLD, 15));
    }

    // REQUIRES: panel must have an active wordGuessTextInput
    // MODIFIES: this, WordGuessBarUI
    // EFFECTS: when button is pressed by user, word guess is sent to the game boards which
    //          changes the state of the game
    //          otherwise button is waiting for user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            setEnabled(true);
        } else {
            setEnabled(false);
            // send text in text input box to the game window to that it can send it to the game boards
            panel.getGameWindow().sendUserGuess(panel.getTextInput());
            setEnabled(true);
        }
    }
}
