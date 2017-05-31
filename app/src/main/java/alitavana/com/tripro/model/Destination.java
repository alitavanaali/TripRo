package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 10/04/2017.
 */

public class Destination {
    String destination_name, destination_location, destination_image;

    public Destination(String destination_name, String destination_location, String destination_image) {
        this.destination_name = destination_name;
        this.destination_location = destination_location;
        this.destination_image = destination_image;
    }

    public String getDestination_name() {
        return destination_name;
    }

    public void setDestination_name(String destination_name) {
        this.destination_name = destination_name;
    }

    public String getDestination_location() {
        return destination_location;
    }

    public void setDestination_location(String destination_location) {
        this.destination_location = destination_location;
    }

    public String getDestination_image() {
        return destination_image;
    }

    public void setDestination_image(String destination_image) {
        this.destination_image = destination_image;
    }
}
