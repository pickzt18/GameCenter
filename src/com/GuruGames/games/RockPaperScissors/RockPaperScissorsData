package com.GuruGames.games.RockPaperScissors;

import com.GuruGames.games.GameData;

import java.io.Serializable;

public class RockPaperScissorsData extends GameData implements Serializable {

    @Override
    public void addStats(GameData data) {
        this.wins += data.wins;
        this.losses += data.losses;
    }

    @Override
    public String toString() {
        return "RockPaperScissorsData " +
                "wins=" + wins +
                ", losses=" + losses +
                "";
    }
}
