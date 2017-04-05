package Driver;

import Model.Model;
import TemplateEngine.FreeMarkerEngine;
import java.io.IOException;
import static spark.Spark.*;
import spark.ModelAndView;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

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
