import Driver.MainRoute;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import static org.junit.Assert.*;

/**
 * Created by yanmingwang on 3/27/17.
 */
public class MainRouteReturnTest {
        public static class TestContollerTestSparkApplication implements SparkApplication {
                @Override
                public void init() {
                        new MainRoute();
                }
        }

        @ClassRule
        public static SparkServer<TestContollerTestSparkApplication> testServer = new SparkServer<>(MainRouteReturnTest.TestContollerTestSparkApplication.class, 4567);

//        @Test
//        public void getMapJson() throws Exception {
//		/* The second parameter indicates whether redirects must be followed or not */
//                GetMethod get = testServer.get("/getMapJson", false);
////                get.addHeader("Test-Header", "test");
//                HttpResponse httpResponse = testServer.execute(get);
////                assertEquals(200, httpResponse.code());
////                assertNotNull(testServer.getApplication());
//                //
////                assertEquals("This works!", new String(httpResponse.body()));
//
//
//        }

//        @Test
//        public void addEventTest() throws Exception {
//		/* The second parameter indicates whether redirects must be followed or not */
//                GetMethod get = testServer.post("/addEvent", [","]);
////                get.addHeader("Test-Header", "test");
//                HttpResponse httpResponse = testServer.execute(get);
////                assertEquals(200, httpResponse.code());
////                assertNotNull(testServer.getApplication());
//                //
////                assertEquals("This works!", new String(httpResponse.body()));
//
//
//        }
}
