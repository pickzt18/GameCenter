package com.GuruGames.GameCenter.PegGame;

import org.junit.Test;

import static org.junit.Assert.*;

public class PegGameTest {

    @Test
    public void isValidPiece() {
        PegGame game1 = new PegGame();
        game1.displayBoard(game1.board);
        boolean test = game1.isValidPiece("B", "3", "5","3"); //+ 1 to rows and columns since actual method has -1 due
        // to array starting at 0
        assertFalse("Good fail", test); //makes sure it's false
        boolean test2 = game1.isValidPiece("4", "1", "2", "3");
        assertTrue("Bad fail(don't want this)", test2); //makes sure it's true
    }

    @Test
    public void movePeg(){
        PegGame game2 = new PegGame();
        game2.displayBoard(game2.board);
        char[][] test = game2.movePeg(6, 3, 4, 1);
        boolean test2 = game2.isValidPiece("2", "3", "4", "1");
       game2.displayBoard(game2.board);


    }
}