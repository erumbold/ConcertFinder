package Model;

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
//        em.insertEvent("Test Festival", 6, 18, 2017, 12, 0, "description",
//                "The Woodlands", "123 Place", "Dover", "DE", "19901", 42.439577, -76.497002);
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
        String[] oc = new String[11];
        for (int i = 0; i < oc.length; i++){
            oc[i] = ev[i];
        }
        ret.add(oc);
        return ret;
    }

}
