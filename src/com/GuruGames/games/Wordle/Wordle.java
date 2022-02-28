package com.GuruGames.games.Wordle;

import java.util.Random;

/*
public class Wordle {
    final String WORD=random Enum;
    char [] wordle=WORD.split
    String playerChoice

    int turn=0
    public void playGame(){
        playerChoice=enter word
        char [] playerWord=playerChoice.split
        while(!gameOver&&turns<6){
            for(int i=0; i<playerWords.length; i++){
                for(int j=0; j<wordle.length; j++){
                    if(pw[i]==wordle[j]){
                        if(i==j){
                            Letter pw[i] is in the correct place
                         }else{
                            Letter pw[i] is in the incorrect place
                         }
                    }
                }
             }
            checkResults()
        }
    }
    public void checkResults(){
        turn++;
        if(WORD.equalsIgnoreCase(playerChoice){
            winMsg();
        else if(turn=6){
            loseMsg()
        }else{
            input new word
        }
    }
}
*/
public class Wordle{
    public static void main(String[] args) {
        String WORD= String.valueOf(Words.values()[new Random().nextInt(Words.values().length)]);
        char[] wordle=WORD.toCharArray();
        for (var a:wordle)
            System.out.println(a);
    }
}