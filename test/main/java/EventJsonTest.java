import org.junit.Test;
import Driver.*;
import static org.junit.Assert.*;
/**
 * Created by yanmingwang on 5/10/17.
 */
public class EventJsonTest {

    @Test
    public void eventExist(){
        EventJson testEvent = new EventJson(100.0,"TITLE",12,12,2017,12,30,"DESCRIPTION","VENUE_NAME","ADDRESS","CITY","STATE","ZIPCODE",100.0);
        assertNotNull("should not be null", testEvent);
    }
}
