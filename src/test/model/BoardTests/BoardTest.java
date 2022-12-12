package model.BoardTests;

import org.junit.jupiter.api.Test;

// Interface for testing suites of the Board Class
public interface BoardTest {

    @Test
    void testConstructor();

    @Test
    void testCreateRows();

    @Test
    void testCheckGuessCorrectGuess();

    @Test
    void testCheckGuessHalfWrongGuess();

    @Test
    void testCheckGuessCompletelyWrongGuess();

    @Test
    void testCheckGuessWordAlreadyGuessed();

    @Test
    void testCheckGuessReachMaximumNumberGuesses();

    @Test
    void testCheckGuessRightInMiddleOfGuesses();

    @Test
    void testCheckGuessRightOnLastGuess();

    @Test
    void testCheckGuessAfterReachedMaxGuesses();
}
