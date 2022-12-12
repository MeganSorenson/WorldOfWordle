package persistence;

import model.Board;
import model.WordleGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// complete test suite for the JsonWriter class
// serializes an easy, medium, and hard game to test JSON files
public class JasonWriterTest {
    private WordleGameManager gameManager;

    @BeforeEach
    public void setUp() {
        gameManager = new WordleGameManager();
    }

    @Test
    public void testWriteToFileFileDoesNotExist() {
        try {
            JsonWriter testWriter = new JsonWriter("datax/savedWordleGame.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();
            fail("file does not exist: ERROR EXPECTED");
        } catch (FileNotFoundException e) {
            // test passes
        }
    }

    @Test
    public void testWriteToFileGameWithNoGuesses() {
        try {
            // write game manager state
            JsonWriter testWriter = new JsonWriter("data/writerTestFiles/testEasyGameNoGuesses.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();

            // read written data
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/writerTestFiles/testEasyGameNoGuesses.json");
            WordleGameManager readGameManager = reader.read();

            // check values of read data to make sure game was written properly
            ArrayList<Board> readGameBoards = readGameManager.getGameBoards();
            Board readBoard1 = readGameBoards.get(0);
            assertEquals(0, readGameManager.getNumberOfGuesses());
            assertEquals(1, readGameManager.getDifficulty());
            assertEquals(5, readGameManager.getMaxNumberOfGuesses());
            assertEquals(0, readGameManager.getScore());
            assertFalse(readGameManager.getGameOver());
            assertEquals("|/////|/////|/////|/////|/////",
                    readBoard1.getAllRowsAsString());
            assertEquals(0, readBoard1.getNumberOfGuessesMade());
            assertEquals(5, readBoard1.getMaxNumberOfGuesses());
            assertEquals(1, readBoard1.getBoardID());
            assertFalse(readBoard1.isGuessed());
            assertEquals(1, readGameBoards.size());
        } catch (FileNotFoundException e) {
            fail("file exists: NO ERROR EXPECTED WHILE WRITING");
        } catch (IOException e) {
            fail("file exists: NO ERROR EXPECTED WHILE READING");
        }
    }

    @Test
    public void testWriteToFileMiddleOfEasyGame() {
        // set conditions to write
        ArrayList<Board> gameBoards = gameManager.getGameBoards();
        for (Board board : gameBoards) {
            board.setWordToGuess("XXXXX");
        }
        gameManager.setCurrentGuess("ABCDE");
        gameManager.updateBoards();
        gameManager.isGameOver();
        gameManager.setCurrentGuess("FGHIJ");
        gameManager.updateBoards();
        gameManager.isGameOver();

        try {
            // write game manager state
            JsonWriter testWriter = new JsonWriter("data/writerTestFiles/testMiddleOfEasyGame.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();

            // read written data
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/writerTestFiles/testMiddleOfEasyGame.json");
            WordleGameManager readGameManager = reader.read();

            // check values of read data to make sure game was written properly
            ArrayList<Board> readGameBoards = readGameManager.getGameBoards();
            Board readBoard1 = readGameBoards.get(0);
            assertEquals(2, readGameManager.getNumberOfGuesses());
            assertEquals(1, readGameManager.getDifficulty());
            assertEquals(5, readGameManager.getMaxNumberOfGuesses());
            assertEquals(0, readGameManager.getScore());
            assertFalse(readGameManager.getGameOver());
            assertEquals("|/AR/BR/CR/DR/ER|/FR/GR/HR/IR/JR|/////|/////|/////",
                    readBoard1.getAllRowsAsString());
            assertEquals("XXXXX", readBoard1.getWordToGuess());
            assertEquals(2, readBoard1.getNumberOfGuessesMade());
            assertEquals(5, readBoard1.getMaxNumberOfGuesses());
            assertEquals(1, readBoard1.getBoardID());
            assertFalse(readBoard1.isGuessed());
            assertEquals(1, readGameBoards.size());
        } catch (FileNotFoundException e) {
            fail("file exists: NO ERROR EXPECTED WHILE WRITING");
        } catch (IOException e) {
            fail("file exists: NO ERROR EXPECTED WHILE READING");
        }
    }

    @Test
    public void testWriteToFileMiddleOfMediumGame() {
        // set conditions to write
        gameManager.changeDifficulty("MEDIUM");
        gameManager.resetGame();
        ArrayList<Board> gameBoards = gameManager.getGameBoards();
        gameBoards.get(0).setWordToGuess("MUNCH");
        gameBoards.get(1).setWordToGuess("XXXXX");
        gameManager.setCurrentGuess("HELLO");
        gameManager.updateBoards();
        gameManager.isGameOver();

        try {
            // write game manager state
            JsonWriter testWriter = new JsonWriter("data/writerTestFiles/testMiddleOfMediumGame.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();

            // read written data
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/writerTestFiles/testMiddleOfMediumGame.json");
            WordleGameManager readGameManager = reader.read();

            // check values of read data to make sure game was written properly
            ArrayList<Board> readGameBoards = readGameManager.getGameBoards();
            Board readBoard1 = readGameBoards.get(0);
            Board readBoard2 = readGameBoards.get(1);
            assertEquals(1, readGameManager.getNumberOfGuesses());
            assertEquals(2, readGameManager.getDifficulty());
            assertEquals(7, readGameManager.getMaxNumberOfGuesses());
            assertEquals(0, readGameManager.getScore());
            assertFalse(readGameManager.getGameOver());
            assertEquals("|/HY/ER/LR/LR/OR|/////|/////|/////|/////|/////|/////",
                    readBoard1.getAllRowsAsString());
            assertEquals("MUNCH", readBoard1.getWordToGuess());
            assertEquals(1, readBoard1.getNumberOfGuessesMade());
            assertEquals(7, readBoard1.getMaxNumberOfGuesses());
            assertEquals(1, readBoard1.getBoardID());
            assertFalse(readBoard1.isGuessed());
            assertEquals("|/HR/ER/LR/LR/OR|/////|/////|/////|/////|/////|/////",
                    readBoard2.getAllRowsAsString());
            assertEquals("XXXXX", readBoard2.getWordToGuess());
            assertEquals(1, readBoard2.getNumberOfGuessesMade());
            assertEquals(7, readBoard2.getMaxNumberOfGuesses());
            assertEquals(2, readBoard2.getBoardID());
            assertFalse(readBoard2.isGuessed());
            assertEquals(2, readGameBoards.size());
        } catch (FileNotFoundException e) {
            fail("file exists: NO ERROR EXPECTED WHILE WRITING");
        } catch (IOException e) {
            fail("file exists: NO ERROR EXPECTED WHILE READING");
        }
    }

    @Test
    public void testWriteToFileMiddleOfHardGame() {
        // set conditions to write
        gameManager.changeDifficulty("HARD");
        gameManager.resetGame();
        ArrayList<Board> gameBoards = gameManager.getGameBoards();
        gameBoards.get(0).setWordToGuess("MUNCH");
        gameBoards.get(1).setWordToGuess("ABCDE");
        gameBoards.get(2).setWordToGuess("XXXXX");
        gameManager.setCurrentGuess("JJJJJ");
        gameManager.updateBoards();
        gameManager.isGameOver();
        gameManager.setCurrentGuess("LLLLL");
        gameManager.updateBoards();
        gameManager.isGameOver();
        gameManager.setCurrentGuess("PPPPP");
        gameManager.updateBoards();
        gameManager.isGameOver();

        try {
            // write game manager state
            JsonWriter testWriter = new JsonWriter("data/writerTestFiles/testMiddleOfHardGame.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();

            // read written data
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/writerTestFiles/testMiddleOfHardGame.json");
            WordleGameManager readGameManager = reader.read();

            // check values of read data to make sure game was written properly
            ArrayList<Board> readGameBoards = readGameManager.getGameBoards();
            Board readBoard1 = readGameBoards.get(0);
            Board readBoard2 = readGameBoards.get(1);
            Board readBoard3 = readGameBoards.get(2);
            assertEquals(3, readGameManager.getNumberOfGuesses());
            assertEquals(3, readGameManager.getDifficulty());
            assertEquals(9, readGameManager.getMaxNumberOfGuesses());
            assertEquals(0, readGameManager.getScore());
            assertFalse(readGameManager.getGameOver());
            assertEquals("|/JR/JR/JR/JR/JR|/LR/LR/LR/LR/LR|/PR/PR/PR/PR/PR|/////|/////|/////|/////|/////|/////",
                    readBoard1.getAllRowsAsString());
            assertEquals("MUNCH", readBoard1.getWordToGuess());
            assertEquals(3, readBoard1.getNumberOfGuessesMade());
            assertEquals(9, readBoard1.getMaxNumberOfGuesses());
            assertEquals(1, readBoard1.getBoardID());
            assertFalse(readBoard1.isGuessed());
            assertEquals("|/JR/JR/JR/JR/JR|/LR/LR/LR/LR/LR|/PR/PR/PR/PR/PR|/////|/////|/////|/////|/////|/////",
                    readBoard2.getAllRowsAsString());
            assertEquals("ABCDE", readBoard2.getWordToGuess());
            assertEquals(3, readBoard2.getNumberOfGuessesMade());
            assertEquals(9, readBoard2.getMaxNumberOfGuesses());
            assertEquals(2, readBoard2.getBoardID());
            assertFalse(readBoard2.isGuessed());
            assertEquals("|/JR/JR/JR/JR/JR|/LR/LR/LR/LR/LR|/PR/PR/PR/PR/PR|/////|/////|/////|/////|/////|/////",
                    readBoard3.getAllRowsAsString());
            assertEquals("XXXXX", readBoard3.getWordToGuess());
            assertEquals(3, readBoard3.getNumberOfGuessesMade());
            assertEquals(9, readBoard3.getMaxNumberOfGuesses());
            assertEquals(3, readBoard3.getBoardID());
            assertFalse(readBoard3.isGuessed());
            assertEquals(3, readGameBoards.size());
        } catch (FileNotFoundException e) {
            fail("file exists: NO ERROR EXPECTED WHILE WRITING");
        } catch (IOException e) {
            fail("file exists: NO ERROR EXPECTED WHILE READING");
        }
    }

    @Test
    public void testWriteToFileGameOverGame() {
        // set conditions to write
        ArrayList<Board> gameBoards = gameManager.getGameBoards();
        for (Board board : gameBoards) {
            board.setWordToGuess("XXXXX");
        }
        gameManager.setCurrentGuess("AAAAA");
        gameManager.updateBoards();
        gameManager.isGameOver();
        gameManager.setCurrentGuess("XXXXX");
        gameManager.updateBoards();
        gameManager.isGameOver();

        try {
            // write game manager state
            JsonWriter testWriter = new JsonWriter("data/writerTestFiles/testGameOverEasyGame.json");
            testWriter.openWriter();
            testWriter.writeGameManager(gameManager);
            testWriter.closeWriter();

            // read written data
            GameManagerJsonReader reader = new
                    GameManagerJsonReader("data/writerTestFiles/testGameOverEasyGame.json");
            WordleGameManager readGameManager = reader.read();

            // check values of read data to make sure game was written properly
            ArrayList<Board> readGameBoards = readGameManager.getGameBoards();
            Board readBoard1 = readGameBoards.get(0);
            assertEquals(0, readGameManager.getNumberOfGuesses());
            assertEquals(1, readGameManager.getDifficulty());
            assertEquals(5, readGameManager.getMaxNumberOfGuesses());
            assertEquals(1800, readGameManager.getScore());
            assertFalse(readGameManager.getGameOver());
            assertEquals("|/////|/////|/////|/////|/////",
                    readBoard1.getAllRowsAsString());
            assertEquals(0, readBoard1.getNumberOfGuessesMade());
            assertEquals(5, readBoard1.getMaxNumberOfGuesses());
            assertEquals(1, readBoard1.getBoardID());
            assertFalse(readBoard1.isGuessed());
            assertEquals(1, readGameBoards.size());
        } catch (FileNotFoundException e) {
            fail("file exists: NO ERROR EXPECTED WHILE WRITING");
        } catch (IOException e) {
            fail("file exists: NO ERROR EXPECTED WHILE READING");
        }
    }
}
