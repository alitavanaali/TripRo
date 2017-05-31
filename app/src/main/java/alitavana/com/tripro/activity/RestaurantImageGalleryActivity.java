package alitavana.com.tripro.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.HotelGalleryAdapter;
import alitavana.com.tripro.forsquare.PhotosForSquare;
import alitavana.com.tripro.model.PictureModel;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 26/05/2017.
 */

public class RestaurantImageGalleryActivity extends Activity {
    GridView hotel_gallery_grid_view;
    HotelGalleryAdapter hotelGalleryAdapter;
    ArrayList<PictureModel> pictureList = new ArrayList<>();
    ProgressDialog dialog;
    String restaurantID;
    String restaurantName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_gallery);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        restaurantID = getIntent().getStringExtra("id");
        restaurantName = getIntent().getStringExtra("name");
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        getComponents();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                prepareData();
                setAdapter();
            }
        }, 100);
    }

    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_header);
        toolbar_header.setText(restaurantName);

        hotel_gallery_grid_view = (GridView) findViewById(R.id.hotel_gallery_grid_view);
    }

    private void prepareData() {
        try {
            PhotosForSquare photosForSquare =new PhotosForSquare();
            photosForSquare.setPhotoID(restaurantID);
            pictureList = photosForSquare.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void setAdapter() {
        hotelGalleryAdapter = new HotelGalleryAdapter(this, pictureList);

        hotel_gallery_grid_view.setAdapter(hotelGalleryAdapter);
        hotel_gallery_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(RestaurantImageGalleryActivity.this, ImageFullScreenActivity.class);
                intent.putParcelableArrayListExtra("pictureList", pictureList);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        dialog.dismiss();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }
}
