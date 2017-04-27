package Driver;
import Model.Model;
import TemplateEngine.FreeMarkerEngine;
import java.io.IOException;
import static spark.Spark.*;
import spark.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.StringWriter;
import com.google.gson.*;
import java.util.*;

/**
 * Created by yanmingwang on 3/29/17.
 * This class is a routig for URL that will pass data and render the right HTML temple
 */
public class MainRoute {
    public static void main(String[] args) {
        staticFileLocation("/public");
        MainRoute s = new MainRoute();
        s.init();
    }

    /**
     *  Function for Routes
     *  Each route is a url
     *
     */
    private void init() {
        String[] tempExchange;
        tempExchange = new String[3];
        final Boolean[] preLoad = {false};
        Gson gson = new Gson();
        Model mod = new Model();
        get("/", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("title", "Welcome to ConcertFinder");
            viewObjects.put("templateName", "home.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        post("/", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            String first = request.body();
            String second = first.substring(7, first.length());
            String [] tmp = second.split("\\+");
            String result = "";
            if(tmp.length > 1)
            {
                for(int i = 0; i < tmp.length-1; i++)
                {
                    result += tmp[i] + " ";
                }
                result += tmp[tmp.length-1];
            }else
            {
                result += tmp[0];
            }
            System.out.println(result);
            String[] eventList = mod.em.selectEventByTitle(result);
            mod.em.clearQuery();
            mod.em.createQuery(eventList);
            response.status(204);
            response.type("application/json");
            return 1;
        });

        post("/addEventPre/:lat/:lon/:address", (request, response) -> {
            tempExchange[0] = request.params("lat");
            tempExchange[1] = request.params("lon");
            tempExchange[2] = request.params("address");
            preLoad[0] = true;
            response.status(200);
            return response;
        });

        get("/addEvent", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "addEvent.ftl");
            if(preLoad[0]){
                viewObjects.put("lat",  tempExchange[0]);
                viewObjects.put("lon", tempExchange[1]);
                viewObjects.put("address", tempExchange[2]);
                preLoad[0] = false;
            }
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/search", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showSearch.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        post("/search", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            String first = request.body();
            String second = first.substring(7, first.length());
            String [] tmp = second.split("\\+");
            String result = "";
            if(tmp.length > 1)
            {
                for(int i = 0; i < tmp.length-1; i++)
                {
                    result += tmp[i] + " ";
                }
                result += tmp[tmp.length-1];
            }else
            {
                result += tmp[0];
            }
            System.out.println(result);
            String[] eventList = mod.em.selectEventByTitle(result);
            mod.em.clearQuery();
            mod.em.createQuery(eventList);
            response.status(204);
            response.type("application/json");
            return 1;
        });

        get("/getsearch", (request, response) -> {
            response.status(200);
            String[] tmp = mod.em.getQuery();
            if(tmp[0] != null)
            {
                return toJSON(mod.sendEvents(mod.em.getQuery()));
            }
            return toJSON(mod.sendEvents());
        });

        post("/addEvent", (request, response) -> {
            System.out.println("sendEventData Called");
            String addList=request.queryParams().toString();
            addList = addList.substring(14, addList.length()-1);
            addList = "{"+addList;
            System.out.println(addList);
            EventJson temp = gson.fromJson(addList, EventJson.class);
            System.out.println(temp);
            mod.em.insertEvent(temp.getTITLE(),temp.getMONTH(),temp.getDAY(),temp.getYEAR(),temp.getHOUR(),temp.getMINUTE(),temp.getDESCRIPTION(),temp.getVENUE_NAME(),temp.getADDRESS(),temp.getCITY(),temp.getSTATE(),temp.getZIPCODE(),temp.getLATITUDE(),temp.getLONGITUDE());
            System.out.println("Done!");
            response.status(200);
            return response;
        });


        get("/viewDB", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showEvent.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        get("/getevents", (request, response) -> {
            response.status(200);
            return toJSON(mod.sendEvents());
        });

        get("/getmapjson", (request, response) -> {
            response.status(200);
            Gson ngson = new Gson();
            List<String[]> ret= mod.em.listResults();
            List<String> jsonR =  new ArrayList<>();
            for (String[] aRet : ret) {
                jsonR.add(ngson.toJson(aRet));
            }
            return jsonR;
        });

        get("/map", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();;
            viewObjects.put("templateName", "mapview.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        post("/map", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            String first = request.body();
            String second = first.substring(7, first.length());
            String [] tmp = second.split("\\+");
            String result = "";
            if(tmp.length > 1)
            {
                for(int i = 0; i < tmp.length-1; i++)
                {
                    result += tmp[i] + " ";
                }
                result += tmp[tmp.length-1];
            }else
            {
                result += tmp[0];
            }
            System.out.println(result);
            String[] eventList = mod.em.selectEventByTitle(result);
            mod.em.clearQuery();
            mod.em.createQuery(eventList);
            response.status(204);
            response.type("application/json");
            return 1;
        });

    }

    /**
     *  This function converts an Object to JSON String
     * @param obj
     * Any object
     */
    private static String toJSON(Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, obj);
            return sw.toString();
        }
        catch(IOException e) {
            System.err.println(e);
        }
        return null;
    }

}

