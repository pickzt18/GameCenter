package com.GuruGames.games.Wordle;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class WordleDriverTest {
    private static final String WORD = String.valueOf(Words.values()[new Random().nextInt(Words.values().length)]);
    private static char[] wordle = WORD.toLowerCase().toCharArray();
    private static boolean gameOver = false;
    private static int tries = 0;
    private static String wordGuess;


    public static void main(String[] args) throws IOException {
        System.out.println(WORD);
        Wordle game = new Wordle();
        Scanner in = new Scanner(System.in);
        String s;
        String[] words;
        char[] charGuess;

        while (!gameOver && tries < 5) {
            System.out.println("Enter your 5 letter guess.");
            wordGuess = in.nextLine();
            if (game.realWord(wordGuess)) {
                if (game.checkWin(WORD, wordGuess)) {
                    gameOver=true;
                    game.endGame();
                } else {
                    game.playGame(wordGuess, wordle);
                    System.out.println();
                    tries++;
                    System.out.println("You have " + (5 - tries) + " guesses remaining.");
                }
            }else{
                System.out.println("CHEATER!!!");
            }
        }
        if (tries >= 5) {
            game.endGame();
        }
    }
}

