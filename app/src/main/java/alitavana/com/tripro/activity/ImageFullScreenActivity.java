package alitavana.com.tripro.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.ImageFullScreenAdapter;
import alitavana.com.tripro.model.PictureModel;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 07/05/2017.
 */

public class ImageFullScreenActivity extends Activity {

    ViewPager image_fulscreen_viewpager;
    ImageFullScreenAdapter imageFullScreenAdapter;
    ArrayList<PictureModel> imagePath = new ArrayList<>();
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
        imageFullScreenAdapter = new ImageFullScreenAdapter(this, imagePath);
        image_fulscreen_viewpager.setAdapter(imageFullScreenAdapter);
        image_fulscreen_viewpager.setCurrentItem(position);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
