package alitavana.com.tripro.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ali Tavana on 19/04/2017.
 */

public class Hotel implements Serializable {
    private ArrayList<LowestPrice> prices;
    private String name;
    private int score;
    private ArrayList<TripImage> photos;
    private String uID;
    private String address;
    private String description;
    private String features;
    private double lat;
    private double lng;
    private float aliLat;

    public float getAliLat() {
        return aliLat;
    }

    public void setAliLat(float aliLat) {
        this.aliLat = aliLat;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }


    public double getLng() {
        return lng;
    }


    public void setLng(double lng) {
        this.lng = lng;
    }




    public Hotel(String hotelName, int hotelRate) {
        name = hotelName;
        score = hotelRate;
    }

    public Hotel() {

    }

    public String getHotelName() {
        return name;
    }

    public void setHotelName(String hotelName) {
        name = hotelName;
    }

    public int getHotelRate() {
        return score;
    }

    public void setHotelRate(int hotelRate) {
        score = hotelRate;
    }


    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", rate='" + score + '\'' +
                '}';
    }

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public ArrayList<TripImage> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<TripImage> photos) {
        this.photos = photos;
    }

    public ArrayList<LowestPrice> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<LowestPrice> prices) {
        this.prices = prices;
    }
}
