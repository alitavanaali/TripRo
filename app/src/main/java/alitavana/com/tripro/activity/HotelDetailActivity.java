package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.CommentAdapter;
import alitavana.com.tripro.model.Comment;
import alitavana.com.tripro.model.Hotel;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import at.blogc.android.views.ExpandableTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HotelDetailActivity extends AppCompatActivity {
    boolean FLAG_COLLAPSED = true;
    ImageView hotel_detail_back_btn;
    Location currenLocation = new Location("");
    FloatingActionButton fab;
    ExpandableTextView expandableTextView;
    List<Comment> commentList = new ArrayList<>();
    RecyclerView comments_recyclerview;
    CommentAdapter commentAdapter;
    NestedScrollView adapter_hotel_NestedScrollView;
    TextView content_hotel_ettelaatehotel, content_hotel_name, content_hotel_distance, content_hotel_address;
    LinearLayout hotel_detail_map_linear;
    Hotel hotel;
    RatingBar adapter_hotel_ratingbar, adapter_hotel_ratingbar2;
    ImageView imageimage;
    TextView toolbar_header;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getIntents();
        getComponents();
        setComponents();
        setClicks();
        setFonts();
        prepareComments();
        setCommentsAdapter();
    }

    private void setComponents(){
        // name
        content_hotel_name.setText(hotel.getName());

        // description
        expandableTextView.setText(hotel.getDescription());

        // address
        content_hotel_address.setText(hotel.getAddress());

        //calculate distance
        Location hotelLocation = new Location("");
        hotelLocation.setLatitude(hotel.getLat());
        hotelLocation.setLongitude(hotel.getLng());
        content_hotel_distance.setText((int) (calculateDistance(currenLocation, hotelLocation) / 1000)
                + " km");

        //rate
        adapter_hotel_ratingbar.setRating(hotel.getHotelRate() * 2);
        adapter_hotel_ratingbar2.setRating(hotel.getHotelRate() * 2);

        Log.i("HotelDetail", hotel.getFeatures());

        //Image
        if (hotel.getPhotos() != null) {
            if (hotel.getPhotos().size() > 0) {
                try {
                    Log.d("this is test ------>", "91.99.96.10:8102/PondMS/" + hotel.getPhotos().get(0).getDownloadLink());
                    System.out.println("91.99.96.10:8102/PondMS/" + hotel.getPhotos().get(0).getDownloadLink());
                    byte[] data = hotel.getPhotos().get(0).getPhotoValue();
                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageimage.setImageBitmap(bmp);
                    Log.d("hoteladapter", "bmp: " + bmp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("image", "null bood!");
                imageimage.setImageDrawable(getResources().getDrawable(R.drawable.picasso_placeholder));
            }
        }
    }

    private void getComponents() {
        toolbar_header = (TextView) findViewById(R.id.toolbar_title);
        toolbar_header.setText(hotel.getName());
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        fab = (FloatingActionButton) findViewById(R.id.fab);
        hotel_detail_back_btn = (ImageView) findViewById(R.id.hotel_detail_back_btn);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    FLAG_COLLAPSED = true;
                    hotel_detail_back_btn.setVisibility(View.VISIBLE);
                    toolbar_header.setVisibility(View.VISIBLE);
                } else if (verticalOffset == 0) {
                    FLAG_COLLAPSED = false;
                    hotel_detail_back_btn.setVisibility(View.INVISIBLE);
                    toolbar_header.setVisibility(View.INVISIBLE);
                } else {
                }
            }
        });
        expandableTextView = (ExpandableTextView) findViewById(R.id.expandableTextView);
        comments_recyclerview = (RecyclerView) findViewById(R.id.comments_recyclerview);
        adapter_hotel_NestedScrollView = (NestedScrollView) findViewById(R.id.adapter_hotel_NestedScrollView);
        content_hotel_ettelaatehotel = (TextView) findViewById(R.id.content_hotel_ettelaatehotel);
        content_hotel_name = (TextView) findViewById(R.id.content_hotel_name);
        content_hotel_distance = (TextView) findViewById(R.id.content_hotel_distance);
        content_hotel_address = (TextView) findViewById(R.id.content_hotel_address);
        hotel_detail_map_linear = (LinearLayout) findViewById(R.id.hotel_detail_map_linear);
        adapter_hotel_ratingbar = (RatingBar) findViewById(R.id.adapter_hotel_ratingbar);
        adapter_hotel_ratingbar2 = (RatingBar) findViewById(R.id.adapter_hotel_ratingbar2);
        imageimage= (ImageView) findViewById(R.id.imageimage);
    }

    private void getIntents(){
        hotel = (Hotel) getIntent().getSerializableExtra("Hotel");
        currenLocation.setLatitude(getIntent().getDoubleExtra("lat", 35.6892));
        currenLocation.setLongitude(getIntent().getDoubleExtra("lng", 51.3890));
    }

    private void setClicks(){
        hotel_detail_map_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, HotelMapActivity.class);
                intent.putExtra("lat", hotel.getLat());
                intent.putExtra("lng", hotel.getLng());
                intent.putExtra("name", hotel.getName());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, HotelImageGalleryActivity.class);
                intent.putExtra("Hotel", hotel);
                startActivity(intent);
            }
        });
        imageimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, HotelImageGalleryActivity.class);
                intent.putExtra("Hotel", hotel);
                startActivity(intent);
            }
        });
        expandableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableTextView.toggle();
            }
        });
    }

    private void setCommentsAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        commentAdapter = new CommentAdapter(this, commentList);
        comments_recyclerview.setLayoutManager(mLayoutManager);
        comments_recyclerview.setItemAnimator(new DefaultItemAnimator());
        comments_recyclerview.setAdapter(commentAdapter);
    }

    private void prepareComments() {
        /*Comment comment1 = new Comment("هتل خوب با برخورد مناسب", "یکی از بهترین هتل هایی بود که تا حالا رفتم", "2.6", "95/12/3");
        Comment comment2 = new Comment("تخت های کثیف ضعف بزرگ", "تخت ها متاسفانه همه کثیف بودند و رسیدگی نمی شد", "2.6", "96/1/15");
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment1);*/
    }

    private void setFonts() {
        Typeface irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");
        SpannableString spannableString = new SpannableString("دیدگاه ها");
        spannableString.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString spannableString2 = new SpannableString("اطلاعات هتل ها");
        spannableString2.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

    }

    private float calculateDistance(Location location1, Location location2) {
        return location2.distanceTo(location1);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void onBackPressedHandler(View view) {
        onBackPressed();
    }
}
