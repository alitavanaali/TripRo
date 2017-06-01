package alitavana.com.tripro.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.data.DataHolder;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.SearchCityAdapter;
import alitavana.com.tripro.model.City;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.MabdaCity;
import static alitavana.com.tripro.activity.MainActivity.MaghsadCity;

/**
 * Created by Ali Tavana on 12/05/2017.
 */

public class SearchCityActivity extends AppCompatActivity {
    ListView city_search_recyclerview;
    SearchCityAdapter searchCityAdapter;
    List<City> cityList = new ArrayList<>();
    List<City> filteredCityList = new ArrayList<>();
    TextView search_toolbar_clear_btn;
    EditText searchbox;
    Boolean isMaghsad;
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
        getComponents();
        prepareData();
        setAdapters();
        setOnClicks();
    }

    private void getComponents() {
        city_search_recyclerview = (ListView) findViewById(R.id.city_search_recyclerview);
        /*searchCityAdapter = new SearchCityAdapter(this, cityList);*/
        search_toolbar_clear_btn = (TextView) findViewById(R.id.search_toolbar_clear_btn);
        searchbox = (EditText) findViewById(R.id.searchbox);
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
                if (isMaghsad){
                    MaghsadCity = city;
                    Log.i("SearchCity", "maghsad is: " + MaghsadCity);
                    finish();
                }
                else {
                    MabdaCity = city;
                    Log.i("SearchCity", "mabda is:"  + MabdaCity);
                    finish();
                }
            }
        });
    }

    private void prepareData() {
        City city1 = new City("اصفهان", "ایران", "فرودگاه اصفهان");
        City city2 = new City("تهران", "ایران", "فرودگاه مهراباد");
        City city3 = new City("گیلان", "ایران", "فرودگاه مرکزی");
        City city4 = new City("مشهد", "ایران", "فرودگاه مهراباد");
        City city5 = new City("کرمان", "ایران", "فرودگاه مهراباد");
        City city6 = new City("چهارمحال بختیاری", "ایران", "فرودگاه مهراباد");
        City city7 = new City("خوزستان", "ایران", "فرودگاه مهراباد");
        City city8 = new City("البرز", "ایران", "فرودگاه مهراباد");

        cityList.add(city1);
        cityList.add(city2);
        cityList.add(city3);
        cityList.add(city4);
        cityList.add(city5);
        cityList.add(city6);
        cityList.add(city7);
        cityList.add(city8);
    }

    private void setAdapters() {
        /*city_search_recyclerview.setAdapter(searchCityAdapter);*/
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
