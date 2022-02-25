public class Driver {
    public static void main(String[] args) {
/*        String[][] board = {{" ", " ", " ", "@", " ", " ", " "}, {" ", " ", "@", " ", "@", " ", " "}, {" ", "@", " ", "@", " ", "@", " "}, {"@", " ", "@", " ", "@", " ", "@"}};
        for(var a:board){
            for(var b:a){
                System.out.print(b);
            }
            System.out.println();
        }
    }*/
        Boolean[][] board = {{null, null, null, false, null, null, null}, {null, null, true, null, true, null, null}, {null, true, null, true, null, true, null}, {true, null, true, null, true, null, true}};
        int j=0;
        for(var a:board){
            for(int i=0; i<a.length; i++){
                if(a[i]==null){
                    System.out.print(" ");
                }else{
                    System.out.print(j);
                    j++;
                }
            }
            System.out.println();
        }
        for (var a : board) {
            for (var b : a) {
                if (b == null) {
                    System.out.print(" ");
                } else if (b) {
                    System.out.print("@");
                } else {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
}
/*
    0        |    @    |
   1 2       |   @ @   |
  3 4 5      |  @ @ @  |
 6 7 8 9     | @ @ - @ |
1 1 1 1 1    |@ @ @ @ @|
 */