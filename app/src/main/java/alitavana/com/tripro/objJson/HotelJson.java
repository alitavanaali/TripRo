package alitavana.com.tripro.objJson;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;


import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.jsoup.Connection;
import org.jsoup.Jsoup;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import alitavana.com.tripro.model.FoursquareModel;
import alitavana.com.tripro.model.Hotel;
import alitavana.com.tripro.model.LowestPrice;
import alitavana.com.tripro.model.Price;
import alitavana.com.tripro.model.TripImage;

import static alitavana.com.tripro.R.drawable.hotel;


/**
 * Created by eric on 5/25/17.
 */

public class HotelJson extends AsyncTask< View,Void, ArrayList<Hotel>>{

    String cityName;
    private String jsessionid="";
    String date;
    String nights;
    private String things_to_doUid = "";

    String url="";

    private ArrayList<LowestPrice> lowestPricesList = new ArrayList<>();

    private ArrayList<Hotel> hotels=new ArrayList<>();

    private List<String> hotelNames = new ArrayList<>();

    String lowDetailTemp;
    String fullDetailTmp;
    private  String hotelUid = "";

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    protected ArrayList<Hotel> doInBackground(View... link) {

        this.url="http://91.99.96.10:8102/PondMS/j_spring_security_check";
        try {
            getConnected(url);
            getLayerUid(cityName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.hotels;
    }

    //this is login method and at first runs
    private void getConnected(String link) throws Exception
    {
        Connection.Response response = Jsoup.connect(link).userAgent("Mozilla/5.0(X11; Ubuntu; Linux x86_64;rv:53.0)Gecko/20100101 Firefox/53.0").data("j_username", "sysadmin").data("j_password", "sysadmin").method(Connection.Method.POST).ignoreContentType(true).execute();
        if(response.statusCode()==200)
        {
            getSessionId(response.cookies().toString());
        }
    }


    //this is for gettting city from uID and secondly we need it
    private void getLayerUid(String cityName) throws IOException {

        JsonObject myJson = new JsonObject();
        JsonArray myArray = new JsonArray();
        JsonObject singleJson = new JsonObject();

        singleJson.addProperty("type", "like");
        singleJson.addProperty("field", "name");
        singleJson.addProperty("value", "شهر" + " " + cityName);
        myArray.add(singleJson);
        myJson.add("restrictions", myArray);

        Connection.Response response = Jsoup.connect(
                "http://91.99.96.10:8102/PondMS/api/org/da0f6335-120d-4557-9b19-eaf6ce5a72c8/layer/items?extent=full")
                .method(Connection.Method.POST)
                .userAgent("Mozilla/5.0(X11; Ubuntu; Linux x86_64;rv:53.0)Gecko/20100101 Firefox/53.0")
                .header("Cookie", jsessionid).header("X-HTTP-Method-Override", "GET")
                .header("Content-Type", "application/json").ignoreContentType(true).requestBody(myJson.toString())
                .header("Content-Encoding", "gzip").execute();

        try {

            JSONObject jsonObject = new JSONObject(response.body());
            JSONArray items = (JSONArray) jsonObject.get("items");
            JSONObject itemObject = (JSONObject) items.get(0);
            JSONArray sublayers = (JSONArray) itemObject.get("subLayers");
            JSONObject hotelObject = (JSONObject) sublayers.get(0);
            hotelUid = hotelObject.get("uid").toString();
            getHotelsListPrice();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //hotels low details
    private void getHotelsListPrice() throws Exception{

        Connection.Response myResponse = Jsoup.connect("http://91.99.96.10:8102/JustRO/rest/search/hotels/items?city="
                + this.cityName + "&check_in=" + date + "&nights=" + nights + "&adults=2&children=2").userAgent("Mozilla").followRedirects(true).header("Cookie" , jsessionid).header("Content-Type" , "application/json").ignoreContentType(true).ignoreHttpErrors(true).method(Connection.Method.GET).execute();
        Log.i("HotelJson", myResponse + "");
        try {

            parseHotelsLowDetailsFromServer(myResponse.body());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getHotelsList(List<String> hotelNames) throws Exception{

        JsonObject restrictionObject = new JsonObject();
        JsonArray restrictionArray = new JsonArray();
        JsonObject layerUidRestrction = new JsonObject();
        JsonObject nameFieldSearchRestriction = new JsonObject();
        JsonObject UidValue = new JsonObject();
        JsonArray collectionsList = new JsonArray();
        JsonArray orderList = new JsonArray();

        UidValue.addProperty("uid", hotelUid);


        for (String response : hotelNames) {
            collectionsList.add(response);
        }

        layerUidRestrction.addProperty("type", "eq");
        layerUidRestrction.add("value", UidValue);
        layerUidRestrction.addProperty("field", "layer");

        restrictionArray.add(layerUidRestrction);

        nameFieldSearchRestriction.addProperty("type", "in");
        nameFieldSearchRestriction.add("collection", collectionsList);
        nameFieldSearchRestriction.addProperty("field", "name");

        restrictionArray.add(nameFieldSearchRestriction);

        restrictionObject.add("restrictions", restrictionArray);
        restrictionObject.add("orders", orderList );


        String url = "http://91.99.96.10:8102/PondMS/api/org/da0f6335-120d-4557-9b19-eaf6ce5a72c8/gis-vector-object/items?extent=full&len=100&start=0";
        Connection.Response myResponseHotelsList = Jsoup.connect(url)
                .userAgent("Mozilla")
                .followRedirects(true)
                .header("Cookie" , jsessionid)
                .header("Content-Type" , "application/json")
                .header("X-HTTP-Method-Override", "GET")
                .ignoreContentType(true)
                .requestBody(restrictionObject.toString())
                .method(Connection.Method.POST).execute();

        Log.d("erfan", myResponseHotelsList.body() + "");
        parseHotelsFullDetailsFromServer( myResponseHotelsList.body());

        // return myResponseHotelsList.body();
    }


    //get all the hotels details from json and map it to hotels list
    private void parseHotelsFullDetailsFromServer(String response) {

        try {

            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("items");

            System.out.println(jsonObject.toString());
            Log.i("mohandes", jsonArray.length() + "");

            for (int i = 0; i < jsonArray.length(); i++) {

                Hotel hotelTemp = new Hotel();
                ArrayList<TripImage> imagesTmp = new ArrayList<>();



                        JSONObject pointTemp = jsonArray.getJSONObject(i).getJSONObject("point");

                        hotelTemp.setLat((Double) pointTemp.getJSONArray("coordinates").get(1));
                        hotelTemp.setHotelName(jsonArray.getJSONObject(i).getString("name"));

                        hotelTemp.setLng((Double) pointTemp.getJSONArray("coordinates").get(0));

                   for (LowestPrice temps : lowestPricesList){


                        if (temps.getName().equals(jsonArray.getJSONObject(i).getString("name"))){

                            hotelTemp.setPrices(temps);
                        }
                    }

                        if (jsonArray.getJSONObject(i).has("formInstance")) {

                            JSONObject temp = jsonArray.getJSONObject(i).getJSONObject("formInstance");

                            hotelTemp.setAddress(temp.getString("Address"));
                            hotelTemp.setDescription(temp.getString("Description"));
                            hotelTemp.setFeatures(temp.getString("Features"));
                            hotelTemp.setHotelRate((Integer) temp.get("Score"));
                            JSONArray jsonImagesArray = temp.getJSONArray("Images");

                            for (int j = 0; j < jsonImagesArray.length(); j++) {

                                JSONObject jsonObjectImages = jsonImagesArray.getJSONObject(j);

                                if (temp.getJSONArray("Images").getJSONObject(j).has("downloadLink")) {
                                    TripImage tripImage = new TripImage();
                                    tripImage.setDownloadLink(jsonObjectImages.getString("downloadLink"));
                                    //if (j == 0) {
                                        tripImage.setPhotoValue(getImage("http://91.99.96.10:8102/PondMS/" + tripImage.getDownloadLink()));
                                    //}
                                    imagesTmp.add(tripImage);
                                }
                            }


                            hotelTemp.setPhotos(imagesTmp);
                        }



                hotels.add(hotelTemp);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private byte[] getImage(String url){

        try {
            Connection.Response resultImageResponse = Jsoup.connect(url).header("Cookie", jsessionid)
                    .ignoreContentType(true).execute();

            return resultImageResponse.bodyAsBytes();

        }catch (Exception e){

            e.printStackTrace();

        }

        return null;
    }


    //parse hotel lowest price in hotels list
    private void parseHotelsLowDetailsFromServer(String response) {

        try {

            JSONArray jsonArray = new JSONArray(response);

            Log.i("mohandes man",  jsonArray.length() + "");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonobject =  jsonArray.getJSONObject(i);
                hotelNames.add(jsonobject.getString("name"));
                JSONArray jsonArray1 = jsonobject.getJSONArray("lowestPrices");
                LowestPrice temp = new LowestPrice();
                temp.setName(jsonobject.getString("name"));
                ArrayList<Price> getMoney = new ArrayList<>();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    Price lowestPrice = new Price();
                    JSONObject jsonObject1 =  jsonArray1.getJSONObject(j);
                    lowestPrice.setPrice(jsonObject1.getString("price"));
                    lowestPrice.setHost(jsonObject1.getString("host"));
                    lowestPrice.setLink(jsonObject1.getString("link"));
                    getMoney.add(lowestPrice);
                }
                temp.setPrice(getMoney);
                lowestPricesList.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            getHotelsList(hotelNames);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //map the JSESSEIONID from connection json
    private void getSessionId(String test) {
        try {
            Gson myGson = new Gson();
            SessionCookies mySessionID = myGson.fromJson(test, SessionCookies.class);
            this.jsessionid = "JSESSIONID="+mySessionID.JSESSIONID;
            System.out.println(jsessionid);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNights(String nights) {
        this.nights = nights;
    }
}

