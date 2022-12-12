package ui.boards;

import model.FeedbackCharacter;

import javax.swing.*;
import java.awt.*;

// LetterFeedbackUI is the visible letter components of the wordle board that are created after user word guessing
// based on the letter feedback given by the game, produces a JLabel with the appropriate image for the user
public class LetterFeedbackUI extends JLabel {

    // EFFECTS: decides which text and color to be shown on the label
    //          based on the given letter feedback
    public LetterFeedbackUI(FeedbackCharacter letterColor) {
        String feedbackString = letterColor.getLetter() + letterColor.getColor();
        mapLetterCodeToColoredText(feedbackString);

    }

    // MODIFIES: this
    // EFFECTS: determines the appropriate text with color for the letter feedback
    private void mapLetterCodeToColoredText(String letterColor) {
        if (letterColor.equals("")) {
            // do nothing
        } else {
            char letter = letterColor.charAt(0);
            char color = letterColor.charAt(1);
            setText(Character.toString(letter));
            setFont(new Font("Calibri", Font.BOLD, 30));

            setColor(color);
        }
    }

    // REQUIRES: color is either "R", "Y", or "G"
    // MODIFIES: this
    // EFFECTS: sets the color of the label based on a given color character
    private void setColor(char color) {
        setForeground(new Color(0x433447));
        setBackground(mapColor(color));
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
    }

    // REQUIRES: color is either "R", "Y", or "G"
    // EFFECTS: returns a color based on the given character
    private Color mapColor(char color) {
        String colorString = Character.toString(color);
        if (colorString.equals("R")) {
            return new Color(0xD15B54);
        } else if (colorString.equals("Y")) {
            return new Color(0xE8C95D);
        } else {
            return new Color(0xA9D8C8);
        }
    }
}
