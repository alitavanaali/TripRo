package alitavana.com.tripro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by eric on 5/29/17.
 */

public class LowestPrice  implements Serializable {

    private ArrayList<Price> price;
    private String name;

    public ArrayList<Price> getPrice() {
        return price;
    }

    public void setPrice(ArrayList<Price> price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
