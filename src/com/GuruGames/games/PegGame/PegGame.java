package com.GuruGames.games.PegGame;

public class PegGame {
    char[][] board =
                     {{' ', ' ', ' ', '-', ' ', ' ', ' '},
                    {' ', ' ', '@', ' ', '@', ' ', ' '},
                    {' ', '@', ' ', '@', ' ', '@', ' '},
                    {'@', ' ', '@', ' ', '@', ' ', '@'}};
    boolean isValid;
//    String column;
//    String row;
//    String destinationColumn;
//    String destinationRow;
//    int columnInput = Integer.parseInt(column) - 1;
//    int rowInput = Integer.parseInt(row) - 1;
//    int columnDest = Integer.parseInt(destinationColumn) - 1;
//    int rowDest = Integer.parseInt(destinationRow) - 1;

    //    Boolean[][] board = {{null, null, null, false, null, null, null}, {null, null, true, null, true, null, null}, {null, true, null, true, null, true, null}, {true, null, true, null, true, null, true}};
//
//
//
//    public void newBoard(){
//        int j=0;
//        for(var a:board){
//            for(int i=0; i<a.length; i++){
//                if(a[i]==null){
//                    System.out.print(" ");
//                }else{
//                    System.out.print(j);
//                    j++;
//                }
//            }
//            System.out.println();
//        }
//        for (var a : board) {
//            for (var b : a) {
//                if (b == null) {
//                    System.out.print(" ");
//                } else if (b) {
//                    System.out.print("@");
//                } else {
//                    System.out.print("-");
//                }
//            }
//            System.out.println();
//        }
//    }
//   public static char[][] createBoard() {
//        board =
//                {{'*','*','*','-','*','*', '*'},
//                {'*','*','@','*','@','*', '*'},
//                {'*','@','*','@','*','@', '*'},
//                {'@','*','@','*','@', '*','@'}};
//                {'@','@','@','@', '@', '@', '@','@','@'},
//                {'*','*','*','*','@','*', '*', '*','*'},
//                {'*','*','*','*','@','*', '*', '*','*'}};
//
//        return board; }


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



    //parseCommand
// get two strings and check if they are valid
    public  boolean isValidPiece(String column, String row, String destinationColumn, String destinationRow) throws IllegalArgumentException {
        //trying to move to vs destination that they're actually moving to
        int columnInput = Integer.parseInt(column) - 1;
        int rowInput = Integer.parseInt(row) - 1;
        int columnDest = Integer.parseInt(destinationColumn) - 1;
        int rowDest = Integer.parseInt(destinationRow) - 1;

        System.out.println(columnInput + " " + rowInput + " " + columnDest + " " + rowDest);

        if (columnInput < 0 || columnInput > 7 ) { //!Character.isDigit(columnInput)
            System.out.println("Please select a number between 1-7.");
            isValid = false;
        } else if (rowInput < 0 || rowInput > 7) { //row.matches("\\d")
            System.out.println("Please select a number between 1-4.");
            isValid = false;
        } else //explore absolute value
            if (board[rowDest][columnDest] == '-' && board[rowInput][columnInput] == '@' && (Math.abs(rowInput - rowDest) >= 2)
                    && (Math.abs(columnInput - columnDest) >= 2)) {

                isValid = true;
                movePeg(columnInput, rowInput,columnDest,rowDest);
            } else {
                System.out.println(rowInput + " to " + rowDest + " and " + columnInput + " to " + columnDest + " is " +
                        "currently not a valid move.");
                isValid = false;
            }
    return isValid;
    }

public char[][] movePeg(int columnInput, int rowInput,int columnDest,int rowDest) {
        if (isValid) {
                board[rowInput][columnInput] = '-';
                board[rowDest][columnDest] = '@';
                //figure out how to remove after jumping
                board[Math.abs(rowInput - rowDest)-1][Math.abs(columnInput-columnDest)] = '-';
            }
        System.out.println("Moving a peg to row " + rowDest + ", column " + columnDest);
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

        public void winner(){
        displayBoard(board);
        if (countPegsRemaining(board) == 1) {
            System.out.println("Congrats you won!");
        }
        }

//        Scanner in = new Scanner(System.in);
//        String columnInput = in.next();
//        column = Integer.parseInt(columnInput) - 1; //takes String input and parses it into an int, -1 since
//        // array starts from 0
//
//        while (column < 0 || column > 6) {
//            System.out.println("Please select a valid number between 1-7.");
//            columnInput = in.next();
//            column = Integer.parseInt(columnInput) - 1;
//        }
//        System.out.println("Chosen column: " + column);
//
//        System.out.println("Choose the row of a peg you'd like to move: ");
//        String rowInput = in.next();
//        row = Integer.parseInt(rowInput) - 1;
//
//        while (row < 0 || row > 3) {
//            System.out.println("Please select a valid number between 1-4.");
//            rowInput = in.next();
//            row = Integer.parseInt(rowInput) - 1;
//        }
//        System.out.println("Chosen row: " + row);
//
//        return isValidMove(column, row);
//    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }

    public static void main(String[] args) {
        PegGame game1 = new PegGame();
        game1.displayBoard(game1.board);

        PegGame game2 = new PegGame();
        game2.displayBoard(game2.board);


    }


}

