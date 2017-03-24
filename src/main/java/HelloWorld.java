import static spark.Spark.*;

/**
 * Created by erikarumbold on 3/21/17.
 */
public class HelloWorld {
    public static void main(String[] args) {get("/hello", (req, res) -> "Hello World");}

}


