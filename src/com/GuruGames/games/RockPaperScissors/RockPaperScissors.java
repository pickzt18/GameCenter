package com.GuruGames.games.RockPaperScissors;

import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class RockPaperScissors implements Game{

    RockPaperScissorsData gameData = new RockPaperScissorsData();

    final String[] rps = {"rock", "paper", "scissors"};

    String playerMove;
    String computerMove;

        int wins = 0;
        int losses = 0;

        public RockPaperScissors(){
            System.out.println("Welcome to Rock Paper Scissors");
            System.out.println("Type 'start' to continue");
        }
        public void playGame() {

            computerMove = rps[new Random().nextInt(rps.length)];
            playerMove = null;
            System.out.println("Please enter your move starting with 'shoot' (rock, paper, or scissors)");

        }

    public void isValidHand(String param) throws IllegalArgumentException {
        for(String hand:rps)

        {
            if (param.equalsIgnoreCase(hand)) {
                playerMove = param;
            }
        }
               if (playerMove== null)throw new IllegalArgumentException("Not a hand");
    }

    @Override
    public Boolean checkResults() {
            if (playerMove != null) {
                System.out.println("Computer played " + computerMove);
                if (playerMove.equalsIgnoreCase(computerMove)) {
                    System.out.println("You tied!");
                    return null;
                } else if (playerMove.equals("rock")) {
                    if (computerMove.equals("paper")) {
                        System.out.println("You lose!");
                        gameData.losses++;
                        return false;

                    } else if (computerMove.equals("scissors")) {
                        System.out.println("You win!");
                        gameData.wins++;
                        return true;
                    }
                } else if (playerMove.equals("paper")) {
                    if (computerMove.equals("rock")) {
                        System.out.println("You win!");
                        gameData.wins++;
                        return true;


                    } else if (computerMove.equals("scissors")) {
                        System.out.println("You lose!");
                        gameData.losses++;
                        return false;
                    }
                } else if (playerMove.equals("scissors")) {
                    if (computerMove.equals("paper")) {
                        System.out.println("You win!");
                        gameData.wins++;
                        return true;

                    } else if (computerMove.equals("rock")) {
                        System.out.println("You lose!");
                        gameData.losses++;
                        return false;
                    }
                }
            }
        return null;
    }


    @Override
    public GameData getGameData() {
        return gameData;
    }

    @Override
    public void parseCommand(String command, String... params) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (command.equalsIgnoreCase(LocalCommand.shoot.keyword)) {
            isValidHand(params[0]);
        } else if (command.equalsIgnoreCase("help")) {
                System.out.println(help());
        }
        else{
            throw new IllegalArgumentException("Command not found");
        }
    }

    @Override
    public String help() {
        return "Please enter your move starting with 'shoot' (rock, paper, or scissors)";
    }
    enum LocalCommand{
    shoot("shoot","shoot rock, paper or scissors",1,1,"Rock","Paper","Scissors");
    String keyword;
    String description;
    int minParam;
    int maxParam;
    String[] params;

    LocalCommand(String keyword, String description, int minParam, int maxParam, String... params){
        this.keyword =  keyword;
        this.description = description;
        this.minParam = minParam;
        this.maxParam = maxParam;
        this.params = params;
    }
  }
}
