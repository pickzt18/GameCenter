package com.GuruGames.games.PegGame;

import com.GuruGames.games.Game;

import java.lang.reflect.InvocationTargetException;


public class PegGame implements Game {
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
        System.out.println("Enter start to begin game or enter help for instructions");
    }

    public static void displayBoard(char[][] board) { //to print the rows and columns

        System.out.println();
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print(i + 1);
        }
        System.out.println();
        for (int j = 0; j < board.length; j++) {
            System.out.print((j + 1) + " "); //   //for loop for row iteration.
            for (int i = 0; i < board[0].length; i++) {
                System.out.print(board[j][i]); //for loop for column iteration.
            }
            System.out.println();
        }
    }

    public boolean isValidPiece(int rowInput, int columnInput, int rowDest, int columnDest) throws IllegalArgumentException {

        if (columnInput < 0 || columnInput > 9 || rowInput < 0 || rowInput > 5) {
            isValid = false;
            throw new IllegalArgumentException("Please select a valid number (Column 1-9, Row 1-5).");
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
                    }
                } else if (Math.abs(rowInput - rowDest) == 2) {
                    if (Math.abs(columnInput - columnDest) == 2) {
                        isValid = true;
                    } else {
                        isValid = false;
                    }
                }
            } else {
                isValid = false;
                throw new IllegalArgumentException("You have selected an invalid move.");
            }
        }
        return isValid;
    }

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
            System.out.println("Please enter a valid number.");
        }

        if (isValidPiece(rowInput, columnInput, rowDest, columnDest)) {
            board[rowInput][columnInput] = '-';
            board[rowDest][columnDest] = '@';

            int[] middle = getMiddlePiece(rowInput, columnInput, rowDest, columnDest);
            int middleRow = middle[0];
            int middleColumn = middle[1];
            board[middleRow][middleColumn] = '-';

        }
        displayBoard(board);
        System.out.println("Moved a peg from row " + (rowInput + 1) + ", column " + (columnInput + 1) + " to row " + (rowDest + 1) + ", column " + (columnDest + 1));
        System.out.println("Please make your next move.");

        return board;
    }

    public int[] getMiddlePiece(int rowInput, int columnInput, int rowDest, int columnDest) {
        int[] middle = {0, 0};
        int middleRow;      //to remove peg in the middle of the skipped one
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

    public int countPegsRemaining(char[][] board) {
        int count = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '@') {
                    count++;
                }
            }
        }
        return count;
    }

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

        if (!countMovesAvailable(board)) {
            if (countPegsRemaining(board) == 1) {
                System.out.println("Congrats you won!");
                checkResults = true;
                System.out.println("Press enter to return to main menu");
            } else {
                System.out.println("You lose");
                checkResults = false;
                System.out.println("Press enter to return to main menu");
            }
        } else {
            checkResults = null;
        }
    }

    @Override
    public Boolean checkResults() {
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
    public addStats(PegGameData data) {
        PegGameData data
    }
    public abstract class GameData {
        public int wins;
        public int losses;
        //combine with another GameData of the same type Cast data to the implementation
        public abstract void addStats(GameData data);
    }

}
