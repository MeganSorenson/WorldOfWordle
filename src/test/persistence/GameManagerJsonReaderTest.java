package persistence;

import model.Board;
import model.WordleGameManager;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// test suite for GameManagerJsonReader class
// de-serializes an easy, medium, and hard game from test JSON files
public class GameManagerJsonReaderTest {

    @Test
    public void testReadFileThatDoesNotExist() {
        try {
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("dataz/readerTestFiles/testEasyGameManager.json");
            reader.read();
            fail("file does not exist: EXCEPTION EXPECTED");
        } catch(IOException e) {
            // test passes
        }
    }

    @Test
    public void testEasyGameManager() {
        try {
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/readerTestFiles/testEasyGameManager.json");
            WordleGameManager gameManager = reader.read();
            Board board1 = gameManager.getGameBoards().get(0);

            assertEquals(1, gameManager.getDifficulty());
            assertEquals(3, gameManager.getNumberOfGuesses());
            assertEquals(5, gameManager.getMaxNumberOfGuesses());
            assertEquals(1000, gameManager.getScore());
            assertEquals(1, gameManager.getGameBoards().size());

            assertEquals("|/XR/XR/CG/EY/DY|/////|/////|/////|/////", board1.getAllRowsAsString());
            assertEquals("ABCDE", board1.getWordToGuess());
            assertEquals(1, board1.getNumberOfGuessesMade());
            assertEquals(5, board1.getMaxNumberOfGuesses());
            assertEquals(1, board1.getBoardID());
            assertFalse(board1.isGuessed());
        } catch (IOException e) {
            fail("NO ERROR EXPECTED");
        }
    }

    @Test
    public void testMediumGameManager() {
        try {
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/readerTestFiles/testMediumGameManager.json");
            WordleGameManager gameManager = reader.read();
            Board board1 = gameManager.getGameBoards().get(0);
            Board board2 = gameManager.getGameBoards().get(1);

            assertEquals(2, gameManager.getDifficulty());
            assertEquals(6, gameManager.getNumberOfGuesses());
            assertEquals(7, gameManager.getMaxNumberOfGuesses());
            assertEquals(0, gameManager.getScore());
            assertEquals(2, gameManager.getGameBoards().size());

            assertEquals("|/AG/BG/CG/DG/EG|/////|/////|/////|/////|/////|/////", board1.getAllRowsAsString());
            assertEquals("ABCDE", board1.getWordToGuess());
            assertEquals(1, board1.getNumberOfGuessesMade());
            assertEquals(7, board1.getMaxNumberOfGuesses());
            assertEquals(1, board1.getBoardID());
            assertTrue(board1.isGuessed());

            assertEquals("|/AR/BR/CR/DR/ER|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/XR/XR/XR/XR/XR|/FG/XR/XR/XR/XR|/////", board2.getAllRowsAsString());
            assertEquals("FGHIJ", board2.getWordToGuess());
            assertEquals(6, board2.getNumberOfGuessesMade());
            assertEquals(7, board2.getMaxNumberOfGuesses());
            assertEquals(2, board2.getBoardID());
            assertFalse(board2.isGuessed());
        } catch (IOException e) {
            fail("NO ERROR EXPECTED");
        }

    }

    @Test
    public void testHardGameManager() {
        try {
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/readerTestFiles/testHardGameManager.json");
            WordleGameManager gameManager = reader.read();
            Board board1 = gameManager.getGameBoards().get(0);
            Board board2 = gameManager.getGameBoards().get(1);
            Board board3 = gameManager.getGameBoards().get(2);

            assertEquals(3, gameManager.getDifficulty());
            assertEquals(5, gameManager.getNumberOfGuesses());
            assertEquals(9, gameManager.getMaxNumberOfGuesses());
            assertEquals(2500, gameManager.getScore());
            assertEquals(3, gameManager.getGameBoards().size());

            assertEquals("|/HR/OG/MR/EG/SG|/PG/OG/LG/EG/SG|/////|/////|/////|/////|/////|/////|/////", board1.getAllRowsAsString());
            assertEquals("POLES", board1.getWordToGuess());
            assertEquals(2, board1.getNumberOfGuessesMade());
            assertEquals(9, board1.getMaxNumberOfGuesses());
            assertEquals(1, board1.getBoardID());
            assertTrue(board1.isGuessed());

            assertEquals("|/HY/OR/MY/ER/SR|/PR/OR/LR/ER/SR|/PR/UG/NG/CG/HG|/MG/UG/NG/CG/HG|/////|/////|/////|/////|/////", board2.getAllRowsAsString());
            assertEquals("MUNCH", board2.getWordToGuess());
            assertEquals(4, board2.getNumberOfGuessesMade());
            assertEquals(9, board2.getMaxNumberOfGuesses());
            assertEquals(2, board2.getBoardID());
            assertTrue(board2.isGuessed());

            assertEquals("|/HG/OR/MR/ER/SR|/PR/OR/LR/ER/SR|/PR/UG/NG/CG/HG|/MR/UG/NG/CG/HG|/LR/UG/NG/CG/HG|/////|/////|/////|/////", board3.getAllRowsAsString());
            assertEquals("HUNCH", board3.getWordToGuess());
            assertEquals(5, board3.getNumberOfGuessesMade());
            assertEquals(9, board3.getMaxNumberOfGuesses());
            assertEquals(3, board3.getBoardID());
            assertFalse(board3.isGuessed());
        } catch (IOException e) {
            fail("NO ERROR EXPECTED");
        }

    }
}
