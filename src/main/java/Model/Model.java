package Model;

import Controller.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Model {
    private Map<String, Object> user;
    public Database db;

    /**
     * Constructor
     */
    public Model()
    {

        this.user = new HashMap<>();
        this.db = new Database();
        db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
        db.insertEvent("DRAM at Ithaca College", 3, 21, 2017, 12, 0, "description",
                "Ithaca College", "953 Danby Road", "Ithaca", "NY", "14850", -.25, .55);
        db.insertEvent("Drake at Ithaca College", 3, 21, 2017, 12, 0, "description",
                "Ithaca College", "953 Danby Road", "Ithaca", "NY", "14850", -.25, .55);
    }

    public List sendEvents()
    {
        List<String[]> ret = new ArrayList<>(db.listResults());
        return ret;
    }
}
