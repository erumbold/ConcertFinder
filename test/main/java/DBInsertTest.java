import Controller.Database;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by erikarumbold on 3/27/17.
 */
public class DBInsertTest {
    Database db = new Database();

    @Test
    public void testEvents() {
        db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
        assertEquals("Firefly Music Festival", db.selectEventByTitle("Firefly Music Festival")[1]);             // insert Firefly Music Festival
        db.insertEvent("Pumpkin Chunkin'", 6, 18, 2017, 12, 0, "description", "Field", "Somewhere",
                "Dover", "DE", "19901", -.5, .5);
        assertEquals("Pumpkin Chunkin'", db.selectEventByTitle("Pumpkin Chunkin'")[1]);                   // insert Pumpkin Chunkin'
        db.insertEvent("Coachella", 9, 23, 2017, 12, 0, "description", "Somewhere", "Out there", "Chicago", "IL",
                "12345", -.5, .5);
        assertEquals("Coachella", db.selectEventByTitle("Coachella")[1]);                          // insert Coachella

        db.deleteEvent("Firefly Music Festival");
        db.deleteEvent("Pumpkin Chunkin'");
        db.deleteEvent("Coachella");
    }
}
