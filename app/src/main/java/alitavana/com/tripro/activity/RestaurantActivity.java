package alitavana.com.tripro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ExecutionException;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.RestaurantAdapter;
import alitavana.com.tripro.forsquare.RestaurantForSquare;
import alitavana.com.tripro.gps.GPSTracker;
import alitavana.com.tripro.model.FoursquareModel;
import alitavana.com.tripro.model.Restaurant;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.SortingModel;

/**
 * Created by Ali Tavana on 21/04/2017.
 */

public class RestaurantActivity extends AppCompatActivity {
    ArrayList<FoursquareModel> restaurantList = new ArrayList<>();
    RestaurantAdapter restaurantAdapter;
    ListView restaurant_listview;
    TextView restaurant_backbtn, hotels_sorting_model, hotels_sorting_btn;
    EditText searchbox;
    GPSTracker gps;
    Location location;
    Button btnLoadMore;
    String geoCode, cityName;
    int offset = 1;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getIntents();
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        getCurrentLocation();
        getComponents();
        setClicks();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareRestaurantList();
                setAdapter();
            }
        }, 100);

    }

    private void getComponents() {

        restaurant_listview = (ListView) findViewById(R.id.restaurant_listview);

        restaurant_backbtn  = (TextView) findViewById(R.id.restaurant_backbtn);
        hotels_sorting_model  = (TextView) findViewById(R.id.hotels_sorting_model);
        hotels_sorting_btn  = (TextView) findViewById(R.id.hotels_sorting_btn);

        searchbox  = (EditText) findViewById(R.id.searchbox);
        if (cityName != null)
            searchbox.setHint(cityName);


        btnLoadMore = new Button(this);
        btnLoadMore.setText(setFonts("مشاهده بیشتر"));
        btnLoadMore.setTextSize(19);
        /*btnLoadMore.setBackgroundResource(R.drawable.btn_load_background_selector);*/
    }

    private void setSortingType(){
        switch (SortingModel) {
            case 1:
                hotels_sorting_model.setText("مرتب سازی: رتبه بندی");
                Collections.sort(restaurantList, new Comparator() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        FoursquareModel p1 = (FoursquareModel) o1;
                        FoursquareModel p2 = (FoursquareModel) o2;
                        return p1.getRate().compareToIgnoreCase(p2.getRate());
                    }
                });
                restaurantAdapter.notifyDataSetChanged();
                break;
            case 2:
                hotels_sorting_model.setText("مرتب سازی: قیمت، نزولی");
                break;
            case 3:
                hotels_sorting_model.setText("مرتب سازی:قیمت، صعودی");
                break;
            case 4:
                hotels_sorting_model.setText("مرتب سازی: مسافت");
                break;

        }
    }

    private void setClicks(){
        restaurant_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offset += 30;
                prepareRestaurantList();
                restaurantAdapter.notifyDataSetChanged();
            }
        });
        restaurant_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(RestaurantActivity.this, RestaurantDetailActivity.class);
                intent.putExtra("FoursquareModel", restaurantList.get(position));
                intent.putExtra("lat", location.getLatitude());
                intent.putExtra("lng", location.getLongitude());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        hotels_sorting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantActivity.this, SortingActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void setAdapter() {
        restaurantAdapter = new RestaurantAdapter(this, restaurantList, location);
        restaurant_listview.setAdapter(restaurantAdapter);
        restaurant_listview.addFooterView(btnLoadMore);
        dialog.cancel();
    }

    private void prepareRestaurantList() {
        RestaurantForSquare restaurantForSquare = new RestaurantForSquare();
        restaurantForSquare.setOffset(offset);
        restaurantForSquare.setContext(this);
        if (geoCode != null){
            restaurantForSquare.setGeoCode(geoCode);
        }
        try {
            ArrayList<FoursquareModel> newFoursquareModels = new ArrayList<>();
            newFoursquareModels = restaurantForSquare.execute().get();
            for (int number = 0; number< newFoursquareModels.size(); ++number){
                restaurantList.add(newFoursquareModels.get(number));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void getIntents(){
        geoCode = getIntent().getStringExtra("geoCode");
        cityName = getIntent().getStringExtra("cityName");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        setSortingType();

    }

    private void getCurrentLocation(){
        gps = new GPSTracker(this);
        location = new Location("");
        // check if GPS enabled
        if (gps.canGetLocation()) {
            location.setLatitude(gps.getLatitude());
            location.setLongitude(gps.getLongitude());
        } else {
            gps.showSettingsAlert();
            // set tehran lat lang
            location.setLatitude(35.6892);
            location.setLongitude(51.3890);
        }
    }

    private SpannableString setFonts(String text){
        Typeface irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                /*String result=data.getStringExtra("result");*/
                dialog = ProgressDialog.show(this, "",
                        "Loading. Please wait...", true);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        restaurantList.clear();
                        offset = 1;
                        prepareRestaurantList();
                        restaurantAdapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                }, 100);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}

