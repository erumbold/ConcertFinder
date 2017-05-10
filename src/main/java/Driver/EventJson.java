package Driver;
/**
 * Created by yanmingwang on 4/30/17.
 * This class hold event data so that it can be turn into json easily by gson.
 */
public class EventJson{
    public String TITLE;
    public int MONTH;
    public int DAY;
    public int YEAR;
    public int HOUR;
    public int MINUTE;
    public String DESCRIPTION;
    public String VENUE_NAME;
    public String ADDRESS;
    public String CITY;
    public String STATE;
    public String ZIPCODE;
    public double LONGITUDE;
    public double LATITUDE;

    public EventJson(){
    }

    /**
     * constructor
     * @param LATITUDE LATITUDE
     * @param TITLE TITLE
     * @param MONTH MONTH
     * @param DAY DAY
     * @param YEAR YEAR
     * @param HOUR HOUR
     * @param MINUTE MINUTE
     * @param DESCRIPTION DESCRIPTION
     * @param VENUE_NAME VENUE_NAME
     * @param ADDRESS ADDRESS
     * @param CITY CITY
     * @param STATE STATE
     * @param ZIPCODE ZIPCODE
     * @param LONGITUDE LONGITUDE
     */
    public EventJson(double LATITUDE, String TITLE, int MONTH, int DAY, int YEAR, int HOUR, int MINUTE, String DESCRIPTION, String VENUE_NAME, String ADDRESS, String CITY, String STATE, String ZIPCODE, double LONGITUDE) {
        this.LATITUDE = LATITUDE;
        this.TITLE = TITLE;
        this.MONTH = MONTH;
        this.DAY = DAY;
        this.YEAR = YEAR;
        this.HOUR = HOUR;
        this.MINUTE = MINUTE;
        this.DESCRIPTION = DESCRIPTION;
        this.VENUE_NAME = VENUE_NAME;
        this.ADDRESS = ADDRESS;
        this.CITY = CITY;
        this.STATE = STATE;
        this.ZIPCODE = ZIPCODE;
        this.LONGITUDE = LONGITUDE;
    }

    public String getTITLE() {
        return TITLE;
    }

    public void setTITLE(String TITLE) {
        this.TITLE = TITLE;
    }

    public int getMONTH() {
        return MONTH;
    }

    public void setMONTH(int MONTH) {
        this.MONTH = MONTH;
    }

    public int getDAY() {
        return DAY;
    }

    public void setDAY(int DAY) {
        this.DAY = DAY;
    }

    public int getYEAR() {
        return YEAR;
    }

    public void setYEAR(int YEAR) {
        this.YEAR = YEAR;
    }

    public int getHOUR() {
        return HOUR;
    }

    public void setHOUR(int HOUR) {
        this.HOUR = HOUR;
    }

    public int getMINUTE() {
        return MINUTE;
    }

    public void setMINUTE(int MINUTE) {
        this.MINUTE = MINUTE;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public String getVENUE_NAME() {
        return VENUE_NAME;
    }

    public void setVENUE_NAME(String VENUE_NAME) {
        this.VENUE_NAME = VENUE_NAME;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getCITY() {
        return CITY;
    }

    public void setCITY(String CITY) {
        this.CITY = CITY;
    }

    public String getSTATE() {
        return STATE;
    }

    public void setSTATE(String STATE) {
        this.STATE = STATE;
    }

    public String getZIPCODE() {
        return ZIPCODE;
    }

    public void setZIPCODE(String ZIPCODE) {
        this.ZIPCODE = ZIPCODE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    @Override
    public String toString() {
        return "EventJson{" +
                "TITLE='" + TITLE + '\'' +
                ", MONTH=" + MONTH +
                ", DAY=" + DAY +
                ", YEAR=" + YEAR +
                ", HOUR=" + HOUR +
                ", MINUTE=" + MINUTE +
                ", DESCRIPTION='" + DESCRIPTION + '\'' +
                ", VENUE_NAME='" + VENUE_NAME + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", CITY='" + CITY + '\'' +
                ", STATE='" + STATE + '\'' +
                ", ZIPCODE='" + ZIPCODE + '\'' +
                ", LONGITUDE=" + LONGITUDE +
                ", LATITUDE=" + LATITUDE +
                '}';
    }
}