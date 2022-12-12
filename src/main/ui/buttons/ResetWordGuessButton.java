package ui.buttons;

import ui.bars.WordGuessBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ResetWordGuessButton is a button in the word guess panel of a World of Wordle game GUI
// when pressed, resets the text in the user word guess input box
public class ResetWordGuessButton extends JButton implements ActionListener {
    private final WordGuessBarUI panel;

    // EFFECTS: creates a button with the proper label, makes sure that the button is actively listening
    //          for user input, and sets the aesthetics of the button
    public ResetWordGuessButton(String buttonName, WordGuessBarUI panel) {
        super(buttonName);
        this.panel = panel;
        addActionListener(this);

        setButtonAesthetics();
    }

    // MODIFIES: this
    // EFFECTS: sets button color, text color, help message. etc
    private void setButtonAesthetics() {
        setToolTipText("Click this button to reset you word guess input box.");
        setBackground(new Color(0xD16B54));
        setOpaque(true);
        setBorderPainted(false);
        setForeground(new Color(0x433447));
        setFocusPainted(false);
        setFont(new Font("Calibri", Font.BOLD, 15));
    }

    // REQUIRES: panel must have an active wordGuessTextInput
    // MODIFIES: this, WordGuessBarUI
    // EFFECTS: when button is pressed by user, word text in word guess input box is reset
    //          otherwise button is ready for user input
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            setEnabled(true);
        } else {
            setEnabled(false);
            panel.setTextInput("");
            setEnabled(true);
        }
    }
}
