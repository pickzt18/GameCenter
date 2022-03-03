package com.GuruGames.games;
import java.lang.reflect.InvocationTargetException;
/**
 * enum of Game
 */
enum GameEnum {
    fakegame(com.GuruGames.games.sample.fakegame.class), Wordle(com.GuruGames.games.Wordle.Wordle.class), PegSolitaire(com.GuruGames.games.PegGame.PegGame.class), RPS(com.GuruGames.games.RockPaperScissors.RockPaperScissors.class);
    Class<? extends Game> gameClass;
    GameEnum(Class gameClass){
        this.gameClass = gameClass;
    }
     Game returnGame(String username) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return gameClass.getConstructor().newInstance(username);
    }
}

public class GameFactory {
    public static Game parseGame(String gameString, String username) throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        GameEnum[] gameEnums = GameEnum.values();
        for(GameEnum gameEnum: gameEnums){
            if(gameString.equalsIgnoreCase(gameEnum.toString())){
                return gameEnum.returnGame(username);
            }
        }
        throw new IllegalArgumentException("Game not found. You can use command getAllGames() to get a list of installed games");
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
