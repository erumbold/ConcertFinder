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
     *  Each route is a url that can be reached
     */
    private void init() {
        String[] tempExchange;
        tempExchange = new String[3];
        final Boolean[] preLoad = {false};
        Gson gson = new Gson();
        Model mod = new Model();
        /**
         * return main page template
         */
        get("/", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("title", "Welcome to ConcertFinder");
            viewObjects.put("templateName", "home.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        /**
         * get search query
         */
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
        /**
         * get coordinate from google map api and store it
         */
        post("/addEventPre/:lat/:lon/:address", (request, response) -> {
            tempExchange[0] = request.params("lat");
            tempExchange[1] = request.params("lon");
            tempExchange[2] = request.params("address");
            preLoad[0] = true;
            response.status(200);
            return response;
        });
        /**
         * return an addevent page with coordinate pre-fill
         */
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
        /**
         * return search page
         */
        get("/search", (request, response) -> {
            response.status(200);
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "showSearch.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        /**
         * get search query
         */
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
        /**
         * get search result
         */
        get("/getsearch", (request, response) -> {
            response.status(200);
            String[] tmp = mod.em.getQuery();
            if(tmp[0] != null)
            {
                return toJSON(mod.sendEvents(mod.em.getQuery()));
            }
            return toJSON(mod.sendEvents());
        });
        /**
         * get event info as json and create an event obj
         */
        post("/addEvent", (request, response) -> {
            String addList=request.queryParams().toString();
            addList = addList.substring(2, addList.length()-1);
            addList = "{"+addList;
            EventJson temp = gson.fromJson(addList, EventJson.class);
            mod.em.insertEvent(temp.getTITLE(),temp.getMONTH(),temp.getDAY(),temp.getYEAR(),temp.getHOUR(),temp.getMINUTE(),temp.getDESCRIPTION(),temp.getVENUE_NAME(),temp.getADDRESS(),temp.getCITY(),temp.getSTATE(),temp.getZIPCODE(),temp.getLATITUDE(),temp.getLONGITUDE());
            response.status(200);
            return response;
        });
        /**
         * return event json
         */
        get("/getevents", (request, response) -> {
            response.status(200);
            return toJSON(mod.sendEvents());
        });
        /**
         * return list of event as json for map
         */
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
        /**
         * return map page
         */
        get("/map", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();;
            viewObjects.put("templateName", "mapview.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());
        /**
         * get search query
         */
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
