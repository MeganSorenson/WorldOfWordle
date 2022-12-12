package model.WordleGameManagerTests;

import model.Board;
import model.WordleGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test suite for all methods in the WordleGameManager class for a Medium Game
public class WordleGameManagerMediumTest implements WordleGameManagerTest {
    private WordleGameManager testWordleGameManager;
    private final String mediumGame = "MEDIUM";
    private Board board1;
    private Board board2;

    @BeforeEach
    public void setUp() {
        testWordleGameManager = new WordleGameManager(mediumGame);

        // set each board to have different words to be guessed
        board1 = testWordleGameManager.getGameBoards().get(0);
        board1.setWordToGuess("ABCDE");
        board2 = testWordleGameManager.getGameBoards().get(1);
        board2.setWordToGuess("FGHIJ");
    }

    @Override
    @Test
    public void testConstructor() {
        assertEquals(2, testWordleGameManager.getDifficulty());
        assertEquals(7, testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(2, testWordleGameManager.getGameBoards().size());
        assertEquals(7, board1.getMaxNumberOfGuesses());
        assertEquals(7, board2.getMaxNumberOfGuesses());
    }

    @Override
    @Test
    public void testUpdateBoardsCurrentGuessEmpty() {
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());

        testWordleGameManager.updateBoards();

        assertEquals("", testWordleGameManager.getCurrentGuess());
    }

    @Override
    @Test
    public void testUpdateBoardsCurrentGuessNotEmpty() {
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        testWordleGameManager.setCurrentGuess("XXXXX");
        assertEquals(1, testWordleGameManager.getNumberOfGuesses());

        testWordleGameManager.updateBoards();

        assertEquals("", testWordleGameManager.getCurrentGuess());
    }

    @Override
    @Test
    public void testUpdateBoardsMultipleUpdates() {
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        testWordleGameManager.setCurrentGuess("XXXXX");
        assertEquals(1, testWordleGameManager.getNumberOfGuesses());

        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 1); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }

        assertEquals("", testWordleGameManager.getCurrentGuess());
    }

    @Test
    public void testIsGameOverOneBoardNotGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();

        assertFalse(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertFalse(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBothBoardsNotGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("XXXXX");
        testWordleGameManager.updateBoards();

        assertFalse(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertFalse(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBothBoardsGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals((testWordleGameManager.getDifficulty() * 1000) -
                (50 * testWordleGameManager.getNumberOfGuesses()), testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverOneBoardNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < testWordleGameManager.getMaxNumberOfGuesses(); i++) {
            testWordleGameManager.setCurrentGuess("ABCDE");
            testWordleGameManager.updateBoards();
        }

        assertTrue(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBothBoardsNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < testWordleGameManager.getMaxNumberOfGuesses(); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }

        assertFalse(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBothBoardsGuessedReachedMaxGuesses() {
        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 1); i++) {
            testWordleGameManager.setCurrentGuess("ABCDE");
            testWordleGameManager.updateBoards();
        }
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals((testWordleGameManager.getDifficulty() * 1000) -
                (50 * testWordleGameManager.getNumberOfGuesses()), testWordleGameManager.getScore());
    }

    @Override
    @Test
    public void testResetGame() {
        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 2); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////", board1.getAllRowsAsString());
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////", board2.getAllRowsAsString());

        testWordleGameManager.resetGame();
        assertEquals("", testWordleGameManager.getCurrentGuess());
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        assertEquals("|/////|/////|/////|/////|/////|/////|/////", testWordleGameManager.getGameBoards().get(0).getAllRowsAsString());
        assertEquals("|/////|/////|/////|/////|/////|/////|/////", testWordleGameManager.getGameBoards().get(1).getAllRowsAsString());
        assertEquals(7, testWordleGameManager.getMaxNumberOfGuesses());
        assertFalse(testWordleGameManager.getGameOver());
    }

    @Override
    @Test
    public void testChangeDifficultyToSameDifficulty() {
        assertEquals(2, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(mediumGame);
        assertEquals(2, testWordleGameManager.getDifficulty());
    }

    @Override
    @Test
    public void testChangeDifficultyToDifferentDifficulty() {
        assertEquals(2, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("EASY");
        assertEquals(1, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(mediumGame);
        assertEquals(2, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("HARD");
        assertEquals(3, testWordleGameManager.getDifficulty());
    }

}
