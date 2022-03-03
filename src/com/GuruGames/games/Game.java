package com.GuruGames.games;

/**
 *
 * <p>Game shared functionality.</p>
 * <p>Game will typically be called as a loop, do playGame() then parseInput() then x=checkResults, while x==null.</p>
 * @see com.GuruGames.games.Commands
 */
public interface Game extends Commands {
    /**
     * <p>Typically prints game info and a prompt for input</p>
     */
    void playGame();

    /**
     * Checks game win and lose condition.
     * @return Boolean object true or false for win and lose condition respectively or null for neither being met
     */
    Boolean checkResults();

    /**
     * Returns GameData object
     */
    GameData getGameData();
}
