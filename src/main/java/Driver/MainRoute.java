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

        get("/addEvent", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("templateName", "addEvent.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

        post("/addEvent", (request, response) -> {
            ObjectMapper mapper = new ObjectMapper();
            String first = request.body();
            String [] second = first.split(",");
            ArrayList<String> third = new ArrayList<>();
            String [] values = new String[14];
            for(int i = 0; i < second.length; i++)
            {
                String [] tmp = second[i].split(":");
                String result = tmp[1];
                if(i == second.length-1)
                {
                    result = result.substring(0, (result.length()-1));
                }
                result = result.substring(1, (result.length()-1));
                values[i] = result;
                System.out.println(values[i]);
            }
            mod.em.insertEvent(values[0],Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]),
                    Integer.parseInt(values[4]), Integer.parseInt(values[5]), values[6], values[7], values[8], values[9],
                    values[10], values[11], Double.parseDouble(values[12]), Double.parseDouble(values[13]));
            response.status(200);
            response.type("application/json");
            return 1;
        });

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
            Gson gson = new Gson();
            List<String[]> ret= mod.em.listResults();
            List<String> jsonR =  new ArrayList<>();
            for (String[] aRet : ret) {
                jsonR.add(gson.toJson(aRet));
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