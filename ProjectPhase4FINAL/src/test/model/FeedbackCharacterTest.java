package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Tests the functionality of the FeedbackCharacter class
class FeedbackCharacterTest {

    private FeedbackCharacter testFeedbackCharacter;

    @BeforeEach
    public void setUp() {
        testFeedbackCharacter = new FeedbackCharacter();
    }

    @Test
    public void testConstructor() {
        assertEquals("", testFeedbackCharacter.getLetter());
        assertEquals("", testFeedbackCharacter.getColor());
    }

    @Test
    public void testSetLetterOnce() {
        assertEquals("", testFeedbackCharacter.getLetter());
        testFeedbackCharacter.setLetter("A");
        assertEquals("A", testFeedbackCharacter.getLetter());
    }

    @Test
    public void testSetLetterMultipleTimes() {
        assertEquals("", testFeedbackCharacter.getLetter());

        for (int i = 0; i < 20; i++) {
            testFeedbackCharacter.setLetter("B");
        }
        testFeedbackCharacter.setLetter("C");

        assertEquals("C", testFeedbackCharacter.getLetter());
    }

    @Test
    public void testSetColorOnce() {
        assertEquals("", testFeedbackCharacter.getColor());
        testFeedbackCharacter.setColor("G");
        assertEquals("G", testFeedbackCharacter.getColor());
    }

    @Test
    public void testSetColorMultipleTimes() {
        assertEquals("", testFeedbackCharacter.getColor());

        for (int i = 0; i < 20; i++) {
            testFeedbackCharacter.setColor("Y");
        }
        testFeedbackCharacter.setColor("R");

        assertEquals("R", testFeedbackCharacter.getColor());
    }

}