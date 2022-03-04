package com.GuruGames.games.Wordle;

import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Wordle implements Game {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final String WORD = String.valueOf(Words.values()[new Random().nextInt(Words.values().length)]);
    private final char[] wordle = WORD.toLowerCase().toCharArray();
    private final ArrayList<String> letters = new ArrayList<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"));
    boolean[] checksPast = {false, false, false, false, false};
    WordleGameData gameData = new WordleGameData();
    private char[] showCorrect = {'-', '-', '-', '-', '-'};
    private boolean gameOver = false;
    private boolean winner;
    private String globalWordGuess;
    private int tries = 0;
    private boolean charGood = true;
    private int cheatCount = 0;
    private char[] charGuess;
    private Boolean checkResults;
    private boolean started = false;

    public Wordle() throws FileNotFoundException {
        System.out.println("Wordle: Enter start to begin, help to see rules, or quit to return to the main menu.");
    }


    public void playGame() {
        System.out.println("Your unused letters are:");
        for (var l : letters) {
            System.out.print(l + " ");
        }
        System.out.println();
        System.out.println("Type in your word.");

        if (WORD.equalsIgnoreCase(globalWordGuess)) {
            winner = true;
            checkResults = true;
        } else if (tries >= 5) {
            winner = false;
            checkResults = false;
        } else {
            checkResults = null;
        }

    }

    protected void runGame(String wordGuess) {
        charGuess = wordGuess.toLowerCase().toCharArray();
        globalWordGuess = wordGuess;

        System.out.println();
        if (tries >= 5) {
            gameOver = true;
        }
        if (!gameOver) {
            try {
                if (realWord(wordGuess)) {
                    if (results()) {
                        gameOver = true;


                        System.out.println();
                    } else {
                        checkGuess();
                        printGuess();


                        System.out.println();
                        tries++;
                        System.out.println("You have " + (6 - tries) + " guesses remaining.");
                    }
                } else {
                    System.out.println("CHEATER!!!");
                    cheatCount++;
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }


    }

    /**
     * Checks to see whether your guess is correct and adds "+","=","-" to an array to determine which color a letter should be printed in.
     */
    protected void checkGuess() {
        showCorrect = new char[]{'-', '-', '-', '-', '-'};
        checksPast = new boolean[]{false, false, false, false, false};
        for (int i = 0; i < wordle.length; i++) {
            if (wordle[i] == charGuess[i]) {
                showCorrect[i] = '=';
                checksPast[i] = true;
            }
        }
        for (int i = 0; i < wordle.length; i++) {
            if (showCorrect[i] != '=') {
                for (int j = 0; j < charGuess.length; j++) {
                    if (wordle[j] == (charGuess[i])&&showCorrect[j]!='=') {
                        if (i != j) {
                            for (int k = 0; k <= i; k++) {
                                if (checksPast[k]) {
                                    if (charGuess[i] == charGuess[k]) {
                                        if (showCorrect[k] != '=' && i != k ) {
                                            showCorrect[k] = '-';
                                            charGood = true;
                                        }
                                    }
                                } else if (charGuess[i] == charGuess[k]) {
                                    charGood = true;
                                } else {
                                    charGood = false;
                                }
                            }
                            if (charGood) {
                                showCorrect[i] = '+';
                                checksPast[i] = true;
                                charGood = false;

                            }
                        }

                    }
                }

            }
        }
    }

    protected void printGuess() {
        for (var c : charGuess) {
            letters.remove(String.valueOf(c));
        }
        for (int i = 0; i < charGuess.length; i++) {
            if (showCorrect[i] == ('=')) {
                System.out.print(ANSI_GREEN + charGuess[i] + ANSI_RESET);
            } else if (showCorrect[i] == ('+')) {
                System.out.print(ANSI_YELLOW + charGuess[i] + ANSI_RESET);
            } else {
                System.out.print(charGuess[i]);
            }
            showCorrect[i] = '-';
        }
        System.out.println();

    }

    /**
     * @param wordGuess-Accepts an inputted guess parameter.
     * @return true-if the word exists in guesses.txt
     * @throws IOException if guesses.txt cannot be read
     */
    protected boolean realWord(String wordGuess) throws IOException {
        String s;
        String[] words;
        BufferedReader guessCheck = new BufferedReader(new FileReader("src/com/GuruGames/games/Wordle/guesses.txt"));
        while ((s = guessCheck.readLine()) != null) {
            words = s.split(" ");
            for (String word : words) {
                if (wordGuess.equalsIgnoreCase(word)) {
                    guessCheck.close();
                    return true;
                }
            }
        }
        guessCheck.close();
        return false;
    }

    public boolean results() {
        if (WORD.equalsIgnoreCase(globalWordGuess)) {
            winner = true;
            return true;
        }
        return false;
    }

    @Override
    public Boolean checkResults() {
        if (winner) {
            System.out.println(WORD);
            System.out.println("You have won!!!");
            gameData.wins++;
            if (cheatCount == 1) {
                System.out.println("But you cheated 1 time!");
            } else if (cheatCount > 1) {
                gameData.cheatCount += cheatCount;
                System.out.println("But you cheated " + cheatCount + " times!");
            }
            return true;
        } else if (gameOver) {
            System.out.println("The word was " + WORD);
            System.out.println("LOSER!!!!!");
            gameData.losses++;
            return false;
        }
        return checkResults;
    }

    @Override
    public GameData getGameData() {
        return gameData;
    }

    @Override
    public void parseCommand(String command, String... params) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (command.equalsIgnoreCase("help")) {
            System.out.println(help());
        } else if (command.split("").length == 5 && started) {
            runGame(command);
        } else if (command.equalsIgnoreCase("start") && !started) {
            started = true;
            System.out.println("Enter a 5 letter guess. If the wordle contains the letter it will be printed in yellow. If the letter is in the correct spot it will be printed in green.");
            throw new IllegalArgumentException("");
        } else {
            throw new IllegalArgumentException("Please enter a 5 letter guess.");
        }
    }


    @Override
    public String help() {
        return ("Enter a 5 letter guess. If the wordle contains the letter it will be printed in yellow. If the letter is in the correct spot it will be printed in green.\n Please enter start/help/quit.");
    }
}