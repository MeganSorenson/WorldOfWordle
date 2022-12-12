package ui.windows;

import ui.WordleGameGUI;
import ui.popups.ClosingPopUp;
import ui.popups.StartPopUp;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// MainGameWindow is the visual game window for a World of Wordle game
public class MainGameWindow extends JFrame {
    private AllGameWindowComponents gameWindow;
    private WordleGameGUI gameGUI;
    private static final int WINDOW_WIDTH = 1000;
    private static final int WINDOW_HEIGHT = 700;

    // EFFECTS: creates the visual game window for a wordle game
    //          this remembers the game window and gui that it belongs to
    public MainGameWindow(AllGameWindowComponents gameWindow, WordleGameGUI gameGUI) {
        this.gameWindow = gameWindow;
        this.gameGUI = gameGUI;
        setUpWindow();
    }


    // MODIFIES: this
    // EFFECTS: tells the window what to do when exited,
    //          sets window size to specified width and height
    private void setUpWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                new StartPopUp(gameWindow.getGameWindow(), "Welcome!", true, gameGUI);
            }

            @Override
            public void windowClosing(WindowEvent e) {
                new ClosingPopUp(gameWindow.getGameWindow(), "GoodBye!", true, gameGUI);
                gameGUI.getGameManager().printLog();
                System.exit(0);
            }
        });
    }
}
