package com.GuruGames.games;

import java.lang.reflect.InvocationTargetException;

/**
 * <p>Shared Command functionality.</p>
 * <p>Intended <a href="sample/fakeGame.LocalCommands.html">LocalCommand</a> Enum for maintainability</p>
 * @see com.GuruGames.games.sample.fakegame
 */
public interface Commands{
    /**
     * Formats a string command/parameter amalgam into a string and string array then sends to implemented parseCommand(String, String...)
     * @param scannerIn String in from a client scanner
     * @throws IllegalArgumentException Exception originates in implemented parseCommand(String, String...)
     */
    default void parseCommand(String scannerIn) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String[] strings = scannerIn.split(" ");
        String command = strings[0];
        String[] params = new String[strings.length - 1]; // extract command
        if (strings.length > 1) {
            // extract parameters
            System.arraycopy(strings, 1, params, 0, strings.length-1);
        }
        parseCommand(command, params); //use other method as implemented
    }

    /**
     * <p>Compares input strings to local command list then calls appropriate method</p>
     * <p>Example parseCommand boilerplate for Games</p>
     * <code>
     *      public void parseCommand(String command, String... params) throws IllegalArgumentException{
     *      <br>&#160;&#160;&#160;&#160;if(params.length == 0){
     *      <br>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;if(command.equalsIgnoreCase("help")){System.out.println(help());}
     *      <br>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;else{
     *      <br>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;throw new IllegalArgumentException("command not found");
     *      <br>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;}
     *      <br>&#160;&#160;&#160;&#160;}
     *      <br>&#160;&#160;&#160;&#160;else {
     *      <br>&#160;&#160;&#160;&#160;&#160;&#160;&#160;&#160;throw new IllegalArgumentException("No commands take this many parameters. Compare your command to the commands found by typing `help`");
     *      <br>&#160;&#160;&#160;&#160;}
     *      <br>}
     * </code>
     * @param command String from user containing desired command
     * @param params String from user containing desired parameter(s)
     * @throws IllegalArgumentException Invalid command, parameter, or parameter count; .getMessage() should have details.
     */
    void parseCommand(String command, String... params) throws IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException;

    /**
     * <p>Gets <a href="sample/fakeGame.LocalCommands.html">local commands</a>, formats them into one string, then returns it.</p>
     * @return String of all commands, and all relevant information on them.
     */
    String help();
    //String help(String command);

}
