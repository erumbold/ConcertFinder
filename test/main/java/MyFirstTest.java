/**
 * Created by kumun_000 on 3/27/2017.
 */

import Model.insertuser;
import org.junit.Test;
import static org.junit.Assert.*;


public class MyFirstTest {
    @Test
    public void insertuserTest() {
        insertuser iu= new insertuser();
        String result= iu.insertuser("Kizito Umunakwe", "kizisaurus", "kizito23",22,"umunakwec@yahoo.com");
        assertEquals("Kizito Umunakwe", result);

        result=iu.insertuser("Alex Mercer", "Merc","Alex345",20,"AlecMerc@yahoo.com" );
        assertEquals("Alex Mercer", result);
    }


}
