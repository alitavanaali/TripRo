package alitavana.com.tripro.model;

import java.io.Serializable;

/**
 * Created by Ali Tavana on 19/05/2017.
 */

public class FoursquareModel  implements Serializable {
    private String id;
    private String name;
    private String city,country;
    private String longtitude,latitude,address;
    private String telephone;
    private String price_tier; // $ $$ $$$ $$$$ $$$$$
    private String rate;
    private String featured_photo;
    private String featured_photo_big;
    private String icon;
    private String category,category_id;
    private String status, isopen;
    private String website;
    private String distance;


    public FoursquareModel(String name, String city, String longtitude, String latitude, String address, String country, String category) {
        this.name = name;
        this.city = city;
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.address= address;
        this.country  =  country;
        this.setCategory(category);

    }

    public FoursquareModel() {
        this.name = "";
        this.city = "";
        this.longtitude = "";
        this.latitude = "";
        this.address= "";
        this.country  =  "";
        this.setCategory("");

    }

    public String getCity() {
        if (city.length() > 0) {
            return city;
        }
        return city;
    }

    public void setCity(String city) {
        if (city != null) {
            this.city = city.replaceAll("\\(", "").replaceAll("\\)", "");
            ;
        }
    }


    public void setCategoryIcon(String icon)
    {
        this.icon = icon;
    }
    public String getCategoryIcon()
    {
        return this.icon;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return this.address;
    }
    public void setCountry(String country)
    {
        this.country = country;
    }
    public String getCountry()
    {
        return this.country;

    }


    public void setLongtitude(String longtitude)
    {
        this.longtitude = longtitude;
    }

    public double getLongtitude( )
    {
        return Double.parseDouble(this.longtitude)  ;
    }


    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public double getLatitude( )
    {
        return  Double.parseDouble(this.latitude)  ;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getFeatured_photo() {
        return featured_photo;
    }

    public void setFeatured_photo(String featured_photo) {
        this.featured_photo = featured_photo;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getPrice_tier() {
        return price_tier;
    }

    public void setPrice_tier(String currency) {
        this.price_tier = currency;
    }

    public String getFeatured_photo_big() {
        return featured_photo_big;
    }

    public void setFeatured_photo_big(String featured_photo_big) {
        this.featured_photo_big = featured_photo_big;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsopen() {
        return isopen;
    }

    public void setIsopen(String isopen) {
        this.isopen = isopen;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}