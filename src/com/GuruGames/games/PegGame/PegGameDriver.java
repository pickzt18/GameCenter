package com.GuruGames.games.PegGame;

public class PegGameDriver {
    public static void main(String[] args) {
        PegGame game1= new PegGame();
        game1.displayBoard(game1.board);

        game1.playGame();
    }
}
