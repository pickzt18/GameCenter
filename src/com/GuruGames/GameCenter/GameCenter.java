package com.GuruGames.GameCenter;

import com.GuruGames.GameCenter.data.Player;
import com.GuruGames.games.Game;
import com.GuruGames.games.GameFactory;
import com.GuruGames.games.Commands;

import java.lang.reflect.InvocationTargetException;
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

    /**
     * not intended for construction, should be called with appropriate input. As of now, we only take Scanners.
     */
    GameCenter(){
        System.out.println("Welcome to the GuruGames Center, please enter a command or type help for available commands");
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
        return gameCenter;
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
    Boolean gameOver = null;
        if(in!=null) {
            do {
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
                gameOver = currentGame.checkResults();
            } while (gameOver == null);
            System.out.println("Welcome to the GuruGames Center, please enter a command or type help for available commands");

        }
        else {
            System.out.println("Games require input! Currently we only support Scanners, add a Scanner to your get instance method to enable functionality");
        }
//        currentPlayer.

        currentGame = null;
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
            } else if (command.equalsIgnoreCase(LocalCommands.quitProgram.keyword)) System.exit(0);
            else { //command not found
                if(e!=null) throw e;
                throw new IllegalArgumentException("The command specified doesn't exist or takes parameters. You can type 'help' for a list of commands.");
            }
        } else if (params.length == 1) { //all 1 arg commands
            if (command.equalsIgnoreCase(LocalCommands.setCurrentGame.keyword)) {
                setCurrentGame(params[0]);
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
        return "commands go here"; ///////
    }

    /**
     * <p>Registry of all commands in this locality.</p>
     * <p>parseCommand(String, String...) compares String with keyword value</p>
     * <p>help() gathers all information found here</p>
     */
    enum LocalCommands {
        setCurrentGame("game","Selects a game. Available games are: "+ GameFactory.getAllGames(), 1,1, "fakegame, Wordle, PegSolitaire, RPS"),
        startGame("start", "Starts the selected game"),
        quitProgram("quit", "Stops GuruGameCenter program");
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
    }
}

