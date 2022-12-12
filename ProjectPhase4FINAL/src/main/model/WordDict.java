package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;


// WordDict is a word dictionary of all words from a given text file
// can give you all possible words in the dictionary or give you a random word from the dictionary

// file fiveLetterWords.txt in data package used to create dictionary was sourced from
// https://github.com/charlesreid1/five-letter-words/blob/master/sgb-words.txt
public class WordDict {
    private final ArrayList<String> wordList = new ArrayList<>();

    // EFFECTS: creates a list of all words from a text file
    public WordDict(String fileName) throws IOException {
        createWordList(fileName);
    }

    // REQUIRES: fileName must be a text file symbolic path (file in data package)
    //           for a text file with five-letter words on separate lines
    // MODIFIES: this
    // EFFECTS: adds all words from a text file into the word dictionary
    private void createWordList(String fileName) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String word;
            while ((word = bufferedReader.readLine()) != null) {
                wordList.add(word);
            }
        }
    }

    // EFFECTS: returns one random word from the word dictionary
    public String getRandomWord() {
        Random randomNum = new Random();
        int randomInt = randomNum.nextInt(wordList.size() - 1);
        String randomWord = wordList.get(randomInt).toUpperCase();

        return randomWord;
    }

    // EFFECTS: returns the entire word dictionary
    public ArrayList<String> getWordList() {
        return wordList;
    }

}
