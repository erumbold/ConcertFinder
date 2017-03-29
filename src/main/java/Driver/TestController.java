package Driver;
import spark.Request;
import spark.Response;

import static spark.Spark.get;

/**
 * Created by yanmingwang on 3/29/17.
 */
public class TestController {

    public TestController() {
        get("/test", (request, response) ->  this.testMethod(request, response));
    }

    public String testMethod(Request request, Response response) {
        return "This works!";
    }

}
