package com.GuruGames.GameCenter.data;

import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Holds all game statistics and saves them to disk.</p>
 * <p>Holds all GameData files in a map for retrieval</p>
 */
public class Player implements Serializable {
    //Player-specific stats
    String username;
    int totalWins;
    int totalLosses;
    //Game-specific stats
    Map<Class<? extends Game>, GameData> stats = new HashMap<>(){};
    //data file objects
    File characterData;
    File visualData;

    /**
     * <p>Used to create a player new to GuruGameCenter.</p>
     * <p>Creates files to store data and text copy.</p>
     */
    Player(String username){
        this.username = username;
        characterData = new File(username+"Save.dat");
        visualData = new File(username+"Save.txt");
    }

    /**
     * Asks user for valid username then tries to load that player's object, if it cannot be found it makes a new player
     * @param username String requested from user for desired name, Only characters
     * @return Player potentially loaded from disk
     */
    public static Player logIn(String username) throws IllegalArgumentException {
        for(Character character : username.toCharArray()) {
            if (!Character.isLetter(character)) throw new IllegalArgumentException("please enter only letters, no special characters, numbers, or spaces");
        }
                try{
                    ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(username+"Save.dat"));
                    return (Player)objectInputStream.readObject();
                } catch (IOException e){
                    return new Player(username);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace(); //should not reach
                    return new Player(username);
                }
    }
    /**
     * Saves game data to player, and cumulates GameData if necessary
     * @param gameName currentGame.getClass
     * @param gameData currentGame.getData
     */
    public void saveStats(Class<? extends Game> gameName, GameData gameData){
        totalWins += gameData.wins;
        totalLosses += gameData.losses;
        if(stats.containsKey(gameName)) {
            stats.get(gameName).addStats(gameData);
        } else {
            stats.put(gameName, gameData);
        }
    }

    /**
     * Stores a dat and txt file to disk
     */
    public void storeStats(){
        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(characterData));
            objectOutputStream.writeObject(this);
            objectOutputStream.close();
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(visualData));
            objectOutputStream.writeObject(this.toString());
            objectOutputStream.close();
        } catch (IOException e){
            System.out.println(characterData);
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns all Stats in string form
     */
    public String checkStats(){
        return this.toString();
    }

    /**
     * Returns Stats of a specified game
     * @param gameString string form of desired game to get stats of
     * @return String containing the stats of specified game
     * @throws IllegalArgumentException player either hasn't played the selected game or entered it in incorrectly
     */
    public String checkStats(String gameString)  throws IllegalArgumentException{
        for(Class<? extends Game> gameClass: stats.keySet()){

            if(gameString.equalsIgnoreCase(gameClass.getName())) {
                return stats.get(gameClass).toString();
            }
        }
        throw new IllegalArgumentException("Stats not found. You either haven't played the selected game or entered it in incorrectly");
    }

    /**
     * Print it pretty
     */

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                ", totalWins=" + totalWins +
                ", totalLosses=" + totalLosses +
                ", stats=" + stats.values() +
                '}';
    }
}
