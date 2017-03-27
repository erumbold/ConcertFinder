import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by erikarumbold on 3/27/17.
 */
public class DBTest {
    @Test
    public void testEventInsert(){
        EventManager em = new EventManager();
        String result = em.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "Dover", "DE", 19901, -.5, .5);
        assertEquals("Firefly Music Festival", result);

        result = em.insertEvent("Woodstock", 8, 15, 1969, 12, 0, "description", "Catskills", "White Lake", "NY", 12345,
                -.5, .5);
        assertEquals("Woodstock", result);
    }
}
