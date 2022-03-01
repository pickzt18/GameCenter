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
        Wordle game=new Wordle();
        game.playGame();
}}

