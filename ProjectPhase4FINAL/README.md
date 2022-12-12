# **World of Wordle**

## **It's like Wordle, but on steroids.**

Like the original Wordle game, World of Wordle has the player trying to uncover a mystery 5-letter word 
in a limited amount of word guesses with feedback about the letters that were correct.

What makes World of Wordle different is the three difficulty levels that you can choose to play with:

- EASY gives the player 5 attempts to guess a single word
- MEDIUM gives the player 7 attempts to simultaneously guess 2 words 
- HARD gives the player 9 attempts to simultaneously guess 3 words


This will bring a new type of challenge for the *Wordle Champions* out there. On top of that, World of Wordle keeps 
track of the player's score and word guess streak so that the player can try to maximize them as they play.

## **Who can play?**

Anyone can play World of Wordle! The option to play an EASY game gives the somewhat typical functionality of the original Wordle, 
which is meant for the everyday person who loves word games. In addition to this, World of Wordle provides
the MEDIUM and HARD game options to attract the more skilled and practiced Wordle players out there.

## Why did I want to make World of Wordle?

As a commuting student at the University of British Columbia, I spend more time than I would really like to on the bus 
to school every morning. Not ready to do anything productive during that time, I often open up one of many of the word 
games on my smart phone. 

Wordle was a popular game over the past year or so, and I completed my fair share of daily word challenges on my daily
commutes. In transit, I would always hear people chat about who was the *Wordle CHAMPION of the day* in their 
respective group and it got me thinking.

I decided that I wanted to create a version of Wordle that would truly separate the Wordle *Players* from the 
Wordle *Champions*, and so came the idea for World of Wordle.

## User Stories:

- As a user, I want to be able to add multiple words to the guess board
- As a user, I want to be able to view letter feedback on my word guess
- As a user, I want to be able to select a game difficulty
- As a user, I want to be able to view my game score as I play
- As a user, I want to be able to quit the application at any time, and have the option to save my entire game for next time
- As a user, I want to be able to have the option to reload the last game I saved when I open the application

## **Instructions for Grader**

- *You can generate the first required event related to adding Xs to a Y by...*
1. Run the game through the main function in Main
2. At the welcome pop-up window, press the 'Play a Whole New Game' button 
3. Place and click your cursor into the text input box to the right of the 'Enter a 5-letter Word Guess: ' text at the bottom of the window 
4. Type a 5-letter word (or any combination of 5 letters) into the text input box 
5. Press the 'Make Word Guess' button to see your word guess be added and displayed onto the board(s) (implements the adding multiple Xs component of adding multiple Xs to Y)

- *You can generate the second required event related to adding Xs to a Y by...*
1. Run the game through the main function in Main
2. At the welcome pop-up window, press the 'Play a Whole New Game' button 
3. Press one of 'New Easy Game', 'New Medium Game', or 'New Hard Game' buttons to change and display the boards that you are adding word guesses to (changes the Y component of adding multiple Xs to Y and removes all Xs from Y)

- *You can locate my visual component by...*
1. Run the game through the main function in Main
2. In the welcome pop-up window, a game character image was added in the top left corner

- *You can save the state of my application by...*
1. Run the game through the main function in Main
2. At the welcome pop-up window, press the 'Play a Whole New Game' or 'Continue Last Saved Game' button 
3. Play games of World of Wordle as you please
4. Once ready to save, exit the window by clicking the window's 'x'
5. In the goodbye pop-up window, press the 'Save Game Progress' button to save the state of and quit the application

- *You can reload the state of my application by...*
1. Run the game through the main function in Main
2. At the welcome pop-up window, press the 'Continue Last Saved Game' button to load the last saved state of the application and continue playing from that state

## Phase 4: Task 2
*Representative sample of event logging:*

Application started.

User made guess on board(s): aeiou

User made guess on board(s): mitns

User made guess on board(s): tirny

User made guess on board(s): tinny

Current game over. Current board(s) cleared.

User guessed the secret word(s).

User has started a new MEDIUM game. All previous boards have been cleared.

User made guess on board(s): munch

User made guess on board(s): north

User made guess on board(s): narts

Current game state saved.

Application closing.

## Phase 4: Task 3

*Potential refactoring for future consideration:*
- Create a getGameGUI() method in AllGameWindowComponents so that MainGameWindow would not need a direct association with the WordleGameGUI (could access a WordleGameGUI through its association with AllGameWindowComponents)
- Create an abstract class for my Buttons (EasyGameButton, MediumGameButton, HardGameButton, SendWordGuessButton, ResetWordGuessButton) so that there is less duplicate code
- Create an abstract class for the ' ui bars' of my game window (MenuBarUI, WordGuessBarUI) to reduce duplicate code
- Create an abstract class for my pop-up windows (ClosingPopUp, StartPopUp, LoserPopUp, WinnerPopUp) to reduce duplicate code
- Remove the Writable interface since only one class (WordleGameManager) ends up implementing it
- Remove the JsonReader abstract class since only one class (GameManagerJsonReader) ends up extending it
- Create more/more meaningful 'ui aesthetics' methods within necessary classes to remove duplicate code (ex: SendWordGuessButton and ResetWordGuessButton aesthetics are almost identical, but are both set separately... aesthetics could have been incorporated into a single method with the buttons as parameters)
- Structure the JsonWriter class differently so that the parsing in GameManagerJsonReader requires less steps
- Create a direct association between WordleGameManager and WordDict so that a new instance of WordDict was not necessary everytime a new game was played (ex: perhaps WordleGameManager could have a WordDict field that gives a random word to Board everytime a new instance of Board is constructed)
