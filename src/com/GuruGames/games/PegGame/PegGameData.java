package com.GuruGames.games.PegGame;
import com.GuruGames.games.GameData;

public class PegGameData extends GameData {
    int twoPegsLeft;
    int numJumps;

    @Override
    public void addStats(GameData data) {
            try {
                this.twoPegsLeft += ((PegGameData) data).twoPegsLeft;
                this.numJumps += ((PegGameData) data).numJumps;
                this.wins += data.wins;
                this.losses += data.losses;
            } catch (ClassCastException e){
                System.out.println("Invalid Data");
            }
        }


    @Override
    public String toString() {
        return "PegGameData{" +
                "wins=" + wins +
                ", losses=" + losses +
                ", twoPegsLeft=" + twoPegsLeft +
                ", numJumps=" + numJumps +
                '}';
    }
}
