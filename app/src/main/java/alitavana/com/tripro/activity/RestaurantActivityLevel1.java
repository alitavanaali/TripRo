package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.SearchCityAdapter;
import alitavana.com.tripro.gps.GPSTracker;
import alitavana.com.tripro.model.City;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.MabdaCity;
import static alitavana.com.tripro.activity.MainActivity.MaghsadCity;

/**
 * Created by Ali Tavana on 27/05/2017.
 */

public class RestaurantActivityLevel1 extends AppCompatActivity {
    ListView city_search_recyclerview;
    SearchCityAdapter searchCityAdapter;
    List<City> cityList = new ArrayList<>();
    List<City> filteredCityList = new ArrayList<>();
    TextView search_toolbar_clear_btn;
    EditText searchbox;
    Boolean isMaghsad;
    Button city_current_button;
    GPSTracker gps;
    Location location;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        isMaghsad = getIntent().getBooleanExtra("isMaghsad", true);
        Toolbar toolbar = (Toolbar) findViewById(R.id.search_city_hotel);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        prepareData();
        getComponents();
        setAdapters();
        setOnClicks();
    }

    private void getComponents() {
        city_search_recyclerview = (ListView) findViewById(R.id.city_search_recyclerview);
        city_current_button = (Button) findViewById(R.id.city_current_button);
        searchCityAdapter = new SearchCityAdapter(this, cityList);
        search_toolbar_clear_btn = (TextView) findViewById(R.id.search_toolbar_clear_btn);
        searchbox = (EditText) findViewById(R.id.searchbox);
        searchbox.setHint("نام استان، شهر یا مقصد");
    }

    private void setOnClicks() {
        search_toolbar_clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchbox.setText("");
            }
        });
        searchbox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchCityAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        city_search_recyclerview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                City city = (City) searchCityAdapter.getItem(position);
                Intent intent = new Intent(RestaurantActivityLevel1.this, RestaurantActivity.class);
                intent.putExtra("geoCode", city.getGeoCode());
                intent.putExtra("cityName", city.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        city_current_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantActivityLevel1.this, RestaurantActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    private void prepareData() {
        City city1 = new City();
        city1.setName("تهران");
        city1.setCountry("ایران");
        city1.setGeoCode("Tehran,IR");

        City city2 = new City();
        city2.setName("مشهد");
        city2.setCountry("ایران");
        city2.setGeoCode("Mashhad,IR");

        cityList.add(city1);
        cityList.add(city2);
    }

    private void setAdapters() {
        city_search_recyclerview.setAdapter(searchCityAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onResume() {
        super.onResume();
        searchCityAdapter.notifyDataSetChanged();
    }
}
