package com.GuruGames.GameCenter;

import com.GuruGames.GameCenter.data.Player;
import com.GuruGames.games.Game;
import com.GuruGames.games.GameFactory;
import com.GuruGames.games.Commands;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * <p>Manages Game, gets Scanner input, and implements its own commands for doing so with <a href="GameCenter.LocalCommands.html">LocalCommand</a> Enum</p>
 * <p>Singleton- use .getInstance(Scanner)</p>
 * @see com.GuruGames.games.Game
 */
public class GameCenter implements Commands {
    static GameCenter gameCenter = null;
    Game currentGame = null;
    IllegalArgumentException e;
    public static Scanner in;
    public Player currentPlayer;
    Boolean gameOver = null;

    /**
     * not intended for construction, should be called with appropriate input. As of now, we only take Scanners.
     */
    GameCenter(){
        //currentPlayer = new Player(getScannerInput());
    }

    /**
     * Called by getInstance(Scanner), assigns Scanner to in
     */
    GameCenter(Scanner in){
        this();
        this.in=in;
    }

    /**
     * @deprecated just for testing DO NOT USE
     * Makes a GameCenter if one does not exist, then returns only instance of GameCenter.
     * @return Singleton instance of GameCenter without an input source.
     */
    public static GameCenter getInstance(){ //testing
        if(gameCenter==null){
            gameCenter = new GameCenter();
        }
        return gameCenter;
    }

    /**
     * Makes a GameCenter if one does not exist, assigns input if necessary, and returns the only instance of GameCenter.
     * @param in Scanner usually on System.in
     * @return Singleton instance of GameCenter with provided Scanner
     */
    public static GameCenter getInstance(Scanner in){
        if(gameCenter==null){
            gameCenter = new GameCenter(in);
        } else if(gameCenter.in==null){
            gameCenter.in=in;
        }
        while(true) {
            try {
                System.out.println("Welcome to the GuruGames Center! Please enter a username to make an account:");
                gameCenter.currentPlayer = Player.logIn(gameCenter.getScannerInput());
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
            System.out.println("Enter 'game' and select what you would like to play (wordle, pegsolitaire or rps), type 'help' for available commands, or type 'stats' to see your statistics, or enter 'close' to end Game Center:");
            if(gameCenter.currentPlayer != null) {
                return gameCenter;
            }
        }
    }

    /**
     * Gets all games from the game factory.
     * @return String array each string being an available game
     */
    public static String[] getAllGames() {
        return GameFactory.getAllGames();
    }

    /**
     * Waits for input from Scanner
     * @return String command/parameter amalgam
     */
    String getScannerInput(){
        String input = in.nextLine();
        return input;
    }

    /**
     * Calls GameFactory to try parsing the gameString
     * @throws IllegalArgumentException Invalid Game name, .getMessage on error will show that.
     */
    void setCurrentGame(String gameString) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        this.currentGame = GameFactory.parseGame(gameString);
    }

