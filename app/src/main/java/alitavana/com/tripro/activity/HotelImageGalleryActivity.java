package alitavana.com.tripro.activity;

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
import alitavana.com.tripro.forsquare.PhotosForSquare;
import alitavana.com.tripro.model.PictureModel;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 06/05/2017.
 */

public class HotelImageGalleryActivity extends AppCompatActivity {
    GridView hotel_gallery_grid_view;
    HotelGalleryAdapter hotelGalleryAdapter;
    ArrayList<PictureModel> pictureList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_gallery);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
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
        /*PictureModel pictureModel1 = new PictureModel("http://hotelyar.com/hotel/313/pic/313/1.jpg", "پل خاجو", "علی توانا", "http://hotelyar.com/hotel/313/pic/313/1.jpg");
        PictureModel pictureModel2 = new PictureModel("http://hotelyar.com/hotel/313/pic/313/6.jpg", "سی و سه پل", "جواد راضی", "http://hotelyar.com/hotel/313/pic/313/1.jpg");
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);
        pictureList.add(pictureModel1);
        pictureList.add(pictureModel2);*/
    }

    private void setAdapter() {
        hotelGalleryAdapter = new HotelGalleryAdapter(getApplicationContext(), pictureList);

        hotel_gallery_grid_view.setAdapter(hotelGalleryAdapter);
        hotel_gallery_grid_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(HotelImageGalleryActivity.this, ImageFullScreenActivity.class);
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
