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
    private String[] currentQuery = new String[14];

    public EventManager(){
        this.db = new Database();
    }

    public void insertEvent(String title, int month, int day, int year, int hour, int minute, String description,
                            String venue, String address, String city, String state, String zipCode, double latitude, double longitude){
        db.insertEvent(title, month, day, year, hour, minute, description, venue, address, city, state, zipCode, latitude, longitude);
    }

    public String[] selectEventByTitle(String title)
    {
        return db.selectEventByTitle(title);
    }

    public void createQuery(String[] result)
    {
        for(int i = 0; i < currentQuery.length; i++)
        {
            currentQuery[i] = result[i+1];
        }
    }

    public void clearQuery()
    {
        for(int i = 0; i < currentQuery.length; i++)
        {
            currentQuery[i] = null;
        }
    }
    public String[] getQuery()
    {
        return currentQuery;
    }
    public List listResults(){
        List<String[]> ret = db.listResults();
        return ret;
    }

public static void main(String[] args) {
        Database db = new Database();
        String result = db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
        System.out.println(result);

        Search mySearch = new Search();
//        mySearch.findEvent();
    }
}
