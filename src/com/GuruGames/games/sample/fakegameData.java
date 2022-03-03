package com.GuruGames.games.sample;

import com.GuruGames.games.GameData;

class fakegameData extends GameData {
    String username;
    int misinputs;
    @Override
    public void addStats(GameData that) {
        if(this.username.equals(((fakegameData)that).username)){
            try {
                this.misinputs += ((fakegameData) that).misinputs;
                this.wins += that.wins;
                this.losses += that.losses;
            } catch (ClassCastException e){
                System.out.println("Invalid Data");
            }
        }
    }
}
