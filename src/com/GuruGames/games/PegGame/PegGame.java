package com.GuruGames.games.PegGame;
import com.GuruGames.games.Game;
import com.GuruGames.games.GameData;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class PegGame implements Game {
    PegGameData data = new PegGameData();
    public static final String ANSI_RED = "\u001B[31m"; //to make an invalid move red
    public static final String ANSI_RESET = "\u001B[0m"; //to reset the red

    char[][] board =
            {{' ', ' ', ' ', ' ', '-', ' ', ' ', ' ', ' '},
                    {' ', ' ', ' ', '@', ' ', '@', ' ', ' ', ' '},
                    {' ', ' ', '@', ' ', '@', ' ', '@', ' ', ' '},
                    {' ', '@', ' ', '@', ' ', '@', ' ', '@', ' '},
                    {'@', ' ', '@', ' ', '@', ' ', '@', ' ', '@'}};
    boolean isValid;
    Boolean checkResults;
    int count = 0;

    public PegGame() {
        System.out.println("Enter 'start' to begin game, 'help' for instructions, or 'quit' to return to the main menu");
    }

    /**
     * This method prints out the contents of the board using @s to
     * represent pegs, -s to represent free spaces, and spaces to represent empty
     * spots that are neither pegs nor holes. The displayBoard method loops around the
     * board to add row and column numbers for the user to select from when making a move.
     *
     * @param board - receives the board design
     * @return - board with rows and columns added
     */

    public static void displayBoard(char[][] board) {

        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print(i + 1);
        }
        System.out.println();
        for (int j = 0; j < board.length; j++) {
            System.out.print((j + 1) + " ");
            for (int i = 0; i < board[0].length; i++) {
                System.out.print(board[j][i]);
            }
            System.out.println();
        }
    }

    /**
     * After obtaining integer inputs from the movePeg method, this method will check whether that input is
     * within the specified range. If the input does not fall within the required range,
     * it prints a red error message before giving the user another chance to enter valid input.
     * The user is given as many chances as they need to enter valid coordinates.
     *
     * @param rowInput -the requested row to move (int value from movePeg method)
     * @param columnInput - the requested column to move (int value from movePeg method)
     * @param rowDest - the requested row destination (int value from movePeg method)
     * @param columnDest - the requested column destination (int value from movePeg method)
     * @return - true when the proposed move is legal, otherwise false.
     */

    public boolean isValidPiece(int rowInput, int columnInput, int rowDest, int columnDest) throws IllegalArgumentException {
        if (rowInput == 4 && columnInput == 8 && rowDest ==0 && columnDest == 4) {
            isValid= false;
            throw new IllegalArgumentException(ANSI_RED+"You have selected an invalid move."+ANSI_RESET);
        }
        if (rowInput == 4 && columnInput == 0 && rowDest == 0 && columnDest == 4) {
            isValid= false;
            throw new IllegalArgumentException(ANSI_RED+"You have selected an invalid move."+ANSI_RESET);
        }
        if (columnInput < 0 || columnInput > 9 || rowInput < 0 || rowInput > 5) {
            isValid = false;
            throw new IllegalArgumentException(ANSI_RED + "Please select a valid number (Column 1-9, Row 1-5)."+ANSI_RESET);
        } else {
            int[] middle = getMiddlePiece(rowInput, columnInput, rowDest, columnDest);
            int middleRow = middle[0];
            int middleColumn = middle[1];
            if (board[rowDest][columnDest] == '-' && board[rowInput][columnInput] == '@' && board[middleRow][middleColumn] == '@') {
                if (rowInput == rowDest) {
                    if (Math.abs(columnInput - columnDest) == 4) {
                        isValid = true;
                    } else {
                        isValid = false;
                        throw new IllegalArgumentException(ANSI_RED+"You have selected an invalid move."+ANSI_RESET);
                    }
                } else if (Math.abs(rowInput - rowDest) == 2) { // come back to this, 5 1 issues
                    if (Math.abs(columnInput - columnDest) == 2) {
                        isValid = true;
                    } else {
                        isValid = false;
                        throw new IllegalArgumentException(ANSI_RED+"You have selected an invalid move."+ANSI_RESET);
                    }
                }
            } else {
                isValid = false;
                throw new IllegalArgumentException(ANSI_RED+"You have selected an invalid move."+ANSI_RESET);
            }
        }
        return isValid;
    }

    /**
     * This method is used to read in all inputs from the user. It first checks if the input is
     * an integer and then uses the isValidPiece method to make sure the integer is in the valid range before
     * continuing. If the user’s input does not represent an integer, it prints a red error message before giving the
     * user another chance to enter valid input. When valid input is entered, the board will be updated replacing the
     * old peg position with '-' and the new peg position with '@'. The method then uses the method getMiddlePiece
     * to obtain the peg that was skipped and replace it with '-'. The user's move is then printed out.
     *
     * @param row - obtains user input for the peg row they would like to move which will be parsed into an int
     * @param column - obtains user input for the peg column they would like to move which will be parsed into an int
     * @param destinationRow - obtains user input for the peg row they would like to move to which will be parsed into an int
     * @param destinationColumn - obtains user input for the peg row they would like to move to which will be parsed into an int
     * @return board - the updated board after a valid move has been implemented/played
     */

    public char[][] movePeg(String row, String column, String destinationRow, String destinationColumn) {
        int columnInput = 0;
        int rowInput = 0;
        int columnDest = 0;
        int rowDest = 0;

        try {
            columnInput = Integer.parseInt(column) - 1;
            rowInput = Integer.parseInt(row) - 1;
            columnDest = Integer.parseInt(destinationColumn) - 1;
            rowDest = Integer.parseInt(destinationRow) - 1;
        } catch (NumberFormatException e) {
            System.out.println(ANSI_RED+"Please enter a valid number."+ANSI_RESET);
        }

        if (isValidPiece(rowInput, columnInput, rowDest, columnDest)) {
            data.numJumps++; //recording stat
            board[rowInput][columnInput] = '-';
            board[rowDest][columnDest] = '@';

            int[] middle = getMiddlePiece(rowInput, columnInput, rowDest, columnDest);
            int middleRow = middle[0];
            int middleColumn = middle[1];
            board[middleRow][middleColumn] = '-';
            System.out.println("Moved a peg from row " + (rowInput + 1) + ", column " + (columnInput + 1) + " to row " + (rowDest + 1) + ", column " + (columnDest + 1));
        }
        return board;
    }

    /**
     * This method finds the coordinates of the peg in the middle of the selected destination and starting peg spots.
     * It is then used by the movePeg method to remove ('-') the skipped peg.
     *
     * @param rowInput -the requested row to move (int value from movePeg method)
     * @param columnInput - the requested column to move (int value from movePeg method)
     * @param rowDest - the requested row destination (int value from movePeg method)
     * @param columnDest - the requested column destination (int value from movePeg method)
     * @return middle - the int coordinates of the middle piece to be skipped if a valid move is preformed
     */

    public int[] getMiddlePiece(int rowInput, int columnInput, int rowDest, int columnDest) {
        int[] middle = {0, 0};
        int middleRow;
        int middleColumn;
        boolean isSameRow = false;
        if (rowInput > rowDest) {
            middleRow = rowInput - 1;
        } else if (rowInput == rowDest) {
            middleRow = rowInput;
            isSameRow = true;
        } else {
            middleRow = rowDest - 1;
        }
        if (isSameRow) {
            if (columnInput > columnDest) {
                middleColumn = columnInput - 2;
            } else if (columnInput == columnDest) {
                middleColumn = columnDest;
            } else {
                middleColumn = columnDest - 2;
            }
        } else {
            if (columnInput > columnDest) {
                middleColumn = columnInput - 1;
            } else if (columnInput == columnDest) {
                middleColumn = columnDest;
            } else {
                middleColumn = columnDest - 1;
            }
        }
        middle[0] = middleRow;
        middle[1] = middleColumn;

        return middle;
    }

    /**
     * This method counts up the number of pegs left on the board and returns that number.
     * Used by checkResults method for deciding when the game is over.
     *
     * @param board - the board that pegs are counted from.
     * @return - the number of pegs found in that board.
     */
    public int countPegsRemaining(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '@') {
                    count++;
                }
            }
        }
        if (count == 2) { //recording stat
            data.twoPegsLeft++;
        }
        return count;
    }

    /**
     * This method counts up the number of legal moves that are available to be
     * performed on the current board and returns false if there are no moves available.
     * Used by checkResults method for deciding when the game is over.
     *
     * @param board - the board that possible moves are counted from.
     * @return - false if no moves are available
     */

    public boolean countMovesAvailable(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                for (int q = 0; q < board.length; q++) {
                    for (int x = 0; x < board[0].length; x++) {
                        try {
                            if (isValidPiece(i, j, q, x)) {
                                return true;
                            }
                        } catch (IllegalArgumentException e) {
                        }
                    }
                }
            }
        }
        return false;
    }


    public void playGame() {
        if (count == 0) {
            System.out.println("Write 'move' followed by the row of the peg you would like to move, the column of the peg you would like to move, the number of the row you would like to move your peg to and the column that you would like to move your peg to (ex. move 3 2 4 1)");
        }
        displayBoard(board);
        count++;
    }

    @Override
    public Boolean checkResults() {
        if (!countMovesAvailable(board)) {
            if (countPegsRemaining(board) == 1) {
                System.out.println("Congrats you won!");
                data.wins++;
                checkResults = true;
            } else {
                System.out.println(ANSI_RED+"You lose."+ANSI_RESET);
                data.losses++;
                checkResults = false;
            }
        } else {
            checkResults = null;
            System.out.println("Please make your next move.");
        }
        return checkResults;
    }

    @Override
    public void parseCommand(String command, String... params) throws
            IllegalArgumentException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        if (command.equalsIgnoreCase("help")) {
            System.out.println(help());
        } else if (command.equalsIgnoreCase("move") && params.length == 4) {
            movePeg(params[0], params[1], params[2], params[3]);
        } else {
            throw new IllegalArgumentException("");
        }
    }

    @Override
    public String help() {
        return "Write move followed by the row of the peg you would like to move, the column of the peg you would like to move, the number of the row you would like to move your peg to and the column that you would like to move your peg to (ex. move 3 2 4 1). A legal move involves jumping one peg (@) over a neighboring peg to rest in a hole (-) on the other side which removes peg that was jumped over. Diagonal jumps are not allowed.";
    }

    @Override
    public GameData getGameData() {
        return data;
    }

    @Override
    public String toString() {
        return "PegGame{" +
                "data=" + data +
                ", board=" + Arrays.toString(board) +
                ", isValid=" + isValid +
                ", checkResults=" + checkResults +
                ", count=" + count +
                '}';
    }
}

