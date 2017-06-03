package alitavana.com.tripro.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.HotelsAdapter;
import alitavana.com.tripro.gps.GPSTracker;
import alitavana.com.tripro.model.Hotel;
import alitavana.com.tripro.objJson.HotelJson;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static alitavana.com.tripro.activity.MainActivity.Adault;
import static alitavana.com.tripro.activity.MainActivity.Children;
import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;
import static alitavana.com.tripro.activity.MainActivity.Room;
import static alitavana.com.tripro.activity.MainActivity.SortingModel;

/**
 * Created by Ali Tavana on 19/04/2017.
 */

public class Hotels extends AppCompatActivity {
    List<Hotel> hotelList = new ArrayList<>();
    HotelsAdapter hotelsAdapter;
    Location currentLocation;
    //RecyclerView hotels_recyclerview;
    ListView hotels_listView;
    //RecyclerView.LayoutManager mLayoutManager;
    LinearLayout hotels_datepicker_layout;
    TextView hotels_children, hotels_adault, hotels_room, hotels_dateviewer, hotels_backbtn,
            hotels_sorting_btn, hotels_sorting_model, textviewNotFound;
    EditText searchbox;
    Button btnLoadMore;
    ProgressDialog dialog;
    GPSTracker gps;
    String cityName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        getCurrentLocation();
        getComponents();
        setClicks();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareHotelList();
                setAdapter();
            }
        }, 100);
    }

    private void getComponents() {
        cityName = getIntent().getStringExtra("cityName");
        // hotelsAdapter = new HotelsAdapter(getApplicationContext(), hotelList);
        // hotels_recyclerview = (RecyclerView) findViewById(R.id.hotels_recyclerview);
        hotels_listView = (ListView) findViewById(R.id.hotels_listView);

        searchbox = (EditText) findViewById(R.id.searchbox);
        searchbox.setHint(cityName);
        //mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        hotels_datepicker_layout = (LinearLayout) findViewById(R.id.hotels_datepicker_layout);
        hotels_children = (TextView) findViewById(R.id.hotels_children);
        hotels_adault = (TextView) findViewById(R.id.hotels_adault);
        hotels_room = (TextView) findViewById(R.id.hotels_room);
        hotels_dateviewer = (TextView) findViewById(R.id.hotels_dateviewer);
        hotels_backbtn = (TextView) findViewById(R.id.hotels_backbtn);
        hotels_sorting_model = (TextView) findViewById(R.id.hotels_sorting_model);
        textviewNotFound = (TextView) findViewById(R.id.textviewNotFound);

        hotels_sorting_btn = (TextView) findViewById(R.id.hotels_sorting_btn);
        btnLoadMore = new Button(this);
        btnLoadMore.setText(setFonts("مشاهده بیشتر"));
        btnLoadMore.setTextSize(19);
    }

    private SpannableString setFonts(String txt) {
        Typeface irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");
        SpannableString spannableString = new SpannableString(txt);
        spannableString.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spannableString;
    }

    private void setClicks() {
        hotels_datepicker_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hotels.this, DatePickerActivity.class);
                startActivity(intent);
            }
        });
        hotels_backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        hotels_sorting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Hotels.this, SortingActivity.class);
                startActivity(intent);
            }
        });
        hotels_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(Hotels.this, HotelDetailActivity.class);
                intent.putExtra("Hotel", hotelList.get(position));
                intent.putExtra("lat", currentLocation.getLatitude());
                intent.putExtra("lng", currentLocation.getLongitude());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        hotels_listView.setEmptyView(textviewNotFound);
    }

    private void setAdapter() {
        hotelsAdapter = new HotelsAdapter(this, hotelList, currentLocation);
        hotels_listView.setAdapter(hotelsAdapter);
        hotels_listView.addFooterView(btnLoadMore);
        dialog.cancel();
    }

    private void setSortingType() {
        switch (SortingModel) {
            case 1:
                hotels_sorting_model.setText("مرتب سازی: رتبه بندی");
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
            case 5:
                hotels_sorting_model.setText("مرتب سازی: نزدیکترین ها");

        }
    }

    private void prepareHotelList() {
        HotelJson hotelJson = new HotelJson();
        if (cityName!= null && !cityName.equals("")){
            hotelJson.setCityName(cityName);
            Log.d("HotelsActivity", cityName);
        }

        ArrayList<Hotel> hotelsTmp = new ArrayList<>();
        try {
            this.hotelList = hotelJson.execute().get();
            Log.e("hotelsTMp >----------", hotelsTmp.toString());

        } catch (InterruptedException e) {

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        hotels_dateviewer.setText(DateEnter.getYear() + "/" + DateEnter.getMonth() + "/" + DateEnter.getDayOfMonth() + " - "
                + DateExit.getYear() + "/" + DateExit.getMonth() + "/" + DateExit.getDayOfMonth());
        hotels_children.setText(Children + "");
        hotels_adault.setText(Adault + "");
        hotels_room.setText(Room + "");
        setSortingType();
    }
    private void getCurrentLocation(){
        gps = new GPSTracker(this);
        currentLocation = new Location("");
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

}