package com.GuruGames.games.Wordle;

import com.GuruGames.games.GameData;

import java.io.Serializable;

public class WordleGameData extends GameData implements Serializable {
    public int cheatCount;
    @Override
    public void addStats(GameData data) {
        this.wins+=data.wins;
        this.losses+=data.losses;
        this.cheatCount+=((WordleGameData)data).cheatCount;
    }

    @Override
    public String toString() {
        return "Wordle: " +
                "wins=" + wins +
                ", losses=" + losses +
                ", cheatCount=" + cheatCount;
    }
}
