package model.WordleGameManagerTests;

import org.junit.jupiter.api.Test;

// interface for alll test suites of the WordleGameManager class
public interface WordleGameManagerTest {
    @Test
    void testConstructor();

    @Test
    void testUpdateBoardsCurrentGuessEmpty();

    @Test
    void testUpdateBoardsCurrentGuessNotEmpty();

    @Test
    void testUpdateBoardsMultipleUpdates();

    @Test
    void testResetGame();

    @Test
    void testChangeDifficultyToSameDifficulty();

    @Test
    void testChangeDifficultyToDifferentDifficulty();
}
