import com.GuruGames.GameCenter.GameCenter;

import java.util.Scanner;

public class TestClient {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        GameCenter gc = GameCenter.getInstance(in);
        String scannerInput;
        while(true){
            try {
                scannerInput = in.nextLine();
                gc.parseCommand(scannerInput);
            } catch (IllegalArgumentException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