    /**
     * <p>Starts the game loop.</p>
     * <p>Calls currentGame::playGame, then gameCenter::getScannerInput, then currentGame::checkResults, then loops.</p>
     * <p>Error messages are printed to the console</p>
     */
    void startGame() {
        gameOver=null;
        if(in!=null) {
            while (gameOver == null && currentGame !=null){
                currentGame.playGame();
                try {
                    parseCommand(gameCenter.getScannerInput());
                } catch (InvocationTargetException invocationTargetException) {
                    invocationTargetException.printStackTrace();
                } catch (InstantiationException instantiationException) {
                    instantiationException.printStackTrace();
                } catch (IllegalAccessException illegalAccessException) {
                    illegalAccessException.printStackTrace();
                } catch (NoSuchMethodException noSuchMethodException) {
                    noSuchMethodException.printStackTrace();
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                if (gameOver == null) {
                gameOver = currentGame.checkResults();
            }
            }
            currentPlayer.saveStats(currentGame.getClass(), currentGame.getGameData());
            System.out.println("Enter 'game' and select what you would like to play (wordle, pegsolitaire or rps), type 'help' for available commands, type 'stats' to see your statistics, or enter 'close' to end Game Center:");
            currentGame = null;

        }
        else {
            System.out.println("Games require input! Currently we only support Scanners, add a Scanner to your get instance method to enable functionality");
        }
    }

    //command parser, takes a command string then multiple parameter strings.
    //Initial sort by parameter count then command, sends string parameters for parsing

    /**
     * Checks String against LocalCommands then if necessary, passes Strings... for parsing by called function.
     * @param command String from user containing desired command
     * @param params String from user containing desired parameter(s)
     * @throws IllegalArgumentException Invalid command, parameter, or parameter count; .getMessage() should have details.
     */
    @Override
    public void parseCommand(String command, String... params) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        e=null;
        if(currentGame != null) {
            try {
                currentGame.parseCommand(command, params); //if playing a game, try to use that command list
                return;
            } catch (IllegalArgumentException e){
                this.e=e;
            }
        }
        if (params.length == 0) { //all 0 arg commands
            if (command.equalsIgnoreCase("help")) System.out.println(help());
            else if (command.equalsIgnoreCase(LocalCommands.startGame.keyword)) {
                startGame();
            } else if(command.equalsIgnoreCase(LocalCommands.quitGame.keyword)){
                if(currentGame==null){
                    parseCommand("close");
                }
                currentPlayer.saveStats(currentGame.getClass(), currentGame.getGameData());
                gameOver = false;
            } else if (command.equalsIgnoreCase(LocalCommands.closeProgram.keyword)) {
                currentPlayer.storeStats();
                System.exit(0);
            } else if(command.equalsIgnoreCase(LocalCommands.savePlayer.keyword)) {
                currentPlayer.storeStats();
            } else if(command.equalsIgnoreCase(LocalCommands.getStats.keyword)){
                System.out.println(currentPlayer.checkStats());
            }
            else { //command not found
                if(e!=null) throw e;
                throw new IllegalArgumentException("The command specified doesn't exist or takes parameters. You can type 'help' for a list of commands.");
            }
        } else if (params.length == 1) { //all 1 arg commands
            if (command.equalsIgnoreCase(LocalCommands.setCurrentGame.keyword)) {
                setCurrentGame(params[0]);
            } else if(command.equalsIgnoreCase(LocalCommands.getStats.keyword)){
                currentPlayer.checkStats(params[0]);
            }
            else { //command not found
                if(e!=null) throw e;
                throw new IllegalArgumentException("The command specified doesn't exist or takes a different amount of parameters. You can type 'help' for a list of commands.");
            }
        }
        else { //too many parameters
            if(e!=null) throw e;
            throw new IllegalArgumentException("No commands take this many parameters. Compare your command to the commands found by typing `help`");
        }
    }

    /**
     * gets LocalCommands, then formats them into a single string, and returns it
     * @return formatted String of all commands and their relevant information
     */
    @Override
    public String help() {
        StringBuilder stringBuilder = new StringBuilder();
        for(LocalCommands localCommands : LocalCommands.values()){
            stringBuilder.append(localCommands);
            stringBuilder.append(System.getProperty("line.separator"));
        }
        return stringBuilder.toString();
    }

    /**
     * <p>Registry of all commands in this locality.</p>
     * <p>parseCommand(String, String...) compares String with keyword value</p>
     * <p>help() gathers all information found here</p>
     */
    enum LocalCommands {
        setCurrentGame("game","Selects a game. Available games are: "+ Arrays.toString(GameFactory.getAllGames()), 1,1,  "Wordle", "PegSolitaire", "RPS"),
        startGame("start", "Starts the selected game"),
        quitGame("quit", "Returns to the GuruGameCenter"),
        savePlayer("save", "Saves user data to disk"),
        getStats("stats", "displays selected stats",0,1,null,  "Wordle", "PegSolitaire", "RPS"),
        closeProgram("close", "Stops GuruGameCenter program");
        String keyword;
        String description;
        int minParam;
        int maxParam;
        String[] parameters;

        /**
         * No parameter command constructor
         * @param keyword String that gets compared to first word user types
         * @param description String of what this command does as far as user is concerned
         */
        LocalCommands(String keyword, String description) {
            this.keyword = keyword;
            this.description = description;
        }

        /**
         * Parameterable command constructor
         * @param keyword String that gets compared to first word user types
         * @param description String of what this command does as far as user is concerned
         * @param minParam int minimum allowed parameters (should really be 0 or above)
         * @param maxParam int maximum allowed parameters (should really be 1 or above)
         * @param parameters String vararg of known parameter options
         */
        LocalCommands(String keyword, String description, int minParam, int maxParam, String... parameters) {
            this.keyword = keyword;
            this.description = description;
            this.maxParam = maxParam;
            this.minParam = minParam;
            this.parameters = parameters;
        }

        /**
         * print it pretty
         */
        @Override
        public String toString() {
            return
                    "keyword='" + keyword + '\'' +
                    ", description='" + description + '\'' +
                    ", minParam=" + minParam +
                    ", maxParam=" + maxParam +
                    ", parameters=" + Arrays.toString(parameters);
        }
    }
}

