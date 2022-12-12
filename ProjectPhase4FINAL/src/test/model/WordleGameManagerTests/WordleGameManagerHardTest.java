package model.WordleGameManagerTests;

import model.Board;
import model.WordleGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

// test suite for WordleGameManager class in hard mode
public class WordleGameManagerHardTest implements WordleGameManagerTest {
    private WordleGameManager testWordleGameManager;
    private final String hardGame = "HARD";
    private Board board1;
    private Board board2;
    private Board board3;

    @BeforeEach
    public void setUp() {
        testWordleGameManager = new WordleGameManager(hardGame);

        // set each board to have different words to be guessed
        board1 = testWordleGameManager.getGameBoards().get(0);
        board1.setWordToGuess("ABCDE");
        board2 = testWordleGameManager.getGameBoards().get(1);
        board2.setWordToGuess("FGHIJ");
        board3 = testWordleGameManager.getGameBoards().get(2);
        board3.setWordToGuess("KLMNO");
    }

    @Override
    @Test
    public void testConstructor() {
        assertEquals(3, testWordleGameManager.getDifficulty());
        assertEquals(9, testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(3, testWordleGameManager.getGameBoards().size());
        assertEquals(9, board1.getMaxNumberOfGuesses());
        assertEquals(9, board2.getMaxNumberOfGuesses());
        assertEquals(9, board3.getMaxNumberOfGuesses());
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
        testWordleGameManager.setCurrentGuess("KLMNO");
        testWordleGameManager.updateBoards();

        assertFalse(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertTrue(board3.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertFalse(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverTwoBoardsNotGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertFalse(board3.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertFalse(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverAllBoardsNotGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("XXXXX");
        testWordleGameManager.updateBoards();

        assertFalse(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertFalse(board3.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertFalse(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverAllBoardsGuessedNotReachedMaxGuesses() {
        testWordleGameManager.setCurrentGuess("ABCDE");
        testWordleGameManager.updateBoards();
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();
        testWordleGameManager.setCurrentGuess("KLMNO");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertTrue(board3.isGuessed());
        assertFalse(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals((testWordleGameManager.getDifficulty() * 1000) -
                (50 * testWordleGameManager.getNumberOfGuesses()), testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverOneBoardNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < (testWordleGameManager.getMaxNumberOfGuesses() - 1); i++) {
            testWordleGameManager.setCurrentGuess("ABCDE");
            testWordleGameManager.updateBoards();
        }
        testWordleGameManager.setCurrentGuess("KLMNO");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertTrue(board3.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverTwoBoardsNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < testWordleGameManager.getMaxNumberOfGuesses(); i++) {
            testWordleGameManager.setCurrentGuess("FGHIJ");
            testWordleGameManager.updateBoards();
        }

        assertFalse(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertFalse(board3.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverAllBoardsNotGuessedReachedMaxGuesses() {
        for (int i = 0; i < testWordleGameManager.getMaxNumberOfGuesses(); i++) {
            testWordleGameManager.setCurrentGuess("XXXXX");
            testWordleGameManager.updateBoards();
        }

        assertFalse(board1.isGuessed());
        assertFalse(board2.isGuessed());
        assertFalse(board3.isGuessed());
        assertTrue(testWordleGameManager.getNumberOfGuesses() >= testWordleGameManager.getMaxNumberOfGuesses());
        assertEquals(0, testWordleGameManager.getScore());

        assertTrue(testWordleGameManager.isGameOver());
        assertEquals(0, testWordleGameManager.getScore());
    }

    @Test
    public void testIsGameOverAllBoardsGuessedReachedMaxGuesses() {
        for (int i = 0; (i < testWordleGameManager.getMaxNumberOfGuesses() - 2); i++) {
            testWordleGameManager.setCurrentGuess("ABCDE");
            testWordleGameManager.updateBoards();
        }
        testWordleGameManager.setCurrentGuess("FGHIJ");
        testWordleGameManager.updateBoards();
        testWordleGameManager.setCurrentGuess("KLMNO");
        testWordleGameManager.updateBoards();

        assertTrue(board1.isGuessed());
        assertTrue(board2.isGuessed());
        assertTrue(board3.isGuessed());
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
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", board1.getAllRowsAsString());
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", board2.getAllRowsAsString());
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", board3.getAllRowsAsString());

        testWordleGameManager.resetGame();
        assertEquals("", testWordleGameManager.getCurrentGuess());
        assertEquals(0, testWordleGameManager.getNumberOfGuesses());
        assertEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", testWordleGameManager.getGameBoards().get(0).getAllRowsAsString());
        assertEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", testWordleGameManager.getGameBoards().get(1).getAllRowsAsString());
        assertNotEquals("|/////|/////|/////|/////|/////|/////|/////|/////|/////", board3.getAllRowsAsString());
        assertEquals(9, testWordleGameManager.getMaxNumberOfGuesses());
        assertFalse(testWordleGameManager.getGameOver());
    }

    @Override
    @Test
    public void testChangeDifficultyToSameDifficulty() {
        assertEquals(3, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(hardGame);
        assertEquals(3, testWordleGameManager.getDifficulty());
    }

    @Override
    public void testChangeDifficultyToDifferentDifficulty() {
        assertEquals(3, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("EASY");
        assertEquals(1, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty(hardGame);
        assertEquals(3, testWordleGameManager.getDifficulty());
        testWordleGameManager.changeDifficulty("MEDIUM");
        assertEquals(2, testWordleGameManager.getDifficulty());
    }

}
