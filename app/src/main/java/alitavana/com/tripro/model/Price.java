package alitavana.com.tripro.model;

import java.io.Serializable;

/**
 * Created by hamedpourjafar on 6/1/17.
 */

public class Price implements Serializable{

    private String host;
    private String price;
    private String link;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
