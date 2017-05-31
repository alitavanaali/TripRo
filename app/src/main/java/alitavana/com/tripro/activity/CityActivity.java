package alitavana.com.tripro.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.NearByAdapter;
import alitavana.com.tripro.model.NearByPlaces;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 10/05/2017.
 */

public class CityActivity extends AppCompatActivity {
    NearByAdapter nearByAdapter;
    List<NearByPlaces> nearByPlaces = new ArrayList<>();
    RecyclerView nearby_recyclerview;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_city);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getComponents();
        prepareData();
        setAdapters();
    }

    private void prepareData() {
        NearByPlaces nearByPlace = new NearByPlaces("شاهین شهر", "20 کیلومتر تا اصفهان", "جاذبه ها: موزه ایران شناسی، مناره...");
        NearByPlaces nearByPlace2 = new NearByPlaces("نظنز", "85 کیلومتر تا اصفهان", "جاذبه ها: موزه ایران شناسی، مناره...");
        nearByPlaces.add(nearByPlace);
        nearByPlaces.add(nearByPlace2);
        nearByPlaces.add(nearByPlace);
        nearByPlaces.add(nearByPlace2);
        nearByPlaces.add(nearByPlace);

    }

    private void getComponents() {
        nearby_recyclerview = (RecyclerView) findViewById(R.id.nearby_recyclerview);
        mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
    }

    private void setAdapters() {
        nearByAdapter = new NearByAdapter(getApplicationContext(), nearByPlaces);
        nearby_recyclerview.setLayoutManager(mLayoutManager);
        nearby_recyclerview.setItemAnimator(new DefaultItemAnimator());
        nearby_recyclerview.setAdapter(nearByAdapter);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }

}
