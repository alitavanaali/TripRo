package alitavana.com.tripro.model;

/**
 * Created by Ali Tavana on 11/05/2017.
 */

public class NearByPlaces {
    String Name, Destiny, TouristicPlaces;

    public NearByPlaces(String name, String destiny, String touristicPlaces) {
        Name = name;
        Destiny = destiny;
        TouristicPlaces = touristicPlaces;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDestiny() {
        return Destiny;
    }

    public void setDestiny(String destiny) {
        Destiny = destiny;
    }

    public String getTouristicPlaces() {
        return TouristicPlaces;
    }

    public void setTouristicPlaces(String touristicPlaces) {
        TouristicPlaces = touristicPlaces;
    }
}
