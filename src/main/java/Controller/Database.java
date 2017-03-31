package Controller;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by erikarumbold on 3/28/17.
 */
public class Database {
    private Connection c = null;
    private Statement st = null;

    public Database(){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:ConcertFinder.db");
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
                    "ADDRESS CHAR(40), CITY CHAR(20), STATE CHAR(2), ZIPCODE CHAR(5), LONGITUDE REAL, LATITUDE REAL);";
            st.execute(sql);

            sql = "CREATE TABLE IF NOT EXISTS USER_TO_EVENT (ID INT PRIMARY KEY NOT NULL, " +
                    "USER_ID INT NOT NULL, EVENT_ID INT NOT NULL, IS_OWNER INT NOT NULL);";
            st.execute(sql);

        } catch (Exception e){
            System.out.println(e);
        }
    }

    public String insertEvent(String title, int month, int day, int year, int hour, int minute,
                              String description, String venueName, String address, String city,
                              String state, String zipCode, double longitude, double latitude){

        try {

            PreparedStatement st = c.prepareStatement("INSERT OR IGNORE INTO EVENT VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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

    public ArrayList<String[]> selectEventByCityState(String city, String state){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE CITY=? AND STATE=?");
            st.setString(1, city);
            st.setString(2, state);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<String[]> selectEventByZipCode(String zip){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE ZIPCODE="+zip);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<String[]> selectEventByTitle(String title){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE TITLE LIKE ?");
            st.setString(1, title);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public String selectEventByOwner(String musician){
        String x = "";
        return x;
    }

    public String selectEventByAttendant(String user){
        String x = "";
        return x;
    }

    public ArrayList<String[]> selectEventByDate(int month, int day, int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH="+month+" AND DAY="+day+" AND YEAR="+year);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<String[]> selectEventByMonth(int month){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE MONTH="+month);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<String[]> selectEventByYear(int year){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM EVENT WHERE YEAR="+year);
            ResultSet rs = st.executeQuery();
            results = getResults(rs);
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<Integer> selectEventIDbyTitle(String title){
        Connection c = null;
        ArrayList<Integer> results = new ArrayList<>();
        try {
            PreparedStatement st = c.prepareStatement("SELECT ID FROM EVENT WHERE TITLE="+title);
            ResultSet rs = st.executeQuery();
            ArrayList<String[]> res = getResults(rs);
            for (int i = 0; i < res.size(); i++){
                results.add(Integer.parseInt(res.get(i)[0]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public ArrayList<String[]> getResults(ResultSet rs){
        ArrayList<String[]> results = new ArrayList<>();
        try {
            while (rs.next()) {
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
            }
            rs.close();
        } catch (Exception e){
            System.out.println(e);
        }
        return results;
    }

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

    public boolean checkDuplicates(String table){
        try {
            PreparedStatement st = c.prepareStatement("SELECT * FROM ? WHERE 0");
        } catch (Exception e){
            System.out.println(e);
        }
        return true;
    }
}
