package com.GuruGames.GameCenter.data;

import com.GuruGames.games.GameData;
import com.GuruGames.games.GameFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    String username;
    int totalWins;
    int totalLosses;
    Map<Enum, ArrayList<GameData>> stats = new HashMap<>();
    public Player(String username){
        this.username = username;
    }
    public void writeStats(Enum gameEnum, GameData gameData){
        stats.put(gameEnum, gameData);
    }

}
