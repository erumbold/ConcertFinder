package Driver;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Created by yanmingwang on 3/29/17.
 */
public class MainRoute {
    public static void main(String[] args) {
        MainRoute Instance = new MainRoute();
        Instance.init();
    }

    private void init() {
        get("/", (request, response) -> "Home");
    }

}