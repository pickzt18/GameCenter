package com.GuruGames.games;

import java.io.Serializable;

public abstract class GameData implements Serializable {
    public int wins;
    public int losses;

    //combine with another GameData of the same type Cast data to the implementation
    public abstract void addStats(GameData data);
}
