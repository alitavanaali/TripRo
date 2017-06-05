package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.ImageFullScreenAdapter;
import alitavana.com.tripro.adapter.ImageFullScreenAdapterHotel;
import alitavana.com.tripro.model.PictureModel;
import alitavana.com.tripro.model.TripImage;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 06/06/2017.
 */

public class ImageFullScreenActivityHotel extends Activity {

    ViewPager image_fulscreen_viewpager;
    ImageFullScreenAdapterHotel imageFullScreenAdapter;
    ArrayList<TripImage> imagePath = new ArrayList<>();
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_imageview);
        imagePath = getIntent().getParcelableArrayListExtra ("pictureList");
        position = getIntent().getExtras().getInt("position");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Log.i("ImageFullScreenActivity", imagePath.size()+ "");
        getComponents();
    }

    private void getComponents(){
        image_fulscreen_viewpager = (ViewPager) findViewById(R.id.image_fulscreen_viewpager);
        imageFullScreenAdapter = new ImageFullScreenAdapterHotel(this, imagePath);
        image_fulscreen_viewpager.setAdapter(imageFullScreenAdapter);
        image_fulscreen_viewpager.setCurrentItem(position);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
