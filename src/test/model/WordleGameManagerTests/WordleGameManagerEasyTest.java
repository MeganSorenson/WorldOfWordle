package model.WordleGameManagerTests;

import model.Board;
import model.WordleGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test suite for all methods in the WordleGameManager class for an Easy Game
public class WordleGameManagerEasyTest implements WordleGameManagerTest {
    private WordleGameManager testWordleGameManager;
    private final String easyGame = "EASY";
    private Board board1;

    @BeforeEach
    public void setUp() {
        testWordleGameManager = new WordleGameManager();

        // set each board to have different words to be guessed
        board1 = testWordleGameManager.getGameBoards().get(0);
        board1.setWordToGuess("ABCDE");
    }

    @Override
    @Test
    public void testConstructor() {
        assertEquals(1, testWordleGameManager.getDifficulty());
        assertEquals(5, testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(1, testWordleGameManager.getGameBoards().size());
        assertEquals(5, board1.getMaxNumberOfGuesses());
    }

    @Test
    public void testConstructorTwo() {
        WordleGameManager testWordleGameManager2 = new WordleGameManager(easyGame);
        assertEquals(1, testWordleGameManager2.getDifficulty());
        assertEquals(5, testWordleGameManager2.getMaxNumberOfGuesses());
        assertEquals(1, testWordleGameManager2.getGameBoards().size());
        assertEquals(5, testWordleGameManager2.getGameBoards().get(0).getMaxNumberOfGuesses());
    }

    @Test
    public void testConstructorThree() {
        WordleGameManager testWordleGameManager3 = new WordleGameManager(1, testWordleGameManager.getGameBoards(),
                0, 5, 1000);
        assertEquals(1, testWordleGameManager3.getDifficulty());
        assertEquals(5, testWordleGameManager3.getMaxNumberOfGuesses());
        assertEquals(1, testWordleGameManager3.getGameBoards().size());
        assertEquals(5, testWordleGameManager3.getGameBoards().get(0).getMaxNumberOfGuesses());
        assertEquals(1000, testWordleGameManager3.getScore());
    }

    @Test
    public void testUpdateBoardsCurrentGuessEmpty() {
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());

        testWordleGameManager.updateBoards();

        assertEquals("", testWordleGameManager.getCurrentGuess());
    }

    @Test
    public void testUpdateBoardsCurrentGuessNotEmpty() {
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        testWordleGameManager.setCurrentGuess("XXXXX");
        assertEquals(1, testWordleGameManager.getNumberOfGuesses());

        testWordleGameManager.updateBoards();

        assertEquals("", testWordleGameManager.getCurrentGuess());
    }

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
    public void testIsGameOverBoardNotGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("XXXXX");
        testWordleGameManager.updateBoards();

        assertFalse(board1.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());

        assertEquals(0, testWordleGameManager.getScore());
        assertFalse(testWordleGameManager.isGameOver());
    }

    @Test
    public void testIsGameOverBoardGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals((testWordleGameManager.getDifficulty() * 1000) -
                (50 * testWordleGameManager.getNumberOfGuesses()), testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBoardNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < testWordleGameManager.getMaxNumberOfGuesses(); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }
        assertFalse(board1.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverBoardGuessedReachedMaxGuesses() {
        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 1); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals((testWordleGameManager.getDifficulty() * 1000) -
                (50 * testWordleGameManager.getNumberOfGuesses()), testWordleGameManager.getScore());

    }

    @Override
    @Test
    public void testResetGame() {
        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 1); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
            testWordleGameManager.isGameOver();
        }
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();
        testWordleGameManager.isGameOver();
        assertEquals("", testWordleGameManager.getCurrentGuess());
        assertEquals(5, testWordleGameManager.getNumberOfGuesses());
        assertNotEquals("|/////|/////|/////|/////|/////", board1.getAllRowsAsString());
        assertEquals(5, testWordleGameManager.getMaxNumberOfGuesses());
        assertTrue(testWordleGameManager.getGameOver());

        testWordleGameManager.resetGame();
        assertEquals("", testWordleGameManager.getCurrentGuess());
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        assertEquals("|/////|/////|/////|/////|/////",
                testWordleGameManager.getGameBoards().get(0).getAllRowsAsString());
        assertEquals(5, testWordleGameManager.getMaxNumberOfGuesses());
        assertFalse(testWordleGameManager.getGameOver());

    }

    @Override
    @Test
    public void testChangeDifficultyToSameDifficulty() {
        assertEquals(1, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(easyGame);
        assertEquals(1, testWordleGameManager.getDifficulty());
    }

    @Override
    @Test
    public void testChangeDifficultyToDifferentDifficulty() {
        assertEquals(1, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("MEDIUM");
        assertEquals(2, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(easyGame);
        assertEquals(1, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("HARD");
        assertEquals(3, testWordleGameManager.getDifficulty());
    }

    @Test
    public void testSetGameOverToTrue() {
        testWordleGameManager.setGameOver(true);
        assertTrue(testWordleGameManager.getGameOver());
    }

    @Test
    public void testSetGameOverToFalse() {
        testWordleGameManager.setGameOver(false);
        assertFalse(testWordleGameManager.getGameOver());
    }


}
