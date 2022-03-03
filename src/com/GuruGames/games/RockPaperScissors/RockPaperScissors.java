package com.GuruGames.games.RockPaperScissors;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    public static void main(String[] args) {
        String[] rps = {"r", "p", "s"};

        Scanner scanner = new Scanner(System.in);
        
        int wins = 0;
        int losses = 0;
        int tie = 0;

        while (true) {
            String computerMove = rps[new Random().nextInt(rps.length)];

            String playerMove;

            while(true) {
                System.out.println("Please enter your move (r, p, or s)");
                playerMove = scanner.nextLine();
                if (playerMove.equals("r") || playerMove.equals("p") || playerMove.equals("s")) {
                    break;
                }
                System.out.println(playerMove + " is not a valid move.");
            }

            System.out.println("Computer played: " + computerMove);

            if (playerMove.equals(computerMove)) {
                System.out.println("The game was a tie!");
                tie++;
            }
            
            else if (playerMove.equals("r")) {
                if (computerMove.equals("p")) {
                    System.out.println("You lose!");
                    losses++;

                } else if (computerMove.equals("s")) {
                    System.out.println("You win!");
                    wins++;
                }
            }

            else if (playerMove.equals("p")) {
                if (computerMove.equals("r")) {
                    System.out.println("You win!");
                    wins++;

                } else if (computerMove.equals("s")) {
                    System.out.println("You lose!");
                    losses++;
                }
            }

            else if (playerMove.equals("s")) {
                if (computerMove.equals("p")) {
                    System.out.println("You win!");
                    wins++;

                } else if (computerMove.equals("r")) {
                    System.out.println("You lose!");
                    losses++;
                }
            }
            
            System.out.println("you have won " + wins + " games!");
            System.out.println("you have won " + losses + " games!");
            System.out.println("Game ended in a tie " + tie +  "");

            System.out.println("Play again? (y/n)");
            String playAgain = scanner.nextLine();

            if (!playAgain.equals("y")) {
                break;
            }
        }
        scanner.close();
    }
}
