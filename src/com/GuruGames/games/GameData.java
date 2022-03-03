package com.GuruGames.games;

public abstract class GameData {
    public int wins;
    public int losses;
    //combine with another GameData of the same type Cast data to the implementation
    public abstract void addStats(GameData data);
}
