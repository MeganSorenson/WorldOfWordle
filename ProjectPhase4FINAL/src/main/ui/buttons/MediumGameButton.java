package ui.buttons;

import ui.bars.MenuBarUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// MediumGameButton is a button in the menu bar of a World of Wordle GUI
// when pressed, the WordleGameGUI creates a new MEDIUM wordle game
public class MediumGameButton extends JButton implements ActionListener {
    private final MenuBarUI panel;

    // EFFECTS: creates a button with the right label and makes sure that the button
    //          is actively listening for user input
    //          also sets the aesthetics of the button
    public MediumGameButton(String buttonName, MenuBarUI panel) {
        super(buttonName);
        this.panel = panel;
        addActionListener(this);

        setButtonAesthetics();
    }

    // MODIFIES: this
    // EFFECTS: sets button color, text color, help message, etc.
    private void setButtonAesthetics() {
        setToolTipText("Click this game to start a new MEDIUM World of Wordle game.");
        setBackground(new Color(0xE8C95D));
        setOpaque(true);
        setBorderPainted(false);
        setForeground(new Color(0x433447));
        setFocusPainted(false);
        setFont(new Font("Calibri", Font.BOLD, 15));
    }

    // MODIFIES: this
    // EFFECTS: when button is pressed by user, a new medium wordle game is started for the user
    //          otherwise button is waiting to be pressed
    @Override
    public void actionPerformed(ActionEvent e) {
        if ("disable".equals(e.getActionCommand())) {
            setEnabled(true);
        } else {
            setEnabled(false);
            panel.getGameWindow().newGame("MEDIUM");
            setEnabled(true);
        }
    }
}
