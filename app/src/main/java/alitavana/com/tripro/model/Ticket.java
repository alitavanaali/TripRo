package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 13/05/2017.
 */

public class Ticket {
    String Mabda, Maghsad, Price, TimeIn, TimeOut, AirportIn, AirportOut, Duration;

    public Ticket() {

    }

    public Ticket(String mabda, String maghsad, String price, String timeIn, String timeOut,
                  String airportIn, String airportOut, String duration) {
        Mabda = mabda;
        Maghsad = maghsad;
        Price = price;
        TimeIn = timeIn;
        TimeOut = timeOut;
        AirportIn = airportIn;
        AirportOut = airportOut;
        Duration = duration;
    }

    public String getMabda() {
        return Mabda;
    }

    public void setMabda(String mabda) {
        Mabda = mabda;
    }

    public String getMaghsad() {
        return Maghsad;
    }

    public void setMaghsad(String maghsad) {
        Maghsad = maghsad;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getTimeIn() {
        return TimeIn;
    }

    public void setTimeIn(String timeIn) {
        TimeIn = timeIn;
    }

    public String getTimeOut() {
        return TimeOut;
    }

    public void setTimeOut(String timeOut) {
        TimeOut = timeOut;
    }

    public String getAirportIn() {
        return AirportIn;
    }

    public void setAirportIn(String airportIn) {
        AirportIn = airportIn;
    }

    public String getAirportOut() {
        return AirportOut;
    }

    public void setAirportOut(String airportOut) {
        AirportOut = airportOut;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
