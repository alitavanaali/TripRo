package alitavana.com.tripro.forsquare;

import android.content.Context;
import android.icu.text.DecimalFormat;
import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import alitavana.com.tripro.gps.GPSTracker;
import alitavana.com.tripro.model.FoursquareModel;

import static alitavana.com.tripro.activity.MainActivity.IsPrice1;
import static alitavana.com.tripro.activity.MainActivity.IsPrice2;
import static alitavana.com.tripro.activity.MainActivity.IsPrice3;
import static alitavana.com.tripro.activity.MainActivity.IsPrice4;


/**
 * Created by Ali Tavana on 23/05/2017.
 */

public class RestaurantForSquare extends AsyncTask<View, Void, ArrayList<FoursquareModel>> {
    int offset = 1;
    String temp;
    ArrayList<FoursquareModel> venuesList = new ArrayList<>();
    String geoCode;
    Location location = new Location("");
    Location currentLocation = new Location("");
    GPSTracker gps;
    Context context;
    @Override
    protected ArrayList<FoursquareModel> doInBackground(View... urls) {
        // make Call to the url
        String Link= "";
        String prices= getPrice();
        if (location.getLongitude() != 0 && location.getLatitude() != 0) {
            /*Link = "https://api.foursquare.com/v2/venues/explore?"
                    + "near=" + geoCode +
                    "&section=food" +
                    "&price=" + prices +
                    "&venuePhotos=1" +
                    "&offset=" + offset +
                    "&oauth_token=AZ1XZL5WKYB0CXYHABBKM1TAN0MJSYZF4FX4G34JSGVQPX5S&v=20170522";*/
            Link = "https://api.foursquare.com/v2/venues/explore?"
                    + "ll=" + location.getLatitude() + "," + location.getLongitude() +
                    "&price=" + prices +
                    "&section=food" +
                    "&venuePhotos=1" +
                    "&sortByDistance=1" +
                    "&radius=10000" +
                    "&offset=" + offset +
                    "&oauth_token=AZ1XZL5WKYB0CXYHABBKM1TAN0MJSYZF4FX4G34JSGVQPX5S&v=20170522";
        }
        else {
            Link = "https://api.foursquare.com/v2/venues/explore?"
                    + "ll=" + currentLocation.getLatitude() + "," + currentLocation.getLongitude() +
                    "&price=" + prices +
                    "&section=food" +
                    "&radius=10000" +
                    "&sortByDistance=1" +
                    "&venuePhotos=1" +
                    "&offset=" + offset +
                    "&oauth_token=AZ1XZL5WKYB0CXYHABBKM1TAN0MJSYZF4FX4G34JSGVQPX5S&v=20170522";
        }
        Log.e("Link", Link);
        temp = makeCall(Link);
        Log.e("Link ---- > ", temp);
        if (temp != null)
            venuesList = (ArrayList<FoursquareModel>) parseFoursquare(temp);
        else venuesList = null;
        return venuesList;
    }

    @Override
    protected void onPreExecute() {
        // we can start a progress bar here
        getCurrentLocation();
    }

    @Override
    protected void onPostExecute(ArrayList<FoursquareModel> result) {
        if (temp == null) {
            Log.e("ERROR! ", "temp is null!");
            // we have an error to the call
            // we can also stop the progress bar
        } else {
            // all things went right
            Log.e("ERROR! ", "temp is not null!");
            // parseFoursquare venues search result
                /*venuesList = (ArrayList<FoursquareModel>) parseFoursquare(temp);*/
            Log.e("ERROR! ", "venuesList.size is : " + venuesList.size());
            // set the results to the list
            // and show them in the xml

            /*for (FoursquareModel model : venuesList) {
                *//*Log.i("FoursquareModel", "getName " + model.getName());
                Log.i("FoursquareModel", "getAddress " + model.getAddress());
                Log.i("FoursquareModel", "getCategory " + model.getCategory());
                Log.i("FoursquareModel", "getCategoryID " + model.getCategory_id());
                Log.i("FoursquareModel", "getPrice_tier " + model.getPrice_tier());
                Log.i("FoursquareModel", "getFeatured_photo " + model.getFeatured_photo());
                Log.i("FoursquareModel", "getRate " + model.getRate());
                Log.i("FoursquareModel", "getTelephone " + model.getTelephone());
                Log.i("FoursquareModel", "getCountry + getCity " + model.getCountry() + model.getCity());
                Log.i("FoursquareModel", "**************************************");*//*
            }*/

        }
    }

