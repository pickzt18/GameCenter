package com.GuruGames.games.Wordle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Wordle {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";
    private String[] showCorrect = {"-", "-", "-", "-", "-"};
    private boolean gameOver = false;
    private boolean winner = false;
    private BufferedReader guessCheck = new BufferedReader(new FileReader("C:\\StudentWork\\IntmJ\\GameCenter\\src\\com\\GuruGames\\GameCenter\\Wordle\\guesses.txt"));

    public Wordle() throws FileNotFoundException {
    }


    protected void playGame(String wordGuess, char[] wordle) {
        char[] charGuess = wordGuess.toLowerCase().toCharArray();


        for (int i = 0; i < wordle.length; i++) {
            for (int j = 0; j < charGuess.length; j++) {
                if (showCorrect[j].equals("=") && showCorrect[j].equals("+")) {
                    if (charGuess[i] == wordle[j]) {
                        if (i != j) {
                            showCorrect[i] = "+";
                            break;
                        } else if (i == j) {
                            showCorrect[i] = "=";
                            break;
                        }
                    }
                } else {
                    showCorrect[i] = "-";
                }
            }
        }
        for (int i = 0; i < charGuess.length; i++) {
            if (showCorrect[i].equals("=")) {
                System.out.print(ANSI_GREEN + charGuess[i] + ANSI_RESET);
            } else if (showCorrect[i].equals("+")) {
                System.out.print(ANSI_YELLOW + charGuess[i] + ANSI_RESET);
            } else {
                System.out.print(charGuess[i]);
            }
            showCorrect[i] = "-";
        }
        System.out.println();
    }

    protected boolean realWord(String wordGuess) throws IOException {
        String s;
        String[] words;
        boolean realWord;
        guessCheck = new BufferedReader(new FileReader("C:\\StudentWork\\IntmJ\\GameCenter\\src\\com\\GuruGames\\GameCenter\\Wordle\\guesses.txt"));
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


    protected boolean checkWin(String wordGuess, String word) {
        if (word.equalsIgnoreCase(wordGuess)) {
            winner = true;
            return true;
        }
        return false;
    }

    protected void endGame() {
        if (winner) {
            System.out.println("You have won!!!");
        } else {
            System.out.println("LOSER!!!!!");
        }
    }
}