package model.EventRelatedTests;

import model.Board;
import model.Event;
import model.EventLog;
import model.WordleGameManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

// Test suite for the relevant actions performed by EventLog in the World of Wordle game application
// i.e. only tests the methods in the model package that log events to EventLog
public class RelevantEventLogTest {
    private WordleGameManager gameManager;
    private EventLog el;

    @BeforeEach
    public void loadEvents() {
        gameManager = new WordleGameManager();
        el = EventLog.getInstance();
        el.clear();
    }

    @Test
    public void testApplicationStartedEventLog() {
        gameManager = new WordleGameManager();
        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Application started.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testLastGameStateLoadedEventLog() {
        ArrayList<Board> boards = new ArrayList<>();
        boards.add(new Board(5, 1));

        gameManager = new WordleGameManager(1, boards, 0, 5, 100);
        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Last game state loaded.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testExpectedUserMadeGuessEventLogWhenCurrentGuessValid() {
        gameManager.setCurrentGuess("hello");
        gameManager.updateBoards();

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("User made guess on board(s): HELLO", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testNoUserMadeGuessEventLogWhenCurrentGuessInvalid() {
        gameManager.setCurrentGuess("");
        gameManager.updateBoards();

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testGameOverEventLogNoUserGuessedWordEventLog() {
        for (int i = 0; i < gameManager.getMaxNumberOfGuesses(); i++) {
            gameManager.setCurrentGuess("xxxxx");
            gameManager.updateBoards();
            gameManager.isGameOver();
        }

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        for (int i = 0; i < gameManager.getMaxNumberOfGuesses(); i++) {
            assertTrue(itr.hasNext());
            assertEquals("User made guess on board(s): XXXXX", itr.next().getDescription());
        }
        assertTrue(itr.hasNext());
        assertEquals("Current game over. Current board(s) cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testGameOverEventLogAndUserGuessedWordEventLog() {
        for (int i = 0; i < gameManager.getMaxNumberOfGuesses() - 1; i++) {
            gameManager.setCurrentGuess("xxxxx");
            gameManager.updateBoards();
            gameManager.isGameOver();
        }

        String word = gameManager.getGameBoards().get(0).getWordToGuess();
        gameManager.setCurrentGuess(word);
        gameManager.updateBoards();
        gameManager.isGameOver();

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        for (int i = 0; i < gameManager.getMaxNumberOfGuesses() - 1; i++) {
            assertTrue(itr.hasNext());
            assertEquals("User made guess on board(s): XXXXX", itr.next().getDescription());
        }
        assertTrue(itr.hasNext());
        assertEquals("User made guess on board(s): " + word.toUpperCase(), itr.next().getDescription());

        assertTrue(itr.hasNext());
        assertEquals("Current game over. Current board(s) cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("User guessed the secret word(s).", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testStartedNewGameDifferentDifficultyEventLog() {
        gameManager.changeDifficulty("MEDIUM");

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("User has started a new MEDIUM game. All previous boards have been cleared.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testCurrentGameSavedEventLog() {
        gameManager.toJson();

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Current game state saved.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }

    @Test
    public void testApplicationClosingEventLogAndEventLogPrinted() {
        gameManager.printLog();

        Iterator<Event> itr = el.iterator();

        assertTrue(itr.hasNext());
        assertEquals("Event log cleared.", itr.next().getDescription());
        assertTrue(itr.hasNext());
        assertEquals("Application closing.", itr.next().getDescription());
        assertFalse(itr.hasNext());
    }
}
