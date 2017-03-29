import Driver.MainRoute;
import Driver.TestController;
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
public class HtmlTest {
        public static class TestContollerTestSparkApplication implements SparkApplication {
                @Override
                public void init() {
                        new TestController();
                }
        }

        @ClassRule
        public static SparkServer<TestContollerTestSparkApplication> testServer = new SparkServer<>(HtmlTest.TestContollerTestSparkApplication.class, 4567);

        @Test
        public void test() throws Exception {
		/* The second parameter indicates whether redirects must be followed or not */
                GetMethod get = testServer.get("/test", false);
                get.addHeader("Test-Header", "test");
                HttpResponse httpResponse = testServer.execute(get);
                assertEquals(200, httpResponse.code());
                assertEquals("This works!", new String(httpResponse.body()));
                assertNotNull(testServer.getApplication());
        }
}
