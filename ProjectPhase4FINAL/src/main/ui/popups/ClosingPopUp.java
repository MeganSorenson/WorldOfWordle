package ui.popups;

import ui.WordleGameGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// ClosingPopUp is a pop up window that shows up when the user closes the game
// asks the user if they want to save their progress or not
public class ClosingPopUp extends JDialog {
    private WordleGameGUI gameGUI;
    private static final int WINDOW_WIDTH = 750;
    private static final int WINDOW_HEIGHT = 180;

    // EFFECTS: creates an end of game pop up window with the appropriate title,
    //          text display, and save/do not save buttons
    //          this remembers the game gui that initiated itself
    public ClosingPopUp(JFrame gameWindow, String title, Boolean modal, WordleGameGUI gameGUI) {
        super(gameWindow, title, modal);
        this.gameGUI = gameGUI;

        setUpDialog();
        createText();
        createButtons();
        getContentPane().setBackground(new Color(0xDDD8D0));
        setVisible(true);
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
        JLabel text1 = new JLabel("Thanks for playing!");
        JLabel text2 = new JLabel("Click 'Save Game Progress' if you'd like to save "
                + "your World or Wordle game for next time.");
        JLabel text3 = new JLabel("Or click 'Do Not Save' if you just want to exit the game without saving.");

        text1.setFont(new Font("Calibri", Font.BOLD, 15));
        text2.setFont(new Font("Calibri", Font.BOLD, 15));
        text3.setFont(new Font("Calibri", Font.BOLD, 15));
        text1.setForeground(new Color(0x433447));
        text2.setForeground(new Color(0x433447));
        text3.setForeground(new Color(0x433447));

        add(text1);
        add(text2);
        add(text3);
    }

    // MODIFIES: this
    // EFFECTS: creates a functioning save last game and do not save buttons
    private void createButtons() {
        JButton saveGameButton = new JButton("Save Game Progress");
        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameGUI.saveGame();
                setVisible(false);
            }
        });

        JButton doNotSaveButton = new JButton("Do Not Save");
        doNotSaveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        setButtonAesthetics(saveGameButton, doNotSaveButton);

        add(saveGameButton);
        add(doNotSaveButton);
    }

    // MODIFIES: JButton
    // EFFECTS: sets the visual aesthetics of the savegame and donotsave buttons
    private void setButtonAesthetics(JButton saveGameButton, JButton doNotSaveGameButton) {
        saveGameButton.setForeground(new Color(0xDDD8D0));
        saveGameButton.setBackground(new Color(0x433447));
        saveGameButton.setFocusPainted(false);
        saveGameButton.setOpaque(true);
        saveGameButton.setBorderPainted(false);
        saveGameButton.setFont(new Font("Calibri", Font.BOLD, 15));

        doNotSaveGameButton.setForeground(new Color(0xDDD8D0));
        doNotSaveGameButton.setBackground(new Color(0x433447));
        doNotSaveGameButton.setFocusPainted(false);
        doNotSaveGameButton.setOpaque(true);
        doNotSaveGameButton.setBorderPainted(false);
        doNotSaveGameButton.setFont(new Font("Calibri", Font.BOLD, 15));
    }
}
