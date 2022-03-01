package com.GuruGames.games.PegGame;

public class PegGame {
    char[][] board =
                     {{' ', ' ', ' ', '-', ' ', ' ', ' '},
                    {' ', ' ', '@', ' ', '@', ' ', ' '},
                    {' ', '@', ' ', '@', ' ', '@', ' '},
                    {'@', ' ', '@', ' ', '@', ' ', '@'}};
    boolean isValid;
    boolean checkResults;

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


// uses ParseCommand to get two strings and check if they are valid
    public  boolean isValidPiece(String column, String row, String destinationColumn, String destinationRow) {
        //trying to move to vs destination that they're actually moving to
        int columnInput = Integer.parseInt(column) - 1;
        int rowInput = Integer.parseInt(row) - 1;
        int columnDest = Integer.parseInt(destinationColumn) - 1;
        int rowDest = Integer.parseInt(destinationRow) - 1;

//        System.out.println(columnInput + " " + rowInput + " " + columnDest + " " + rowDest);

        if (columnInput < 0 || columnInput > 7 ) { //!Character.isDigit(columnInput)
            System.out.println("Please select a number between 1-7.");
            displayBoard(board);
            isValid = false;
        } else if (rowInput < 0 || rowInput > 7) { //row.matches("\\d")
            System.out.println("Please select a number between 1-4.");
            displayBoard(board);
            isValid = false;
        } else //explore absolute value
            if (board[rowDest][columnDest] == '-' && board[rowInput][columnInput] == '@') { // && (Math.abs
                // (columnInput - columnDest) >= 2) removed due to issues- 4 spaces in between @s in columns (ex.
                // column 6 - column 1 = 5 spaces, && (Math.abs(rowInput - rowDest) >= 2) removed as well due to
                // similar issues)
                isValid = true;
                movePeg(columnInput, rowInput,columnDest,rowDest);
            } else {
                System.out.println("Row " + (rowInput +1) + " column " + (columnInput + 1) + " to row " + (rowDest + 1) +
                        " column " + (columnDest +1) + " is currently not a valid move.");

                isValid = false;
                System.out.println("rowDest: " + rowDest);
                System.out.println("columnDest: " + columnDest);
                System.out.println("rowInput: " + rowInput);
                System.out.println("columnInput: " + columnInput);
                displayBoard(board);

            }
    return isValid;
    }

    public char[][] movePeg(int columnInput, int rowInput,int columnDest,int rowDest) {
        if (isValid) {
                board[rowInput][columnInput] = '-';
                board[rowDest][columnDest] = '@';
            System.out.println("rowDest: " + rowDest);
            System.out.println("columnDest: " + columnDest);
            System.out.println("rowInput: " + rowInput);
            System.out.println("columnInput: " + columnInput);

            int middleRow;      //to remove peg in the middle of the skipped one
            int middleColumn;
            boolean isSameRow = false;
            if (rowInput > rowDest) {
                middleRow = rowInput - 1;
            } else if (rowInput == rowDest){
                middleRow = rowInput;
                isSameRow = true;
            } else {
                middleRow=rowDest-1;
            }
            if (isSameRow) {
                if (columnInput > columnDest) {
                    middleColumn = columnInput-2;
                } else if (columnInput == columnDest) {
                    middleColumn = columnDest;
                }else {
                    middleColumn = columnDest-2;
                }
            } else {
            if (columnInput > columnDest) {
                middleColumn = columnInput-1;
            } else if (columnInput == columnDest) {
                middleColumn = columnDest;
            } else {
                middleColumn = columnDest-1;
            }
            }

            board[middleRow][middleColumn] = '-';

//                board[Math.abs(rowInput - (rowDest +1))][Math.abs(columnInput-(columnDest))] = 'X'; //issues with
            // this
                System.out.println("Moving a peg from row " + (rowInput + 1) + ", column " + (columnInput + 1) + " to row " + (rowDest + 1) + ", column " + (columnDest + 1));
                displayBoard(board);
            }

        return board;
    }


    public int countPegsRemaining(char[][] board){
        int count = 0;
        for (int i=0; i < board.length; i++) {
            for (int j = 0; j < board[0].length;j++){
                if (board[i][j] == '@') {
                    count++;
                }
            }
        }
            return count;
        }

        protected void endgame(){
        displayBoard(board);
        if (countPegsRemaining(board) == 1) {
            System.out.println("Congrats you won!");
            System.out.println("Welcome to the GuruGames Center, please enter a command or type help for available commands");
            checkResults = true;
        } else {
            System.out.println("You lose");
            System.out.println("Welcome to the GuruGames Center, please enter a command or type help for available commands");
            checkResults = false;
        }
        }

//        @Override
//        public String help() {
//        return "Enter the number of the column and row that you would like to move. Then, enter the column and row " +
//                "that you would like to move your peg to.";
//        }

    public static void main(String[] args) {
        PegGame game1 = new PegGame();
        game1.displayBoard(game1.board);

    }
}

