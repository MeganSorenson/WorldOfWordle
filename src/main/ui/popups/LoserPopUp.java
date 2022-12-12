package ui.popups;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// LoserPopUp is a pop up dialog that shows up after a user has finished a wordle game
// but has not guessed all of the game boards' words
public class LoserPopUp extends JDialog {
    private static final int WINDOW_WIDTH = 570;
    private static final int WINDOW_HEIGHT = 120;

    private JButton okButton;

    // EFFECTS: creates a loser pop up window with the appropriate title,
    //          text display, and exit button
    //          also incorporates the wrongly guessed word(s) into the text
    public LoserPopUp(JFrame gameWindow, String title, Boolean modal, String words) {
        super(gameWindow, title, modal);

        setUpDialog();
        createText(words);
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
    // EFFECTS: creates the text on the pop up that explains that the user has lost
    //          and what the secret words were for each board
    private void createText(String words) {
        JLabel message = new JLabel("Champions need to practice. Better luck next time!");
        JLabel boardWords = new JLabel(words);
        JLabel buttonAsk = new JLabel("Click the button to return to the game.       ");

        message.setFont(new Font("Calibri", Font.BOLD, 15));
        buttonAsk.setFont(new Font("Calibri", Font.BOLD, 15));
        boardWords.setFont(new Font("Calibri", Font.BOLD, 15));
        message.setForeground(new Color(0x433447));
        buttonAsk.setForeground(new Color(0x433447));
        boardWords.setForeground(new Color(0x433447));

        add(message);
        add(boardWords);
        add(buttonAsk);
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
        okButton.setBackground(new Color(0xD16B54));
        okButton.setFocusPainted(false);
        okButton.setOpaque(true);
        okButton.setBorderPainted(false);
        okButton.setFont(new Font("Calibri", Font.BOLD, 15));

        add(okButton);
    }
}
