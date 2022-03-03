package com.GuruGames.games.sample;

import com.GuruGames.games.GameData;

class fakegameData extends GameData {
    int misinputs;
    @Override
    public void addStats(GameData that) {
            try {
                this.misinputs += ((fakegameData) that).misinputs;
                this.wins += that.wins;
                this.losses += that.losses;
            } catch (ClassCastException e){
                System.out.println("Invalid Data");
            }
    }
}
