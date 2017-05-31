package alitavana.com.tripro.forsquare;

import android.icu.text.DecimalFormat;
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

import alitavana.com.tripro.model.PictureModel;


/**
 * Created by Ali Tavana on 23/05/2017.
 */

public class PhotosForSquare extends AsyncTask<View, Void, ArrayList<PictureModel>> {
    String temp;
    ArrayList<PictureModel> photosList = new ArrayList<>();
    String photoID = "";

    @Override
    protected ArrayList<PictureModel> doInBackground(View... urls) {
        String Link2 = "https://api.foursquare.com/v2/venues/571cf70c498e4066e07a16f0/photos?oauth_token=AZ1XZL5WKYB0CXYHABBKM1TAN0MJSYZF4FX4G34JSGVQPX5S&v=20170526";
        String Link = "https://api.foursquare.com/v2/venues/"
                + photoID +
                "/photos?oauth_token=AZ1XZL5WKYB0CXYHABBKM1TAN0MJSYZF4FX4G34JSGVQPX5S&v=20170526";
        Log.e("Link", Link);
        temp = makeCall(Link);
        Log.e("Link ---- > ", temp);
        if (temp != null)
            photosList = (ArrayList<PictureModel>) parseFoursquare(temp);
        else photosList = null;
        return photosList;
    }

    @Override
    protected void onPreExecute() {
        // we can start a progress bar here
    }


    @Override
    protected void onPostExecute(ArrayList<PictureModel> result) {
        if (temp == null) {
            Log.e("ERROR! ", "temp is null!");
            // we have an error to the call
            // we can also stop the progress bar
        } else {
            // all things went right
            Log.e("ERROR! ", "temp is not null!");
            // parseFoursquare venues search result
                /*venuesList = (ArrayList<FoursquareModel>) parseFoursquare(temp);*/
            Log.e("ERROR! ", "photosList.size is : " + photosList.size());
            // set the results to the list
            // and show them in the xml

            /*for (FoursquareModel model : photosList) {
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

    public void setPhotoID(String photoID){
        this.photoID = photoID;
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

    private static ArrayList<PictureModel> parseFoursquare(
            final String response) {

        ArrayList<PictureModel> temp = new ArrayList<PictureModel>();
        try {

            // make an jsonObject in order to parse the response
            JSONObject jsonObject = new JSONObject(response);

            // make an jsonObject in order to parse the response
            if (jsonObject.has("response")) {
                Log.i("Link", "response darad!");
                if (jsonObject.getJSONObject("response").has("photos")) {
                    Log.i("Link", "photos darad!");
                    if (jsonObject.getJSONObject("response").getJSONObject("photos").has("items")) {
                        Log.i("Link", "items darad!");
                    }

                    JSONArray jsonArray = jsonObject.getJSONObject("response")
                            .getJSONObject("photos").getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        PictureModel photosModel = new PictureModel();
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        try {
                            if (jsonObject1.has("id")) {
                                photosModel.setId(jsonObject1.getString("id"));
                            }
                            if (jsonObject1.has("prefix")) {
                                String imagePath = jsonObject1.getString("prefix") + "130x130" +
                                        jsonObject1.getString("suffix");
                                String imagePathBig = jsonObject1.getString("prefix") + "600x600" +
                                        jsonObject1.getString("suffix");
                                photosModel.setImagePath(imagePath);
                                photosModel.setImagePathBig(imagePathBig);
                            }
                            if (jsonObject1.has("user")) {
                                String authorName = jsonObject1.getJSONObject("user").getString("firstName")
                                        + " " + jsonObject1.getJSONObject("user").getString("lastName");
                                photosModel.setAuthorName(authorName);
                            }
                            temp.add(photosModel);

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
