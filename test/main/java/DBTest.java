import Controller.Database;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by erikarumbold on 3/27/17.
 */
public class DBTest {
//    Database db = new Database();
//    @Test
//    public void testEventInsert(){
//        String result = db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
//                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
//        assertEquals("Firefly Music Festival", result);             // insert Firefly Music Festival
//        result = db.insertEvent("Pumpkin Chunkin'", 6, 18, 2017, 12, 0, "description", "Field", "Somewhere",
//                "Dover", "DE", "19901", -.5, .5);
//        assertEquals("Pumpkin Chunkin'", result);                   // insert Pumpkin Chunkin'
//        result = db.insertEvent("Coachella",9,23,2017,12,0,"description", "Somewhere", "Out there", "Chicago", "IL",
//                "12345", -.5, .5);
//        assertEquals("Coachella", result);                          // insert Coachella
//        result = db.insertEvent("Cayuga Sound", 9,23,2017, 12, 0, "description", ".", "Stewart Park", "Ithaca",
//                "NY", "14850", -.5, .5);
//        assertEquals("Cayuga Sound", result);                       // insert Cayuga sound
//    }
//
//    @Test
//    public void testEventSearch(){
//        String[] ret = {"Firefly Music Festival", "6", "18", "2017", "12", "0", "description","The Woodlands",
//                "123 Place", "Dover", "DE", "19901", "-0.5", "0.5"};
//        String[] ret2 = {"Pumpkin Chunkin'", "6", "18", "2017", "12", "0", "description", "Field", "Somewhere",
//                "Dover", "DE", "19901", "-0.5", "0.5"};
//        ArrayList<String[]> expectDelaware = new ArrayList<>();
//        expectDelaware.add(0, ret);
//        expectDelaware.add(1, ret2);
//
//        ArrayList<String[]> result = db.selectEventByCityState("Dover", "DE");      // select shows in Dover, DE
//        assertEquals(expectDelaware.size(), result.size());
//        for (int i = 0; i < result.size(); i++) {
//            assertArrayEquals(expectDelaware.get(i), result.get(i));
//        }
//
//        result = db.selectEventByZipCode("19901");                                  // select shows in zip code 19901
//        assertEquals(expectDelaware.size(), result.size());
//        for (int i = 0; i < result.size(); i++) {
//            assertArrayEquals(expectDelaware.get(i), result.get(i));
//        }
//
//        ArrayList<String[]> expectCoachella = new ArrayList<>();                    // select title Coachella
//        String[] ret3 = {"Coachella", "9","23","2017","12","0","description", "Somewhere", "Out there", "Chicago", "IL",
//                "12345", "-0.5", "0.5"};
//        expectCoachella.add(0,ret3);
//        result = db.selectEventByTitle("Coachella");
//        assertEquals(expectCoachella.size(), result.size());
//        for (int i = 0; i < result.size(); i++){
//            assertArrayEquals(expectCoachella.get(i), result.get(i));
//        }
//
//        result = db.selectEventByDate(6, 18, 2017);                 // select events on 6/18/17
//        assertEquals(expectDelaware.size(), result.size());
//        for (int i = 0; i < result.size(); i++){
//            assertArrayEquals(expectDelaware.get(i), result.get(i));
//        }
//
//        ArrayList<String[]> expectC = new ArrayList<>();            // create new expected result set
//        String[] r = {"Coachella", "9","23","2017","12","0","description", "Somewhere", "Out there", "Chicago", "IL",
//                "12345", "-0.5", "0.5"};
//        String[] s = {"Cayuga Sound", "9","23","2017", "12", "0", "description", ".", "Stewart Park", "Ithaca",
//                "NY", "14850","-0.5", "0.5"};
//        expectC.add(0, r);
//        expectC.add(1, s);
//
//        result = db.selectEventByMonth(6);                          // select events in June
//        assertEquals(expectDelaware.size(), result.size());
//        for (int i = 0; i < result.size(); i++){
//            assertArrayEquals(expectDelaware.get(i), result.get(i));
//        }
//        result = db.selectEventByMonth(9);                          // select events in September
//        assertEquals(expectC.size(), result.size());
//        for (int i = 0; i < result.size(); i++){
//            assertArrayEquals(expectC.get(i), result.get(i));
//        }
//
//        ArrayList<String[]> expectAll = new ArrayList<>();
//        expectAll.add(0, ret);
//        expectAll.add(1, ret2);
//        expectAll.add(2, r);
//        expectAll.add(3, s);
//        result = db.selectEventByYear(2017);
//        assertEquals(expectAll.size(), result.size());              // select events in 2017
//        for (int i = 0; i < result.size(); i++) {
//            assertArrayEquals(expectAll.get(i), result.get(i));
//        }
//    }
}
