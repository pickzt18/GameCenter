package com.GuruGames.games.Wordle;

import com.GuruGames.GameCenter.GameCenter;
import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.Scanner;


public class Wordle implements Game {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private final String WORD = String.valueOf(Words.values()[new Random().nextInt(Words.values().length)]);
    boolean[] checksPast = {false, false, false, false, false};
    private char[] showCorrect = {'-', '-', '-', '-', '-'};
    private boolean gameOver = false;
    private boolean winner = false;
    private BufferedReader guessCheck = new BufferedReader(new FileReader("C:\\StudentWork\\IntmJ\\GameCenter\\src\\com\\GuruGames\\games\\Wordle\\guesses.txt"));
    private String wordGuess;
    private char[] wordle = WORD.toLowerCase().toCharArray();
    private int tries = 0;
    private boolean charGood = true;
    private int cheatCount=0;


    public Wordle() throws FileNotFoundException {
        System.out.println("Wordle: Enter start to begin, help to see rules, or quit to return to the main menu.");
    }


    public void playGame() {

        Scanner in = GameCenter.in;
        //Scanner in = new Scanner(System.in);

        while (!gameOver && tries < 6) {
            showCorrect = new char[]{'-', '-', '-', '-', '-'};
            checksPast = new boolean[]{false, false, false, false, false};
            boolean charGood = false;
            System.out.println("Enter your 5 letter guess.");
            wordGuess = in.nextLine();
            try {
                if (realWord(wordGuess)) {
                    if (checkResults()) {
                        gameOver = true;
                        endGame();
                    } else {
                        runGame();
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
        if (tries >= 6) {
            endGame();
        }
    }

    protected void runGame() {
        char[] charGuess = wordGuess.toLowerCase().toCharArray();
        for (int i = 0; i < wordle.length; i++) {
            for (int j = 0; j < charGuess.length; j++) {
                if (wordle[j] == (charGuess[i])) {
                    if (i != j) {
                        for (int k = 0; k <= i; k++) {
                            if (checksPast[k]) {
                                if (charGuess[i] == charGuess[k]) {
                                    if (showCorrect[k] != '=') {
                                        showCorrect[k] = '-';
                                        charGood = true;
                                    }
                                }
                            }
                        }
                        if (charGood) {
                            showCorrect[i] = '+';
                            checksPast[i] = true;
                            break;
                        }
                        break;

                    } else {
                        if (i > 0) {
                            for (int k = 0; k <= i; k++) {
                                if (checksPast[k]) {
                                    if (charGuess[i] == charGuess[k]) {
                                        if (showCorrect[k] != '=') {
                                            showCorrect[k] = '-';
                                            charGood = true;
                                        }
                                    }
                                }
                            }
                            if (charGood) {
                                showCorrect[i] = '=';
                                checksPast[i] = true;
                                break;
                            }
                            break;

                        } else {
                            showCorrect[i] = '=';
                            checksPast[i] = true;
                        }
                    }
                }
            }

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
     *
     * @param wordGuess
     * @return
     * @throws IOException
     */
    protected boolean realWord(String wordGuess) throws IOException {
        String s;
        String[] words;
        BufferedReader guessCheck = new BufferedReader(new FileReader("C:\\StudentWork\\IntmJ\\GameCenter\\src\\com\\GuruGames\\games\\Wordle\\guesses.txt"));
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

    @Override
    public Boolean checkResults() {
        if (WORD.equalsIgnoreCase(wordGuess)) {
            winner = true;
            return true;
        }
        return false;
    }

    @Override
    public GameData getGameData() {
        return null;
    }

    protected void endGame() {
        if (winner) {
            System.out.println("You have won!!!"+" But you cheated "+cheatCount+" times!");
            System.out.println("Press Enter to go to main menu.");
        } else {
            System.out.println("LOSER!!!!!");
            System.out.println("Press Enter to go to main menu.");
        }
    }

    @Override
    public void parseCommand(String command, String... params) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (command.equalsIgnoreCase("help")) {
            System.out.println(help());
        } else {
            throw new IllegalArgumentException("");
        }
    }


    @Override
    public String help() {
        return ("Enter a 5 letter guess. If the wordle contains the letter it will be printed in yellow. If the letter is in the correct spot it will be printed in green.\n Please enter start/help/quit.");
    }
}