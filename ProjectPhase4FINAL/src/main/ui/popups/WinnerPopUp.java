package ui.popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// WinnerPopUp is a pop up dialog that shows up after a user has finished a wordle game
// and has guessed all of the game boards' words
public class WinnerPopUp extends JDialog {
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 100;

    private JButton okButton;

    // EFFECTS: creates a winner pop up window with the appropriate title,
    //          text display, and exit button
    public WinnerPopUp(JFrame gameWindow, String title, Boolean modal) {
        super(gameWindow, title, modal);

        setUpDialog();
        createText();
        createButton();
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
    // EFFECTS: creates the text on the pop up that explains that the user has won
    private void createText() {
        JLabel text1 = new JLabel("I sense the makings of a Wordle Champion!");
        JLabel text2 = new JLabel("Click the button to return to the game.     ");

        text1.setFont(new Font("Calibri", Font.BOLD, 15));
        text2.setFont(new Font("Calibri", Font.BOLD, 15));
        text1.setForeground(new Color(0x433447));
        text2.setForeground(new Color(0x433447));

        add(text1);
        add(text2);
    }

    // MODIFIES: this
    // EFFECTS: creates a functioning exit button for the pop up
    private void createButton() {
        okButton = new JButton("Exit");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        okButton.setForeground(new Color(0x433447));
        okButton.setBackground(new Color(0xA9D8C8));
        okButton.setFocusPainted(false);
        okButton.setOpaque(true);
        okButton.setBorderPainted(false);
        okButton.setFont(new Font("Calibri", Font.BOLD, 15));

        add(okButton, FlowLayout.RIGHT);
    }

}
