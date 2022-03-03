package com.GuruGames.games.sample;

import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * fake implementation for reference when adding your own game to GuruGameCenter
 */
public class fakegame implements Game {
    fakegameData data;
    public fakegame(String username) {
        System.out.println("Loading");
        data = new fakegameData(username);
    }

    //Game Implementation, called from client
    @Override
    public void playGame() {
        System.out.println("insert fun here");

    }

    @Override
    public Boolean checkResults() {
        int val = (int) (Math.random() * 10);
        if (val == 0) return false;//lose
        if (val == 3) return true;//win
        else return null;//none
    }

    void moveCharacter(String param) throws IllegalArgumentException {
        for (String dir : LocalCommands.move.parameters) {
            if (dir.equalsIgnoreCase(param)) {
                System.out.println("Character moved " + dir);
                return;
            }
        }
        throw new IllegalArgumentException("Unknown direction. Directions are " + LocalCommands.move.parameters);
    }

    @Override
    public void parseCommand(String command, String... params) throws IllegalArgumentException {
        if (params.length == 0) {
            if (command.equalsIgnoreCase("help")) {
                System.out.println(help());
            } else {
                throw new IllegalArgumentException("command not found");
            }
        } else if (params.length == 1) {
            if (command.equalsIgnoreCase(LocalCommands.move.keyword)) {
                moveCharacter(params[0]);
            } else {
                throw new IllegalArgumentException("command not found");
            }
        } else {
            throw new IllegalArgumentException("No commands take this many parameters. Compare your command to the commands found by typing `help`");
        }
    }

    @Override
    public String help() {
        StringBuilder stringBuilder = new StringBuilder();
        for(LocalCommands command : LocalCommands.values()) stringBuilder.append(command);
        return stringBuilder.toString();
    }

    @Override
    public GameData getGameData() {
        return data;
    }

    /**
     * <p>Registry of all commands in this locality.</p>
     * <p>parseCommand(String, String...) compares String with keyword value</p>
     * <p>help() gathers all information found here</p>
     */
    enum LocalCommands {
        move("move", "Changes your position, ex. move up", 1, 1, new String[]{"up", "down", "left", "right"}),
        close("close", "Closes the game");
        String keyword;
        String description;
        int minParam;
        int maxParam;
        String[] parameters;

        /**
         * No parameter command constructor
         *
         * @param keyword     String that gets compared to first word user types
         * @param description String of what this command does as far as user is concerned
         */
        LocalCommands(String keyword, String description) {
            this.keyword = keyword;
            this.description = description;
        }

        /**
         * Parameterable command constructor
         *
         * @param keyword     String that gets compared to first word user types
         * @param description String of what this command does as far as user is concerned
         * @param minParam    int minimum allowed parameters (should really be 0 or above)
         * @param maxParam    int maximum allowed parameters (should really be 1 or above)
         * @param parameters  String vararg of known parameter options
         */
        LocalCommands(String keyword, String description, int minParam, int maxParam, String... parameters) {
            this.keyword = keyword;
            this.description = description;
            this.maxParam = maxParam;
            this.minParam = minParam;
            this.parameters = parameters;
        }
    }
}
