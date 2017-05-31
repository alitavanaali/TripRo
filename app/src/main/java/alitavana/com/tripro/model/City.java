package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 12/05/2017.
 */

public class City {
    String Name, GeoCode, Country, AirPort;

    public City() {
    }

    public City(String name, String country) {
        Name = name;
        Country = country;
    }

    public City(String name, String country, String airPort) {
        Name = name;
        Country = country;
        AirPort = airPort;
    }

    public City(String name, String geoCode, String country, String airPort) {
        Name = name;
        GeoCode = geoCode;
        Country = country;
        AirPort = airPort;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getAirPort() {
        return AirPort;
    }

    public void setAirPort(String airPort) {
        AirPort = airPort;
    }

    public String getGeoCode() {
        return GeoCode;
    }

    public void setGeoCode(String geoCode) {
        GeoCode = geoCode;
    }
}
