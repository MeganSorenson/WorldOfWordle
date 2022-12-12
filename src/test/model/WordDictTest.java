package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// test suite for the WordDict class
public class WordDictTest {
    private WordDict testWordDict;

    @BeforeEach
    public void setUp() throws IOException {
        String wordFileName = "data/fiveLetterWords.txt";
        testWordDict = new WordDict(wordFileName);
    }

    @Test
    public void testConstructor() {
        assertTrue(testWordDict.getWordList().size() > 0);
    }

    @Test
    public void testGetRandomWord() {
        assertEquals(5, testWordDict.getRandomWord().length());
    }

    @Test
    public void testGetRandomWordMultipleTimes() {
        for (int i = 0; i < 20; i++) {
            assertEquals(5, testWordDict.getRandomWord().length());
        }
    }

}
