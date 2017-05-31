package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 21/04/2017.
 */

public class Restaurant {
    String Name, Phone, Lat, Lng, Address, Rate;

    public Restaurant(String restaurantName, String restaurantRate) {
        Name = restaurantName;
        Rate = restaurantRate;
    }

    public Restaurant(String name, String phone, String lat, String lng, String address, String rate) {
        Name = name;
        Phone = phone;
        Lat = lat;
        Lng = lng;
        Address = address;
        Rate = rate;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLng() {
        return Lng;
    }

    public void setLng(String lng) {
        Lng = lng;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }
}
