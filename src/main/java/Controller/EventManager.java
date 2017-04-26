package Controller;
import Controller.Database;
import Model.Search;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erikarumbold on 3/27/17.
 */
public class EventManager {
    private Database db;

    /**
     * This a constructor for EventManager that create a database obj
     */
    public EventManager(){
        this.db = new Database();
    }

    /**
     * Insert Event use database controller.
     *
     * @param title Event title
     * @param month Event month
     * @param day Event day
     * @param year Event year
     * @param hour Event hour
     * @param minute Event minute
     * @param description Event description
     * @param venue Event venue
     * @param address Event address
     * @param city Event city
     * @param state Event state
     * @param zipCode Event zipCode
     * @param latitude Event latitude
     * @param longitude Event longitude
     */
    public void insertEvent(String title, int month, int day, int year, int hour, int minute, String description,
                            String venue, String address, String city, String state, String zipCode, double latitude, double longitude){
        db.insertEvent(title, month, day, year, hour, minute, description, venue, address, city, state, zipCode, latitude, longitude);
    }

    /**
     *This return event
     * @return give back a list of events (all events)
     */
    public List listResults(){
        List<String[]> ret = db.listResults();
        return ret;
    }

    public String[] selectEventByTitle(String title)
    {
        String[] tmp = db.selectEventByTitle(title);
        return tmp;
    }

    /**
     * This is a database test function
      * @param args
     */
public static void main(String[] args) {
        Database db = new Database();
        db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);

        Search mySearch = new Search();
//        mySearch.findEvent();
    }
}
