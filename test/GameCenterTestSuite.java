import com.GuruGames.GameCenter.GameCenter;
import org.junit.*;

import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class GameCenterTestSuite {
    GameCenter test = GameCenter.getInstance();
    @Test
    public void testSuccessfulPerm(){
        try {
            test.parseCommand("game", "fakegame");
            test.parseCommand("start");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void testGetAllGames(){
        for(String s : GameCenter.getAllGames()){
            System.out.println(s);
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void testParseCommandTooManyParam(){
        try{
            test.parseCommand("game", "every", "single", "game", "you", "have");
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
}
