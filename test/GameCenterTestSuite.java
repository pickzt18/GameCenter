import com.GuruGames.GameCenter.GameCenter;
import org.junit.*;
import static org.junit.Assert.*;

public class GameCenterTestSuite {
    GameCenter test = GameCenter.getInstance();
    @Test
    public void testSuccessfulPerm(){
        test.parseCommand("game", "fakegame");
        test.parseCommand("start");
    }
    @Test
    public void testGetAllGames(){
        for(String s : GameCenter.getAllGames()){
            System.out.println(s);
        }
    }
    @Test(expected = IllegalArgumentException.class)
    public void testParseCommandTooManyParam(){
        test.parseCommand("game", "every", "single", "game", "you", "have");
    }
}
