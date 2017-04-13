package Controller;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Erika Rumbold
 */
public class Database {
    private Connection c = null;
    private Statement st = null;

    public Database(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ConcertFinder2.db");
            c.setAutoCommit(false);
            c.commit();

            st = c.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS USER (ID INT PRIMARY KEY NOT NULL, USERNAME CHAR(20) NOT NULL UNIQUE, " +
                    "PASSWORD CHAR(32) NOT NULL, EMAIL CHAR(32) NOT NULL UNIQUE, IS_ADMIN INT NOT NULL, IS_MUSICIAN INT NOT NULL);";
            st.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS MUSICIANDETAIL (ID INT PRIMARY KEY NOT NULL, USER_ID INT NOT NULL UNIQUE, " +
                    "DESCRIPTION CHAR(400), NAME CHAR(24) NOT NULL, CITY CHAR(20), STATE CHAR(2), TWITTER CHAR(64) UNIQUE, " +
                    "FACEBOOK CHAR(64) UNIQUE, SOUNDCLOUD CHAR(64) UNIQUE);";
            st.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS USERDETAIL (ID INT PRIMARY KEY NOT NULL, USER_ID INT NOT NULL UNIQUE, " +
                    "FIRST_NAME CHAR(20) NOT NULL, LAST_NAME CHAR(20), CITY CHAR(20), STATE CHAR(2));";
            st.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS EVENT (ID INT PRIMARY KEY NOT NULL, TITLE CHAR (32) NOT NULL UNIQUE, MONTH INT, " +
                    "DAY INT, YEAR INT, HOUR INT, MINUTE INT, DESCRIPTION CHAR(400), VENUE_NAME CHAR(20), " +
                    "ADDRESS CHAR(40), CITY CHAR(20), STATE CHAR(2), ZIPCODE CHAR(5), LATITUDE REAL, LONGITUDE REAL);";
            st.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS USER_TO_EVENT (ID INT PRIMARY KEY NOT NULL, " +
                    "USER_ID INT NOT NULL, EVENT_ID INT NOT NULL, IS_OWNER INT NOT NULL);";
            st.execute(sql);

        } catch (Exception e){
            System.out.println(e);
        }
    }

    /**
     * This function gives the number of rows in a table
     * @param table - name of table of which to count the number of rows
     * @return count - number of rows in table
     */
    public int getNumRows(String table){
        int count = 0;
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM "+table);
            ResultSet rs = st.executeQuery();
            while (rs.next()){
                count++;
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return count;
    }

    /**
     * inserts a new user into the database if the username, password, or email don't already exist
     * @param username
     * @param password
     * @param email
     * @param isAdmin
     * @param isMusician
     * @return String username
     * @post new row in USER table in connected database
     */
    public String insertUser(String username, String password, String email, int isAdmin, int isMusician){
        try {
            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO USER (ID, USERNAME, PASSWORD, EMAIL, " +
                    "IS_ADMIN, IS_MUSICIAN) VALUES (?,?,?,?,?,?)");
            st.setInt(1, getNumRows("USER")+1);
            st.setString(2, username);
            st.setString(3, password);
            st.setString(4, email);
            st.setInt(5, isAdmin);
            st.setInt(6, isMusician);
            st.executeUpdate();
            c.commit();
            return username;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * updates the attribute values of a given user, cannot change user type (normal, admin, musician)
     * @param username
     * @param password
     * @param email
     * @return String username
     * @post values for given row in USER table of connected database are changed
     */
    public String modifyUser(String username, String password, String email){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE USER SET USERNAME=?, PASSWORD=?, EMAIL=?");
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, email);
            return username;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * removes a username from the database
     * @param username
     * @return String username
     * @post row removed from USER table in connected database
     */
    public String deleteUser(String username){
        try {
            String[] userInfo = selectUserByUsername(username);
            PreparedStatement st = c.prepareStatement("DELETE FROM USER WHERE USERNAME="+username);
            st.executeUpdate();
            c.commit();
            if (userInfo[5].equals("0")) {
                deleteUserDetail(Integer.parseInt(userInfo[0]));
            } else {
                deleteMusicianDetail(Integer.parseInt(userInfo[0]));
            }
            return username;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * finds all user information associated with a given username
     * @param username
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByUsername(String username){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE USERNAME=?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
        } catch (Exception e){
            System.out.println(e);
        }
        return results.get(0);
    }

    /**
     * finds all user information associated with a given email
     * @param email
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByEmail(String email){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE EMAIL=?");
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
        } catch (Exception e){
            System.out.println(e);
        }
        return results.get(0);
    }

    /**
     * finds all user information associated with a given ID
     * @param id
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByID(int id){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE ID=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
        } catch (Exception e){
            System.out.println(e);
        }
        return results.get(0);
    }

    /**
     * finds the username of a given event's owner
     * @param event
     * @return owner's username as a String
     */
    public String selectMusicianByEvent(String event){
        String owner = "";
        try {
            int eventID = selectEventIDbyTitle(event);
            PreparedStatement st = c.prepareStatement("SELECT USER_ID FROM USER_TO_EVENT WHERE EVENT_ID=? AND IS_OWNER=1");
            st.setString(1, Integer.toString(eventID));
            ResultSet rs = st.executeQuery();
            int ownerID = rs.getInt("USER_ID");
            owner = selectUserByID(ownerID)[1];
        } catch (Exception e){
            System.out.println(e);
        }
        return owner;
    }

    /**
     * finds all users that are admins
     * @return ArrayList<[ID, username, password, email, admin status, musician status]> for each user
     */
    public ArrayList<String[]> selectUserAdmins(){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE IS_ADMIN=1");
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds all users that are musicians
     * @return ArrayList<[ID, username, password, email, admin status, musician status]> for each user
     */
    public ArrayList<String[]> selectUserMusicians(){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE IS_MUSICIAN=1");
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

    /**
     * inserts extended user information, specific for general users, into the database
     * @param userID
     * @param firstName
     * @param lastName
     * @param city
     * @param state
     * @return int the associated userID
     * @post new row in USER_DETAIL table of connected database
     */
    public int insertUserDetail(int userID, String firstName, String lastName, String city, String state){
        try {
            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO USERDETAIL (ID, USER_ID, FIRST_NAME, " +
                    "LAST_NAME, CITY, STATE) VALUES (?,?,?,?,?,?)");
            st.setInt(1, getNumRows("USERDETAIL")+1);
            st.setInt(2, userID);
            st.setString(3, firstName);
            st.setString(4, lastName);
            st.setString(5, city);
            st.setString(6, state);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * updates the attribute values of a user's extended information
     * @param userID
     * @param firstName
     * @param lastName
     * @param city
     * @param state
     * @return the associated userID
     */
    public int modifyUserDetail(int userID, String firstName, String lastName, String city, String state){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE USERDETAIL SET USER_ID=?, FIRST_NAME=?, LAST_NAME=?, " +
                    "CITY=?, STATE=?");
            st.setInt(1, userID);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setString(4, city);
            st.setString(5, state);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * removes a user's extended information from the database
     * @param userID
     * @return the associated userID
     */
    public int deleteUserDetail(int userID){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM USERDETAIL WHERE USER_ID="+userID);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * inserts extended musician information, specific for musician users, in the database
     * @param userID
     * @param name
     * @param description
     * @param city
     * @param state
     * @param twitter
     * @param facebook
     * @param soundcloud
     * @return int userID
     */
    public int insertMusicianDetail(int userID, String name, String description, String city, String state,
                                    String twitter, String facebook, String soundcloud){
        try {
            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO MUSICIANDETAIL (ID, USER_ID, NAME, " +
                    "DESCRIPTION, CITY, STATE, TWITTER, FACEBOOK, SOUNDCLOUD) VALUES (?,?,?,?,?,?,?,?,?)");
            st.setInt(1, getNumRows("MUSICIANDETAIL")+1);
            st.setInt(2, userID);
            st.setString(3, name);
            st.setString(4, description);
            st.setString(5, city);
            st.setString(6, state);
            st.setString(7, twitter);
            st.setString(8, facebook);
            st.setString(9, soundcloud);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * updates the attribute values of a musician's extended information
     * @param userID
     * @param name
     * @param description
     * @param city
     * @param state
     * @param twitter
     * @param facebook
     * @param soundcloud
     * @return int userID
     */
    public int modifyMusicianDetail(int userID, String name, String description, String city, String state,
                                    String twitter, String facebook, String soundcloud){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE MUSICANDETAIL SET USER_ID=?, NAME=?, DESCRIPTION=?, " +
                    "CITY=?, STATE=?, TWITTER=?, FACEBOOK=?, SOUNDCLOUD=?");
            st.setInt(1, userID);
            st.setString(2, name);
            st.setString(3, description);
            st.setString(4, city);
            st.setString(5, state);
            st.setString(6, twitter);
            st.setString(7, facebook);
            st.setString(8, soundcloud);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * removes a musician's extended information from the database
     * @param userID
     * @return int userID
     */
    public int deleteMusicianDetail(int userID){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM MUSICIANDETAIL WHERE USER_ID="+userID);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * inserts a new event into the database if the title of the event is not already in the database
     * @param title
     * @param month
     * @param day
     * @param year
     * @param hour
     * @param minute
     * @param description
     * @param venueName
     * @param address
     * @param city
     * @param state
     * @param zipCode
     * @param longitude
     * @param latitude
     * @return String title
     */
    public String insertEvent(String title, int month, int day, int year, int hour, int minute,
                              String description, String venueName, String address, String city,
                              String state, String zipCode, double longitude, double latitude){

        try {

            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO EVENT (ID, TITLE, MONTH, DAY, YEAR, HOUR, " +
                    "MINUTE, DESCRIPTION, VENUE_NAME, ADDRESS, CITY, STATE, ZIPCODE, LONGITUDE, LATITUDE) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            st.setInt(1, getNumRows("EVENT")+1);
            st.setString(2, title);
            st.setInt(3, month);
            st.setInt(4, day);
            st.setInt(5, year);
            st.setInt(6, hour);
            st.setInt(7, minute);
            st.setString(8, description);
            st.setString(9, venueName);
            st.setString(10, address);
            st.setString(11, city);
            st.setString(12, state);
            st.setString(13, zipCode);
            st.setDouble(14, longitude);
            st.setDouble(15, latitude);
            st.executeUpdate();
            c.commit();
            return title;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * updates the attribute values of a given event
     * @param title
     * @param month
     * @param day
     * @param year
     * @param hour
     * @param minute
     * @param description
     * @param venueName
     * @param address
     * @param city
     * @param state
     * @param zipCode
     * @param longitude
     * @param latitude
     * @return String title
     */
    public String modifyEvent(String title, int month, int day, int year, int hour, int minute,
                              String description, String venueName, String address, String city,
                              String state, String zipCode, double longitude, double latitude){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE EVENT SET TITLE=?, MONTH=?, DAY=?, YEAR=?, HOUR=?, " +
                    "MINUTE=?, DESCRIPTION=?, VENUE_NAME=?, ADDRESS=?, CITY=?, STATE=?, ZIPCODE=?, LONGITUDE=?, LATITUDE=?");
            st.setString(1, title);
            st.setInt(2, month);
            st.setInt(3, day);
            st.setInt(4, year);
            st.setInt(5, hour);
            st.setInt(6, minute);
            st.setString(7, description);
            st.setString(8, venueName);
            st.setString(9, address);
            st.setString(10, city);
            st.setString(11, state);
            st.setString(12, zipCode);
            st.setDouble(13, longitude);
            st.setDouble(14, latitude);
            st.executeUpdate();
            c.commit();
            return title;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * removes a given event from the database
     * @param title
     * @return title of deleted event
     */
    public String deleteEvent(String title){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM EVENT WHERE TITLE="+title);
            st.executeUpdate();
            c.commit();
            return title;
        } catch (Exception e){
            System.out.println(e);
        }
        return "no";
    }

    /**
     * finds all events in a given city and state
     * @param city
     * @param state
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByCityState(String city, String state){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE CITY=? AND STATE=?");
            st.setString(1, city);
            st.setString(2, state);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds all events in a given zip code
     * @param zip
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByZipCode(String zip){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE ZIPCODE="+zip);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds information associated with a given event title
     * @param title
     * @return [ID, title, month, day, year, hour, minute, description, venueName, address, city, state, zipCode,
     * longitude, latitude]
     */
    public String[] selectEventByTitle(String title){
        String res[] = new String[15];
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE TITLE LIKE %"+title+"%");
            ResultSet rs = st.executeQuery();
            ArrayList<String[]> results = getEventResults(rs);
            res = results.get(0);
        } catch (Exception e) {
            System.out.println(e);
        }
        return res;
    }

    /**
     * finds all events attended by a given user
     * @param user
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByUser(String user){
        ArrayList<String[]> events = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT EVENT_ID FROM USER_TO_EVENT WHERE USER=? AND IS_OWNER=0");
            st.setString(1, user);
            ResultSet rs = st.executeQuery();
            ArrayList<Integer> eventIDS = getUTOEResults(rs);
            for (int i = 0; i < eventIDS.size(); i++){
                st = c.prepareStatement("SELECT * FROM EVENT WHERE ID=?");
                st.setInt(1, eventIDS.get(i));
                rs = st.executeQuery();
                ArrayList<String[]> oneEvent = getEventResults(rs);
                events.add(oneEvent.get(0));
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return events;
    }

    /**
     * finds all events on a given date
     * @param month
     * @param day
     * @param year
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByDate(int month, int day, int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH="+month+" AND DAY="+day+" AND YEAR="+year);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds all events in a given month
     * @param month
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByMonth(int month){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH="+month);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds all events in a given year
     * @param year
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> selectEventByYear(int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE YEAR="+year);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    /**
     * finds the ID of an event, given its title
     * @param title
     * @return int eventID
     */
    public int selectEventIDbyTitle(String title){
        Connection c = null;
        int id = 0;
        try {
            PreparedStatement st = c.prepareStatement("SELECT ID FROM EVENT WHERE TITLE="+title);
            ResultSet rs = st.executeQuery();
            ArrayList<String[]> res = getEventResults(rs);
            id = Integer.parseInt(res.get(0)[0]);
        } catch (Exception e) {
            System.out.println(e);
        }
        return id;
    }

    /**
     * inserts a new user-to-event relationship to the database
     * @param userID
     * @param eventID
     * @param isOwner
     * @return int userID
     */
    public int insertUserToEvent(int userID, int eventID, int isOwner){
        try {
            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO USER_TO_EVENT (ID, USER_ID, EVENT_ID, " +
                    "IS_OWNER) VALUES (?,?,?,?)");
            st.setInt(1, getNumRows("USER_TO_EVENT")+1);
            st.setInt(2, userID);
            st.setInt(3, eventID);
            st.setInt(4, isOwner);
            st.executeUpdate();
            c.commit();
            return userID;
        } catch (Exception e){
            System.out.println(e);
        }
        return 0;
    }

    /**
     * retrieves all event attribute values for each result in a given ResultSet
     * @param rs
     * @return ArrayList<[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude]> for each event
     */
    public ArrayList<String[]> getEventResults(ResultSet rs){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            while (rs.next()) {
                String[] toAdd = new String[15];
                toAdd[0] = rs.getInt("ID")+"";
                toAdd[1] = rs.getString("TITLE");
                toAdd[2] = rs.getInt("MONTH") + "";
                toAdd[3] = rs.getInt("DAY") + "";
                toAdd[4] = rs.getInt("YEAR") + "";
                toAdd[5] = rs.getInt("HOUR") + "";
                toAdd[6] = rs.getInt("MINUTE") + "";
                toAdd[7] = rs.getString("DESCRIPTION");
                toAdd[8] = rs.getString("VENUE_NAME");
                toAdd[9] = rs.getString("ADDRESS");
                toAdd[10] = rs.getString("CITY");
                toAdd[11] = rs.getString("STATE");
                toAdd[12] = rs.getString("ZIPCODE");
                toAdd[13] = rs.getDouble("LONGITUDE") + "";
                toAdd[14] = rs.getDouble("LATITUDE") + "";
                results.add(toAdd);
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

    /**
     * retrieves all user attribute values for each result in a given ResultSet
     * @param rs
     * @return ArrayList<[ID, username, password, email, admin status, musician status]> for each event
     */
    public ArrayList<String[]> getUserResults(ResultSet rs){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            while (rs.next()){
                String[] toAdd = new String[6];
                toAdd[0] = rs.getInt("ID")+"";
                toAdd[1] = rs.getString("USERNAME");
                toAdd[2] = rs.getString("PASSWORD");
                toAdd[3] = rs.getString("EMAIL");
                toAdd[4] = rs.getInt("IS_ADMIN")+"";
                toAdd[5] = rs.getInt("IS_MUSICIAN")+"";
                results.add(toAdd);
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

    /**
     * retrieves userIDs from a ResultSet from a user-to-event table search
     * @param rs
     * @return ArrayList of userIDs
     */
    public ArrayList<Integer> getUTOEResults(ResultSet rs){
        ArrayList<Integer> results = new ArrayList<>();
        try {
            while (rs.next()){
                results.add(rs.getInt("USER_ID"));
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

    /**
     * closes the SQLite connection
     */
    public void closeConnection(){
        try {
            c.close();
        } catch (Exception e){
            System.out.println(e);
        }
    }

    public ArrayList<String[]> listResults()
    {
        ArrayList<String[]> results = new ArrayList<>();
        try
        {
            PreparedStatement st = c.prepareStatement("SELECT TITLE, MONTH, DAY, YEAR, HOUR, " +
                    "MINUTE, DESCRIPTION, VENUE_NAME, ADDRESS, CITY, STATE, ZIPCODE, LONGITUDE, LATITUDE " +
                    "from EVENT");
            ResultSet rs = st.executeQuery();
            while(rs.next())
            {
                String[] toAdd = new String[14];
                toAdd[0] = rs.getString("TITLE");
                toAdd[1] = rs.getInt("MONTH") + "";
                toAdd[2] = rs.getInt("DAY") + "";
                toAdd[3] = rs.getInt("YEAR") + "";
                toAdd[4] = rs.getInt("HOUR") + "";
                toAdd[5] = rs.getInt("MINUTE") + "";
                toAdd[6] = rs.getString("DESCRIPTION");
                toAdd[7] = rs.getString("VENUE_NAME");
                toAdd[8] = rs.getString("ADDRESS");
                toAdd[9] = rs.getString("CITY");
                toAdd[10] = rs.getString("STATE");
                toAdd[11] = rs.getString("ZIPCODE");
                toAdd[12] = rs.getDouble("LONGITUDE") + "";
                toAdd[13] = rs.getDouble("LATITUDE") + "";
                results.add(toAdd);
            }rs.close();
        }catch(Exception e)
        {
            System.out.println(e);
        }

        return results;
    }
}