package model;

// FeedbackCharacter is the letter feedback of a word guess
// as represented by a letter with a color representing the letter's correctness
// red ("R") means that the letter does not exist in a word
// yellow ("Y") means that the letter exists in a word but is not in the right position
// green ("G") means that the letter exists in a word and is in the right position
// "" means no color and corresponds with letter being ""
public class FeedbackCharacter {


    private String letter;
    private String color;

    // EFFECTS: creates a letter feedback with default color and letter values
    public FeedbackCharacter() {
        letter = "";
        color = "";
    }

    // EFFECTS: returns letter
    public String getLetter() {
        return letter;
    }

    // EFFECTS: returns color
    public String getColor() {
        return color;
    }

    // REQUIRES: letter must be a single uppercase character in the alphabet
    // MODIFIES: this
    // EFFECTS: sets letter to the provided letter
    public void setLetter(String letter) {
        this.letter = letter;
    }

    // REQUIRES: color must be either "R" or "Y" or "G"
    // MODIFIES: this
    // EFFECTS: sets color to the provided color
    public void setColor(String color) {
        this.color = color;
    }
}
