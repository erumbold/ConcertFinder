package Model;

import Controller.Database;
import Controller.EventManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private Map<String, Object> user;
    public EventManager em;

    /**
     * Constructor
     */
    public Model() {

        this.user = new HashMap<>();
        this.em = new EventManager();
        em.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", 39.1900301, -75.5311199);
        em.insertEvent("DRAM at Ithaca College", 3, 21, 2017, 12, 0, "description",
                "Ithaca College", "953 Danby Road", "Ithaca", "NY", "14850", 42.4217372, -76.4969761);
        em.insertEvent("Drake at Ithaca College", 3, 21, 2017, 12, 0, "description",
                "Ithaca College", "953 Danby Road", "Ithaca", "NY", "14850", 42.4217372, -76.4969761);
    }

    //0 -- List all Events
    //1 -- List search results
    public List sendEvents()
    {
        List<String[]> ret;
        ret = new ArrayList<>(em.listResults());
        return ret;

    }

    public List sendEvents(String[] ev)
    {
        List<String[]> ret = new ArrayList<>();
        ret.add(ev);
        return ret;
    }



}
