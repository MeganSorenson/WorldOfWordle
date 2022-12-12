package ui.bars;

import ui.windows.AllGameWindowComponents;
import ui.buttons.EasyGameButton;
import ui.buttons.HardGameButton;
import ui.buttons.MediumGameButton;

import javax.swing.*;
import java.awt.*;

// MenuBarUI is the manu bar component of a World of Wordle game GUI
// creates the score label, number of guesses label,
// and easy/medium/hard new game buttons and adds then to a single JPanel
public class MenuBarUI extends JPanel {
    private AllGameWindowComponents gameWindow;
    private JLabel scoreInfo;
    private int score;

    private JButton newEasyGameButton;
    private JButton newMediumGameButton;
    private JButton newHardGameButton;

    // EFFECTS: creates the score label and easy/medium/hard new game buttons
    public MenuBarUI(AllGameWindowComponents gameWindow) {
        this.gameWindow = gameWindow;
        this.score = 0;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setUpScoreLabel(this.score);
        createNewGameButtons();
    }

    // EFFECTS: creates the score label and easy/medium/hard new game buttons with the proper score
    public MenuBarUI(AllGameWindowComponents gameWindow, int score) {
        this.gameWindow = gameWindow;
        this.score = score;
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setUpScoreLabel(score);
        createNewGameButtons();
    }

    // MODIFIES: this
    // EFFECTS: gets the score of the game, creates a score label, and adds it to the menu bar
    private void setUpScoreLabel(int score) {
        scoreInfo = new JLabel("     Score: " + score + "                            ");
        scoreInfo.setFont(new Font("Calibri", Font.BOLD, 25));
        scoreInfo.setForeground(new Color(0x433447));
        add(scoreInfo);
    }

    // MODIFIES: this
    // EFFECTS: creates a new easy game, new medium game, and new hard game button
    //          and adds them to the menu bar
    private void createNewGameButtons() {
        newEasyGameButton = new EasyGameButton("New Easy Game", this);
        newMediumGameButton = new MediumGameButton("New Medium Game", this);
        newHardGameButton = new HardGameButton("New Hard Game", this);

        add(newEasyGameButton);
        add(newMediumGameButton);
        add(newHardGameButton);
    }

    // EFFECTS: returns the game window that contains this panel
    public AllGameWindowComponents getGameWindow() {
        return gameWindow;
    }

}
