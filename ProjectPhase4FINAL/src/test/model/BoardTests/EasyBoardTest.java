package model.BoardTests;

import model.Board;
import model.FeedbackCharacter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// complete test suite for the Board class using an "easy" difficulty
public class EasyBoardTest implements BoardTest {
    private Board testBoard;
    private int boardSize;
    private String allRowsDefaultString;


    @BeforeEach
    public void setUp() {
        boardSize = 5;
        testBoard = new Board(boardSize, 1);
        allRowsDefaultString = "|/////|/////|/////|/////|/////";
    }

    @Override
    @Test
    public void testConstructor() {
        assertEquals(boardSize, testBoard.getMaxNumberOfGuesses());
        assertEquals(boardSize, testBoard.getRows().size());
        assertEquals(1, testBoard.getBoardID());
        assertEquals(5, testBoard.getWordToGuess().length());
    }

    @Test
    public void testConstructorTwo() {
        Board testBoard2 = new Board(testBoard.getRows(), "ABCDE",
                0, false, 5, 1);
        assertEquals(boardSize, testBoard2.getMaxNumberOfGuesses());
        assertEquals(boardSize, testBoard2.getRows().size());
        assertEquals(1, testBoard2.getBoardID());
        assertEquals("ABCDE", testBoard2.getWordToGuess());
        assertFalse(testBoard2.isGuessed());
    }

    @Test
    public void testConstructorFileExceptionThrown() {
        testBoard.setWordFileName("abc");
        boolean exceptionThrown;
        try {
            new Board(boardSize, 2);
            exceptionThrown = false;
        } catch (RuntimeException e) {
            exceptionThrown = true;
        }
        assertTrue(exceptionThrown);
        testBoard.setWordFileName("data/fiveLetterWords.txt");
    }

    @Override
    @Test
    public void testCreateRows() {
        assertEquals(boardSize, testBoard.getRows().size());
        for (ArrayList<FeedbackCharacter> feedbackCharacterList : testBoard.getRows()) {
            assertEquals(5, feedbackCharacterList.size());
        }
    }

    @Override
    @Test
    public void testCheckGuessCorrectGuess() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        testBoard.checkGuess("ABCDE");

        assertEquals(1, testBoard.getNumberOfGuessesMade());
        assertTrue(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/AG/BG/CG/DG/EG|/////|/////|/////|/////",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessHalfWrongGuess() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        testBoard.checkGuess("XXCED");

        assertEquals(1, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/CG/EY/DY|/////|/////|/////|/////",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessCompletelyWrongGuess() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        testBoard.checkGuess("XXXXX");

        assertEquals(1, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/XR/XR/XR|/////|/////|/////|/////",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessWordAlreadyGuessed() {
        testBoard.setWordToGuess("ABCDE");
        testBoard.checkGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(1, testBoard.getNumberOfGuessesMade());
        assertTrue(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/AG/BG/CG/DG/EG|/////|/////|/////|/////",
                testBoard.getAllRowsAsString());

        testBoard.checkGuess("XXXXX");

        assertEquals(1, testBoard.getNumberOfGuessesMade());
        assertTrue(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/AG/BG/CG/DG/EG|/////|/////|/////|/////",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessReachMaximumNumberGuesses() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        for (int i = 0; i < testBoard.getMaxNumberOfGuesses(); i++) {
            testBoard.checkGuess("XXXXX");
        }

        // note that checkGuess calls updateRows() which is later tested in this suite
        assertEquals(testBoard.getMaxNumberOfGuesses(), testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertTrue(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessRightInMiddleOfGuesses() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        for (int i = 0; i < (testBoard.getMaxNumberOfGuesses() - 3); i++) {
            testBoard.checkGuess("XXXXX");
        }
        testBoard.checkGuess("ABCDE");

        // note that checkGuess calls updateRows() which is later tested in this suite
        assertEquals(testBoard.getMaxNumberOfGuesses() - 2, testBoard.getNumberOfGuessesMade());
        assertTrue(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/AG/BG/CG/DG/EG|/////|/////",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessRightOnLastGuess() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        for (int i = 0; i < (testBoard.getMaxNumberOfGuesses() - 1); i++) {
            testBoard.checkGuess("XXXXX");
        }
        testBoard.checkGuess("ABCDE");

        // note that checkGuess calls updateRows() which is later tested in this suite
        assertEquals(testBoard.getMaxNumberOfGuesses(), testBoard.getNumberOfGuessesMade());
        assertTrue(testBoard.isGuessed());
        assertTrue(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/AG/BG/CG/DG/EG",
                testBoard.getAllRowsAsString());
    }

    @Override
    @Test
    public void testCheckGuessAfterReachedMaxGuesses() {
        testBoard.setWordToGuess("ABCDE");
        assertEquals("ABCDE", testBoard.getWordToGuess());
        assertEquals(0, testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertFalse(testBoard.hasReachedMaxGuesses());
        assertEquals(allRowsDefaultString,
                testBoard.getAllRowsAsString());

        for (int i = 0; i < testBoard.getMaxNumberOfGuesses(); i++) {
            testBoard.checkGuess("XXXXX");
        }
        testBoard.checkGuess("ABCDE");

        // note that checkGuess calls updateRows() which is later tested in this suite
        assertEquals(testBoard.getMaxNumberOfGuesses(), testBoard.getNumberOfGuessesMade());
        assertFalse(testBoard.isGuessed());
        assertTrue(testBoard.hasReachedMaxGuesses());
        assertEquals("|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR",
                testBoard.getAllRowsAsString());
    }
}