    public void setContext(Context context){
        this.context = context;
    }

    public void setLocation(String lat, String lng){
        this.location.setLatitude(Double.valueOf(lat));
        this.location.setLongitude(Double.valueOf(lng));
    }

    private String getPrice(){
        String prices = "";
        if (IsPrice1)
            prices += "1";
        if (IsPrice2)
            prices += "2";
        if (IsPrice3)
            prices += "3";
        if (IsPrice4)
            prices += "4";

        if (prices.equals(""))
            return "1,2,3,4";
        // gozashtane comma (,) beyne adad haa
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < prices.length(); i++) {
            if (i > 0) {
                result.append(",");
            }
            result.append(prices.charAt(i));
        }
        System.out.println(result.toString());
        return result.toString();
    }

    public void setGeoCode(String geoCode){ this.geoCode = geoCode; }

    public void setOffset(int newOffsetNumber){
        offset = newOffsetNumber;
    }

    private void getCurrentLocation(){
        gps = new GPSTracker(context);
        // check if GPS enabled
        if (gps.canGetLocation()) {
            currentLocation.setLatitude(gps.getLatitude());
            currentLocation.setLongitude(gps.getLongitude());
        } else {
            gps.showSettingsAlert();
            // set tehran lat lang
            currentLocation.setLatitude(35.6892);
            currentLocation.setLongitude(51.3890);
        }
    }

    private static String convertTimeToPersian(String englishString){
        String newPersian = englishString;
        if (newPersian.contains("Likely open")){
            newPersian = newPersian.replace("Likely open", "در حال حاضر باز است");
        }
        if (newPersian.contains("Closed until")){
            newPersian = newPersian.replace("Closed until", "بسته است تا");
        }

        if (newPersian.contains("Open until")){
            newPersian = newPersian.replace("Open until", "باز است تا");
        }

        if (newPersian.contains("reopens later")){
            newPersian = newPersian.replace("reopens later", "دوباره باز می شود");
        }

        if (newPersian.contains("Midnight")){
            newPersian = newPersian.replace("Midnight", "نیمه شب");
        }

        if (newPersian.contains("PM")){
            newPersian = newPersian.replace("PM", "بعد از ظهر");
        }

        if (newPersian.contains("AM")){
            newPersian = newPersian.replace("AM", "قبل از ظهر");
        }
        return newPersian;
    }

    public static String makeCall(String url) {

        // string buffers the url
        StringBuffer buffer_string = new StringBuffer(url);
        String replyString = "";

        // instanciate an HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        // instanciate an HttpGet
        HttpGet httpget = new HttpGet(buffer_string.toString());

        try {
            // get the responce of the httpclient execution of the url
            HttpResponse response = httpclient.execute(httpget);
            InputStream is = response.getEntity().getContent();

            // buffer input stream the result
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(20);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            // the result as a string is ready for parsing
            replyString = new String(baf.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // trim the whitespaces
        return replyString.trim();
    }

    private static ArrayList<FoursquareModel> parseFoursquare(
            final String response) {

        ArrayList<FoursquareModel> temp = new ArrayList<FoursquareModel>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                Log.i("Link", "response darad!");
                if (jsonObject.getJSONObject("response").has("groups")) {
                    Log.i("Link", "groups darad!");
                    if (jsonObject.getJSONObject("response").getJSONArray("groups").getJSONObject(0).has("items")) {
                        Log.i("Link", "items darad!");
                    }

                    JSONArray jsonArray = jsonObject.getJSONObject("response")
                            .getJSONArray("groups").getJSONObject(0).getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        FoursquareModel poi = new FoursquareModel();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        try {
                            if (jsonObject1.getJSONObject("venue").has("name")) {
                                poi.setName(jsonObject1.getJSONObject("venue")
                                        .getString("name"));
                            }
                            if (jsonObject1.getJSONObject("venue").has("id")) {
                                poi.setId(jsonObject1.getJSONObject("venue")
                                        .getString("id"));
                            }
                            if (jsonObject1.getJSONObject("venue").has("contact")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("contact").has("phone")) {
                                    poi.setTelephone(jsonObject1.getJSONObject("venue").getJSONObject("contact")
                                            .getString("phone"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("location")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("location").has("lat")) {
                                    poi.setLatitude(jsonObject1.getJSONObject("venue").getJSONObject("location")
                                            .getString("lat"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("location")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("location").has("lng")) {
                                    poi.setLongtitude(jsonObject1.getJSONObject("venue").getJSONObject("location")
                                            .getString("lng"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("hours")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("hours").has("status")) {
                                    poi.setStatus(convertTimeToPersian(jsonObject1.getJSONObject("venue").getJSONObject("hours")
                                            .getString("status")));

                                }
                                if (jsonObject1.getJSONObject("venue").getJSONObject("hours").has("isOpen")) {
                                    poi.setIsopen(jsonObject1.getJSONObject("venue").getJSONObject("hours")
                                            .getString("isOpen"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("location")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("location").has("formattedAddress")) {
                                    JSONArray address = jsonObject1.getJSONObject("venue").getJSONObject("location")
                                            .getJSONArray("formattedAddress");
                                    String formattedAddress = "";
                                    for (int number = 0; number < address.length(); number++)
                                        formattedAddress += address.getString(number) + " ";
                                    poi.setAddress(formattedAddress);
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("location")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("location").has("city")) {
                                    poi.setCity(jsonObject1.getJSONObject("venue").getJSONObject("location")
                                            .getString("city"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("location")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("location").has("country")) {
                                    poi.setCountry(jsonObject1.getJSONObject("venue").getJSONObject("location")
                                            .getString("country"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("categories")) {
                                if (jsonObject1.getJSONObject("venue").getJSONArray("categories").getJSONObject(0).has("name")) {
                                    poi.setCategory(jsonObject1.getJSONObject("venue").getJSONArray("categories").getJSONObject(0)
                                            .getString("name"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("categories")) {
                                if (jsonObject1.getJSONObject("venue").getJSONArray("categories").getJSONObject(0).has("id")) {
                                    poi.setCategory_id(jsonObject1.getJSONObject("venue").getJSONArray("categories").getJSONObject(0)
                                            .getString("id"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("price")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("price").has("currency")) {
                                    poi.setPrice_tier(jsonObject1.getJSONObject("venue").getJSONObject("price")
                                            .getString("tier"));
                                }
                            }
                            if (jsonObject1.getJSONObject("venue").has("rating")) {
                                poi.setRate(jsonObject1.getJSONObject("venue").getString("rating"));
                            }
                            else {
                                poi.setRate("0");
                            }
                            if (jsonObject1.getJSONObject("venue").has("url")) {
                                poi.setWebsite(jsonObject1.getJSONObject("venue").getString("url"));
                            }
                            if (jsonObject1.getJSONObject("venue").has("featuredPhotos")) {
                                if (jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").has("items")) {
                                    if (jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").getJSONArray("items")
                                            .getJSONObject(0).has("prefix")) {
                                        String ImagePath =
                                                jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").getJSONArray("items")
                                                        .getJSONObject(0).getString("prefix")
                                                        + "130x130" +
                                                        jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").getJSONArray("items")
                                                                .getJSONObject(0).getString("suffix");
                                        String ImagePathBig =
                                                jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").getJSONArray("items")
                                                        .getJSONObject(0).getString("prefix")
                                                        + "600x600" +
                                                        jsonObject1.getJSONObject("venue").getJSONObject("featuredPhotos").getJSONArray("items")
                                                                .getJSONObject(0).getString("suffix");
                                        poi.setFeatured_photo(ImagePath);
                                        poi.setFeatured_photo_big(ImagePathBig);
                                    }
                                }
                            }
                            temp.add(poi);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;

    }
}
