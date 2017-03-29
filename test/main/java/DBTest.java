import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by erikarumbold on 3/27/17.
 */
public class DBTest {
    @Test
    public void testEventInsert(){
        Database db = new Database();
        String result = db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
        assertEquals("Firefly Music Festival", result);
    }
}
