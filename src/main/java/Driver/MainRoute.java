package Driver;
import Model.Model;
import TemplateEngine.FreeMarkerEngine;
import java.io.IOException;
import static spark.Spark.*;
import spark.ModelAndView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * Created by yanmingwang on 3/29/17.
 */
public class MainRoute {
    public static void main(String[] args) {
        staticFileLocation("/public");
        MainRoute s = new MainRoute();
        s.init();
    }

    /**
     *  Function for Routes
     */
    private void init() {
        Model mod = new Model();

        get("/", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
            viewObjects.put("title", "Welcome to Spark Project");
            viewObjects.put("templateName", "home.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

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
        get("/getJson", (request, response) -> {
            response.status(200);
            return mod.sendEvents();
        });

        get("/map", (request, response) -> {
            Map<String, Object> viewObjects = new HashMap<String, Object>();
//            viewObjects.put("title", "Map");
            viewObjects.put("templateName", "mapview.ftl");
            return new ModelAndView(viewObjects, "main.ftl");
        }, new FreeMarkerEngine());

    }

    /**
     *  This function converts an Object to JSON String
     * @param obj
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