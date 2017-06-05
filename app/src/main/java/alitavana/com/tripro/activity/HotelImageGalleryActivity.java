package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.HotelGalleryAdapter;
import alitavana.com.tripro.adapter.HotelNoPicassoGalleryAdapter;
import alitavana.com.tripro.forsquare.PhotosForSquare;
import alitavana.com.tripro.model.Hotel;
import alitavana.com.tripro.model.PictureModel;
import alitavana.com.tripro.model.TripImage;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 06/05/2017.
 */

public class HotelImageGalleryActivity extends Activity {
    GridView hotel_gallery_grid_view;
    HotelNoPicassoGalleryAdapter HotelNoPicassoGalleryAdapter;
    ArrayList<TripImage> pictureList = new ArrayList<>();
    Hotel hotel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_gallery);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        getIntents();
        getComponents();
        prepareData();
        setAdapter();
        PhotosForSquare photosForSquare =new PhotosForSquare();
        photosForSquare.setPhotoID("571cf70c498e4066e07a16f0");
        photosForSquare.execute();
    }

    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        toolbar_header.setText("گالری تصاویر");

        hotel_gallery_grid_view = (GridView) findViewById(R.id.hotel_gallery_grid_view);
    }

    private void prepareData() {
        pictureList = hotel.getPhotos();
    }

    private void getIntents(){
        hotel = (Hotel) getIntent().getSerializableExtra("Hotel");
    }

    private void setAdapter() {
        HotelNoPicassoGalleryAdapter = new HotelNoPicassoGalleryAdapter(getApplicationContext(), pictureList);

        hotel_gallery_grid_view.setAdapter(HotelNoPicassoGalleryAdapter);
        hotel_gallery_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HotelImageGalleryActivity.this, ImageFullScreenActivityHotel.class);
                intent.putParcelableArrayListExtra("pictureList", pictureList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }
}
