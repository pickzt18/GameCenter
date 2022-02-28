package com.GuruGames.games;
import com.GuruGames.games.sample.fakegame;

public class GameFactory {
    /**
     * enum of Game
     */
    enum GameEnum {
        fakegame(), Wordle(), PegSolitaire(), RPS();

    }
    public static Game parseGame(String gameString) throws IllegalArgumentException{
        GameFactory.GameEnum[] gameEnums = GameFactory.GameEnum.values();
        if(gameString.equalsIgnoreCase(gameEnums[0].toString())){
            return new fakegame();
        }
        else throw new IllegalArgumentException("Game not found. You can use command getAllGames() to get a list of installed games");
    }
    public static String[] getAllGames() {
        GameEnum[] gameEnums = GameEnum.values();
        String[] games = new String[gameEnums.length];
        for (int i = 0; i < gameEnums.length; i++) {
            games[i] = gameEnums[i].toString();
        }
        return games;
    }
}
