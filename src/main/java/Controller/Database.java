package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


// TODO WRITE SELECT FOR EXTENDED INFO

/**
 * This class consists of a database constructor and all methods to interact with information stored in the database.
 * @author Erika Rumbold
 */
public class Database {
    private Connection c = null;
    private Statement st = null;
    private final static Logger LOGGER = Logger.getLogger(Database.class.getName());

    /**
     * This constructor opens a connection to the database and creates the tables (EVENT, USER, USERDETAIL,
     * MUSICIANDETAIL, USER_TO_EVENT) if they don't already exist.
     */
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

            LOGGER.log(Level.FINE, "database accessed");

        } catch (Exception e){
            LOGGER.log(Level.WARNING, "not connected to database");
        }
    }

    /**
     * This function gives the number of rows in a table
     * @param table name of table of which to count the number of rows
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
            LOGGER.log(Level.INFO, "number of rows not calculated");
        }
        return count;
    }

    /**
     * inserts a new user into the database if the username, password, or email don't already exist
     * @param username desired username
     * @param password associated password
     * @param email associated email
     * @param isAdmin Admin status
     * @param isMusician Musician status
     * @post new row in USER table in connected database
     */
    public void insertUser(String username, String password, String email, int isAdmin, int isMusician){
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
            LOGGER.log(Level.FINE, "User {0} added to database", username);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0} could not be added", username);
        }
    }

    /**
     * updates the attribute values of a given user, cannot change user type (normal, admin, musician)
     * @param newUsername new username
     * @param password new password
     * @param email new email
     * @param oldUsername username to change
     * @post values for given row in USER table of connected database are changed
     */
    public void modifyUser(String newUsername, String password, String email, String oldUsername){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE USER SET USERNAME=?, PASSWORD=?, EMAIL=? WHERE USERNAME=?");
            st.setString(1, newUsername);
            st.setString(2, password);
            st.setString(3, email);
            st.setString(4, oldUsername);
            LOGGER.log(Level.FINE, "User {0}'s info changed", oldUsername);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0}'s info NOT changed", oldUsername);
        }
    }

    /**
     * removes a username from the database
     * @param username name of user to remove
     * @post row removed from USER table in connected database
     */
    public void deleteUser(String username){
        try {
            String[] userInfo = selectUserByUsername(username);
            PreparedStatement st = c.prepareStatement("DELETE FROM USER WHERE USERNAME=?");
            st.setString(1, username);
            st.executeUpdate();
            c.commit();
            if (userInfo[5].equals("0")) {
                deleteUserDetail(Integer.parseInt(userInfo[0]));
            } else {
                deleteMusicianDetail(Integer.parseInt(userInfo[0]));
            }
            LOGGER.log(Level.FINE, "User {0} deleted", username);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0} NOT deleted", username);
        }
    }

    /**
     * finds all user information associated with a given username
     * @param username user to find
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByUsername(String username){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE USERNAME=?");
            st.setString(1, username);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find user by username");
        }
        return results.get(0);
    }

    /**
     * finds all user information associated with a given email
     * @param email email of user to find
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByEmail(String email){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE EMAIL=?");
            st.setString(1, email);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find user by email");
        }
        return results.get(0);
    }

    /**
     * finds all user information associated with a given ID
     * @param id ID of user to find
     * @return [ID, username, password, email, admin status, musician status]
     */
    public String[] selectUserByID(int id){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE ID=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find user by ID");
        }
        return results.get(0);
    }

    /**
     * finds the username of a given event's owner
     * @param event event of which to find the owner
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
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find musician by event");
        }
        return owner;
    }

    /**
     * finds all users that are admins
     * @return ArrayList of arrays[ID, username, password, email, admin status, musician status] for each user
     */
    public ArrayList<String[]> selectUserAdmins(){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE IS_ADMIN=1");
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find Admins");
        }
        return results;
    }

    /**
     * finds all users that are musicians
     * @return ArrayList of arrays[ID, username, password, email, admin status, musician status] for each user
     */
    public ArrayList<String[]> selectUserMusicians(){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM USER WHERE IS_MUSICIAN=1");
            ResultSet rs = st.executeQuery();
            results = getUserResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find Musicians");
        }
        return results;
    }

    /**
     * inserts extended user information, specific for general users, into the database
     * @param userID ID of relevant user
     * @param firstName user's first name
     * @param lastName user's last name
     * @param city user's home city
     * @param state user's home state
     * @post new row in USERDETAIL table of connected database
     */
    public void insertUserDetail(int userID, String firstName, String lastName, String city, String state){
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
            LOGGER.log(Level.FINE, "Extended info for User {0} added to database", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Extended info for User {0} NOT added to database", userID);
        }
    }

    /**
     * updates the attribute values of a user's extended information
     * @param userID user's ID
     * @param firstName user's new first name
     * @param lastName new last name
     * @param city new home city
     * @param state new home state
     * @post values in USERDETAIL table of connected database are changed
     */
    public void modifyUserDetail(int userID, String firstName, String lastName, String city, String state){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE USERDETAIL SET FIRST_NAME=?, LAST_NAME=?, " +
                    "CITY=?, STATE=? WHERE USER_ID=?");
            st.setString(1, firstName);
            st.setString(2, lastName);
            st.setString(3, city);
            st.setString(4, state);
            st.setInt(5, userID);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "User {0}'s extended info changed", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0}'s extended info NOT changed", userID);
        }
    }

    /**
     * removes a user's extended information from the database
     * @param userID ID of user to delete
     * @post row removed from USERDETAIL table of connected database
     */
    public void deleteUserDetail(int userID){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM USERDETAIL WHERE USER_ID=?");
            st.setInt(1, userID);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "User {0}'s extended info deleted", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0}'s extended info NOT deleted", userID);
        }
    }

    /**
     * inserts extended musician information, specific for musician users, in the database
     * @param userID associated user ID
     * @param name musician name
     * @param description description of musician
     * @param city musician's home city
     * @param state musician's home state
     * @param twitter musician's twitter url
     * @param facebook musician's facebook url
     * @param soundcloud musician's soundcloud url
     * @post new row in MUSICIANDETAIL table of connected database
     */
    public void insertMusicianDetail(int userID, String name, String description, String city, String state,
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
            LOGGER.log(Level.FINE, "Extended info for Musician {0} added to database", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Extended info for Musician {0} NOT added to database", userID);
        }
    }

    /**
     * updates the attribute values of a musician's extended information
     * @param userID ID of user to be changed
     * @param name new musician name
     * @param description updated description
     * @param city new home city
     * @param state new home state
     * @param twitter new twitter url
     * @param facebook new facebook url
     * @param soundcloud new soundcloud url
     * @post values for given row in MUSICIANDETAIL table of connected database are changed
     */
    public void modifyMusicianDetail(int userID, String name, String description, String city, String state,
                                     String twitter, String facebook, String soundcloud){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE MUSICANDETAIL SET NAME=?, DESCRIPTION=?, " +
                    "CITY=?, STATE=?, TWITTER=?, FACEBOOK=?, SOUNDCLOUD=? WHERE USER_ID=?");
            st.setString(1, name);
            st.setString(2, description);
            st.setString(3, city);
            st.setString(4, state);
            st.setString(5, twitter);
            st.setString(6, facebook);
            st.setString(7, soundcloud);
            st.setInt(8, userID);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "Extended info for Musician {0} changed", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Extended info for Musician {0} NOT changed", userID);
        }
    }

    /**
     * removes a musician's extended information from the database
     * @param userID ID of user to be deleted
     * @post row removed from MUSICIANDETAIL table in connected database
     */
    public void deleteMusicianDetail(int userID){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM MUSICIANDETAIL WHERE USER_ID=?");
            st.setInt(1, userID);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "Musician {0}'s extended info deleted", userID);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Musician {0}'s extended info NOT deleted", userID);
        }
    }

    /**
     * inserts a new event into the database if the title of the event is not already in the database
     * @param title event title
     * @param month month of event
     * @param day day of event
     * @param year year of event
     * @param hour hour of event
     * @param minute minute of event
     * @param description event description
     * @param venueName venue of event
     * @param address venue's street address
     * @param city venue's city
     * @param state venue's state
     * @param zipCode venue's zip code
     * @param longitude venue's longitude coordinate
     * @param latitude venue's latitude coordinate
     * @post new row in EVENT table of connected database
     */
    public void insertEvent(String title, int month, int day, int year, int hour, int minute,
                            String description, String venueName, String address, String city,
                            String state, String zipCode, double longitude, double latitude){
        System.out.println("insertEvent Called");

        try {
            System.out.println("insertEvent Tried");

            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO EVENT (ID, TITLE, MONTH, DAY, YEAR, HOUR, " +
                    "MINUTE, DESCRIPTION, VENUE_NAME, ADDRESS, CITY, STATE, ZIPCODE, LATITUDE, LONGITUDE) " +
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
            st.setDouble(14, latitude);
            st.setDouble(15, longitude);
            st.executeUpdate();
            c.commit();
            System.out.println("insertEvent Added");
            LOGGER.log(Level.FINE, "Event {0} added to database", title);
        } catch (Exception e){
            System.out.println("insertEvent NOT ADDED");
            LOGGER.log(Level.INFO, "Event {0} NOT added to database", title);
        }
    }

    /**
     * updates the attribute values of a given event
     * @param title updated event title
     * @param month updated month of event
     * @param day updated day of event
     * @param year updated year of event
     * @param hour updated hour of event
     * @param minute updated minute of event
     * @param description updated event description
     * @param venueName updated venue of event
     * @param address updated venue's street address
     * @param city updated venue's city
     * @param state updated venue's state
     * @param zipCode updated venue's zip code
     * @param longitude updated venue's longitude coordinate
     * @param latitude updated venue's latitude coordinate
     * @post values for given row of EVENT table are changed
     */
    public void modifyEvent(int id, String title, int month, int day, int year, int hour, int minute,
                            String description, String venueName, String address, String city,
                            String state, String zipCode, double longitude, double latitude){
        try {
            PreparedStatement st = c.prepareStatement("UPDATE EVENT SET TITLE=?, MONTH=?, DAY=?, YEAR=?, HOUR=?, " +
                    "MINUTE=?, DESCRIPTION=?, VENUE_NAME=?, ADDRESS=?, CITY=?, STATE=?, ZIPCODE=?, LATITUDE=?, LONGITUDE=? WHERE ID=?");
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
            st.setDouble(13, latitude);
            st.setDouble(14, longitude);
            st.setInt(15, id);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "Event {0}'s info changed", title);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Event {0}'s info NOT changed", title);
        }
    }

    /**
     * removes a given event from the database
     * @param title title of event to delete
     * @post row removed from EVENT table of connected database
     */
    public void deleteEvent(String title){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM EVENT WHERE TITLE=?");
            st.setString(1, title);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "Event {0} deleted", title);
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Event {0} NOT deleted", title);
        }
    }

    /**
     * finds all events in a given city and state
     * @param city name of city in which to find events
     * @param state name of state in which to find events
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
     */
    public ArrayList<String[]> selectEventByCityState(String city, String state){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE CITY=? AND STATE=?");
            st.setString(1, city);
            st.setString(2, state);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            System.out.println(e);
            LOGGER.log(Level.INFO, "Unable to execute search: find event by city, state");
        }
        return results;
    }

    /**
     * finds all events in a given zip code
     * @param zip zip code in which to find events
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
     */
    public ArrayList<String[]> selectEventByZipCode(String zip){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE ZIPCODE=?");
            st.setString(1, zip);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event by zip code");
        }
        return results;
    }

    /**
     * finds information associated with a given event title
     * @param title name of event to find
     * @return array [ID, title, month, day, year, hour, minute, description, venueName, address, city, state, zipCode,
     * longitude, latitude]
     */
    public String[] selectEventByTitle(String title){
        String res[] = new String[15];
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE TITLE=?");
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            ArrayList<String[]> results = getEventResults(rs);
            res = results.get(0);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event by title");
        }
        return res;
    }

    /**
     * finds all events attended by a given user
     * @param user username of owner of the events to find
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
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
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Unable to execute search: find event by user");
        }
        return events;
    }

    /**
     * finds all events on a given date
     * @param month month of events
     * @param day day of events
     * @param year year of events
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
     */
    public ArrayList<String[]> selectEventByDate(int month, int day, int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH=? AND DAY=? AND YEAR=?");
            st.setInt(1, month);
            st.setInt(2, day);
            st.setInt(3, year);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event by date");
        }
        return results;
    }

    /**
     * finds all events in a given month
     * @param month month of events
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
     */
    public ArrayList<String[]> selectEventByMonth(int month){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH=?");
            st.setInt(1, month);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event by month");
        }
        return results;
    }

    /**
     * finds all events in a given year
     * @param year year of events
     * @return ArrayList of arrays[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
     */
    public ArrayList<String[]> selectEventByYear(int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE YEAR=?");
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            results = getEventResults(rs);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event by year");
        }
        return results;
    }

    /**
     * finds the ID of an event, given its title
     * @param title title of event to find
     * @return int eventID
     */
    public int selectEventIDbyTitle(String title){
        Connection c = null;
        int id = 0;
        try {
            PreparedStatement st = c.prepareStatement("SELECT ID FROM EVENT WHERE TITLE=?");
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            ArrayList<String[]> res = getEventResults(rs);
            id = Integer.parseInt(res.get(0)[0]);
            LOGGER.log(Level.FINE, "Search Successful");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Unable to execute search: find event ID by title");
        }
        return id;
    }

    /**
     * inserts a new user-to-event relationship to the database
     * @param userID ID of user to connect
     * @param eventID ID of event to connect
     * @param isOwner status of given userID's ownership of the event
     * @post new row in USER_TO_EVENT table of connected database
     */
    public void insertUserToEvent(int userID, int eventID, int isOwner){
        try {
            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO USER_TO_EVENT (ID, USER_ID, EVENT_ID, " +
                    "IS_OWNER) VALUES (?,?,?,?)");
            st.setInt(1, getNumRows("USER_TO_EVENT")+1);
            st.setInt(2, userID);
            st.setInt(3, eventID);
            st.setInt(4, isOwner);
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "User {0} (owner: {1}) to Event {2} link added to database", new Object[]{userID, isOwner, eventID});
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User {0} to Event {1} link NOT added to database", new Object[]{userID, eventID});
        }
    }

    /**
     * retrieves all event attribute values for each result in a given ResultSet
     * @param rs SQLite query ResultSet from which to extract data
     * @return ArrayList[ID, title, month, day, year, hour, minute, description, venueName, address, city, state,
     * zipCode, longitude, latitude] for each event
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
                toAdd[13] = rs.getDouble("LATITUDE") + "";
                toAdd[14] = rs.getDouble("LONGITUDE") + "";
                results.add(toAdd);
            }
            rs.close();
            LOGGER.log(Level.FINE, "Event retrieval successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Event retrieval unsuccessful");
        }
        return results;
    }

    /**
     * retrieves all user attribute values for each result in a given ResultSet
     * @param rs SQLite query ResultSet from which to extract data
     * @return ArrayList[ID, username, password, email, admin status, musician status] for each event
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
            LOGGER.log(Level.FINE, "User retrieval successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User retrievel unsuccessful");
        }
        return results;
    }

    /**
     * retrieves userIDs from a ResultSet from a user-to-event table search
     * @param rs SQLite query ResultSet from which to extract data
     * @return ArrayList of userIDs
     */
    public ArrayList<Integer> getUTOEResults(ResultSet rs){
        ArrayList<Integer> results = new ArrayList<>();
        try {
            while (rs.next()){
                results.add(rs.getInt("USER_ID"));
            }
            rs.close();
            LOGGER.log(Level.FINE, "User/Event retrieval successful");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "User/Event retrieval unsuccessful");
        }
        return results;
    }

    /**
     * gets all rows in EVENT table of connected database
     * @return ArrayList of arrays[title, month, day, year, hour, minute, description, venue name, address, city, state,
     * zip code, longitude, latitude]
     */
    public ArrayList<String[]> listResults()
    {
        ArrayList<String[]> results = new ArrayList<>();
        try {
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
                toAdd[12] = rs.getDouble("LATITUDE") + "";
                toAdd[13] = rs.getDouble("LONGITUDE") + "";
                results.add(toAdd);
            }
            rs.close();
            LOGGER.log(Level.FINE, "all events retrieved successfully");
        } catch(Exception e) {
            LOGGER.log(Level.INFO, "unable to retrieve all events");
        }

        return results;
    }

    /**
     * closes the SQLite connection
     */
    public void closeConnection(){
        try {
            c.close();
            LOGGER.log(Level.FINE, "database connection closed successfully");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "database connection NOT closed");
        }
    }

    public void clearEventTable(){
        try {
            PreparedStatement st = c.prepareStatement("DELETE FROM EVENT");
            st.executeUpdate();
            c.commit();
            LOGGER.log(Level.FINE, "Table {0} deleted");
        } catch (Exception e){
            LOGGER.log(Level.INFO, "Table {0} NOT deleted");
        }
    }

    public static void main(String[] args) {
        Database db = new Database();
//        db.deleteEvent("Drake at Ithaca College");
//        db.clearEventTable();
    }
}