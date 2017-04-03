package Model; /**
 * Created by Seth on 3/22/2017.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import Controller.Database;


public class Search{
    public Database db = new Database();
    public String parameter;
    public String [] events;

    public Search()
    {
        String result = db.insertEvent("Firefly Music Festival", 6, 18, 2017, 12, 0, "description",
                "The Woodlands", "123 Place", "Dover", "DE", "19901", -.5, .5);
        String result2 = db.insertEvent("Dram at Ithaca College", 4, 25, 2017, 11, 0, "description",
            "A and E Center", "953 Danby", "Ithaca", "NY", "27501", -.5, .5);
    }

    public boolean findEvent()
    {
        System.out.println("Enter the name of an event you're looking for:");
        System.out.println();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<String[]> result;
        try {
            parameter = br.readLine();
            result = db.selectEventByTitle(parameter);
            events = result.get(0);

            if(result.size() > 0)
            {
                System.out.print("Found:  ");
                for(int i = 0; i < events.length; i++)
                {
                    if(i < (events.length - 1))
                    {
                        System.out.print(events[i] + ", ");
                    }else
                    {
                        System.out.print(events[i]);
                    }
                }
                System.out.println();
                return true;
            }else
            {
                System.out.println("Event does not exist.");
                return false;
            }

        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

    }
}