class EventJson{
    public String TITLE;
    public int MONTH;
    public int DAY;
    public int YEAR;
    public int HOUR;
    public int MINUTE;
    public String DESCRIPTION;
    public String VENUE_NAME;
    public String ADDRESS;
    public String CITY;
    public String STATE;
    public String ZIPCODE;
    public double LONGITUDE;
    public double LATITUDE;

    public EventJson(){
    }

    public EventJson(double LATITUDE, String TITLE, int MONTH, int DAY, int YEAR, int HOUR, int MINUTE, String DESCRIPTION, String VENUE_NAME, String ADDRESS, String CITY, String STATE, String ZIPCODE, double LONGITUDE) {
        this.LATITUDE = LATITUDE;
        this.TITLE = TITLE;
        this.MONTH = MONTH;
        this.DAY = DAY;
        this.YEAR = YEAR;
        this.HOUR = HOUR;
        this.MINUTE = MINUTE;
        this.DESCRIPTION = DESCRIPTION;
        this.VENUE_NAME = VENUE_NAME;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZIPCODE = ZIPCODE;
        this.LONGITUDE = LONGITUDE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getMONTH() {
        return MONTH;
    }

    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getDAY() {
        return DAY;
    }

    public void setDAY(int DAY) {
        this.DAY = DAY;
    }

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }

    public int getHOUR() {
        return HOUR;
    }

    public void setHOUR(int HOUR) {
        this.HOUR = HOUR;
    }

    public int getMINUTE() {
        return MINUTE;
    }

    public void setMINUTE(int MINUTE) {
        this.MINUTE = MINUTE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getVENUE_NAME() {
        return VENUE_NAME;
    }

    public void setVENUE_NAME(String VENUE_NAME) {
        this.VENUE_NAME = VENUE_NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getZIPCODE() {
        return ZIPCODE;
    }

    public void setZIPCODE(String ZIPCODE) {
        this.ZIPCODE = ZIPCODE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    @Override
    public String toString() {
        return "EventJson{" +
                "TITLE='" + TITLE + '\'' +
                ", MONTH=" + MONTH +
                ", DAY=" + DAY +
                ", YEAR=" + YEAR +
                ", HOUR=" + HOUR +
                ", MINUTE=" + MINUTE +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", VENUE_NAME='" + VENUE_NAME + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", CITY='" + CITY + '\'' +
                ", STATE='" + STATE + '\'' +
                ", ZIPCODE='" + ZIPCODE + '\'' +
                ", LONGITUDE=" + LONGITUDE +
                ", LATITUDE=" + LATITUDE +
                '}';
    }
}