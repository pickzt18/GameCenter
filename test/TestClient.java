import com.GuruGames.GameCenter.GameCenter;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

/**
 * Run game with only access to GameCenter
 */
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
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }
}
