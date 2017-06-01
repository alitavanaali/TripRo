package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;


import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.Top10DestinationAdapter;
import alitavana.com.tripro.model.City;
import alitavana.com.tripro.model.Destination;
import ir.mirrajabi.persiancalendar.PersianCalendarView;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    List<Destination> destinationList = new ArrayList<>();
    RecyclerView main_maghsad_recycler1;
    RecyclerView main_maghsad_recycler2;
    LinearLayout main_datepicker_layout;
    Top10DestinationAdapter top10DestinationAdapter;
    TextView main_dateviewer, main_children, main_room, main_adault, main_location, NavigationHeader, NavigationEmail;
    public static int Room = 1;
    public static int Adault = 1;
    public static int Children = 0;
    public static int SortingModel = 2;
    public static Boolean IsPrice1 = false;
    public static Boolean IsPrice2 = false;
    public static Boolean IsPrice3 = false;
    public static Boolean IsPrice4 = false;
    public static City MabdaCity = new City();
    public static City MaghsadCity = new City();
    public static PersianDate DateEnter;
    public static PersianDate DateExit;
    public static Boolean IsTwoWay = false;
    PersianCalendar persianCalendar;
    DrawerLayout drawer;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_awesome_toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            toolbar.showOverflowMenu();
        }
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        prepareDestinationsData();
        getComponents();
    }

    private void getComponents() {
        persianCalendar = new PersianCalendar();
        DateEnter = new PersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth() + 1, persianCalendar.getPersianDay());
        DateExit = new PersianDate(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth() + 1, persianCalendar.getPersianDay());

        top10DestinationAdapter = new Top10DestinationAdapter(this, destinationList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, true);

        main_maghsad_recycler1 = (RecyclerView) findViewById(R.id.main_maghsad_recycler1);
        main_maghsad_recycler2 = (RecyclerView) findViewById(R.id.main_maghsad_recycler2);
        main_datepicker_layout = (LinearLayout) findViewById(R.id.main_datepicker_layout);
        main_datepicker_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DatePickerActivity.class);
                startActivity(intent);
            }
        });

        main_maghsad_recycler1.setLayoutManager(mLayoutManager);
        main_maghsad_recycler1.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        main_maghsad_recycler1.setAdapter(top10DestinationAdapter);

        main_maghsad_recycler2.setLayoutManager(mLayoutManager2);
        main_maghsad_recycler2.setItemAnimator(new DefaultItemAnimator());
        main_maghsad_recycler2.setAdapter(top10DestinationAdapter);

        main_dateviewer = (TextView) findViewById(R.id.main_dateviewer);

        main_children = (TextView) findViewById(R.id.main_children);
        main_room = (TextView) findViewById(R.id.main_room);
        main_adault = (TextView) findViewById(R.id.main_adault);
        main_location = (TextView) findViewById(R.id.main_location);
        main_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchCityActivity.class);
                startActivity(intent);
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        NavigationHeader = (TextView) hView.findViewById(R.id.NavigationHeader);
        NavigationEmail = (TextView) hView.findViewById(R.id.NavigationEmail);

        NavigationHeader.setText("علی توانا");
        NavigationEmail.setText("امتیاز: 185");
    }

    public void mainClicks(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_hotels:
                intent = new Intent(MainActivity.this, HotelsActivityLevel1.class);
                startActivity(intent);
                break;
            case R.id.main_restaurants:
                intent = new Intent(MainActivity.this, RestaurantActivityLevel1.class);
                startActivity(intent);
                break;
            case R.id.main_flights:
                intent = new Intent(MainActivity.this, FlightsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_atrafe_man:
                intent = new Intent(MainActivity.this, testActivity.class);
                startActivity(intent);
                break;
            case R.id.main_menu_button:
                drawer.openDrawer(GravityCompat.END);
                break;


        }
    }


    private void prepareDestinationsData() {
        Destination destination1 = new Destination("چهل ستون", "ایران", "null");
        Destination destination2 = new Destination("پل خاجو", "ایران", "null");
        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination1);
        destinationList.add(destination2);
        destinationList.add(destination1);
        destinationList.add(destination2);
    }

    private String getPersianDateStyle(PersianDate persianDate) {
        return persianDate.getYear() + "/" + persianDate.getMonth() + "/" + persianDate.getDayOfMonth();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        main_dateviewer.setText(getPersianDateStyle(DateEnter) + " - " + getPersianDateStyle(DateExit));
        main_children.setText(Children + "");
        main_adault.setText(Adault + "");
        main_room.setText(Room + "");
        if (MaghsadCity.getName() != null)
            main_location.setText(MaghsadCity.getName() + ", " + MaghsadCity.getCountry());
        else main_location.setText("انتخاب محل");
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_profile) {
            Intent intent=new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_settings) {

        } else if (id == R.id.action_ertebat) {

        } else if (id == R.id.action_info) {

        }
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerVisible(GravityCompat.END))
            drawer.closeDrawer(GravityCompat.END);
        else
            super.onBackPressed();

    }
}

