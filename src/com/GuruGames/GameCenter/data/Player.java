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
    Map<Class<? extends Game>, GameData> stats = new HashMap<>();
    File characterData;
    File visualData;
    //private constructor creates a new Player if one of that username cannot be found in disk
    Player(String username){
        this.username = username;
        characterData = new File(username+"Save.dat");
        visualData = new File(username+"Save.txt");
    }
    //checks disk for save files of provided username else creates new files and returns a new Player
    public static Player logIn(String username) {
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
    //adds GameData object to map
    public void saveStats(Class<? extends Game> gameName, GameData gameData){
        totalWins += gameData.wins;
        totalLosses += gameData.losses;
        if(stats.containsKey(gameName)) {
            stats.get(gameName).addStats(gameData);
        } else {
            stats.put(gameName, gameData);
        }
    }
    //stores player info in disk
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
    //returns stats in string form
    public String checkStats(){
        return this.toString();
    }
    //returns stats of a specific game in string form
    public String checkStats(String gameString)  throws IllegalArgumentException{
        for(Class<? extends Game> gameClass: stats.keySet()){
            if(gameString.equalsIgnoreCase(gameClass.toString())) return stats.get(gameClass).toString();
        }
        throw new IllegalArgumentException("Stats not found. You either haven't played the selected game or entered it in incorrectly");
    }

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
