package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.CommentAdapter;
import alitavana.com.tripro.model.Comment;
import alitavana.com.tripro.model.FoursquareModel;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import at.blogc.android.views.ExpandableTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 07/05/2017.
 */

public class RestaurantDetailActivity extends AppCompatActivity {
    boolean FLAG_COLLAPSED = true;
    ImageView hotel_detail_back_btn, restaurant_featured_image;
    FloatingActionButton fab;
    /*ExpandableTextView expandableTextView;*/
    List<Comment> commentList = new ArrayList<>();
    RecyclerView comments_recyclerview;
    CommentAdapter commentAdapter;
    /*NestedScrollView adapter_hotel_NestedScrollView;*/
    TextView content_hotel_ettelaatehotel, content_restaurant_name, toolbar_header, content_restaurant_tier,
            content_restaurant_distance, content_restaurant_category, content_restaurant_status, content_restaurant_isopen,
            content_restaurant_address,content_restaurant_telephone, content_restaurant_website;
    RatingBar content_restaurant_ratingbar, adapter_restaurant_ratingbar2;
    LinearLayout restaurant_detail_map_linear;
    FoursquareModel restaurant;
    AppBarLayout appBarLayout;
    Location currenLocation = new Location("");
    String Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        restaurant = (FoursquareModel) getIntent().getSerializableExtra("FoursquareModel");
        currenLocation.setLatitude(getIntent().getDoubleExtra("lat", 35.6892));
        currenLocation.setLongitude(getIntent().getDoubleExtra("lng", 51.3890));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getComponents();
        setTexts();
        setClicks();
        setFonts();
        prepareComments();
        setCommentsAdapter();
    }

    private void getComponents() {
        toolbar_header = (TextView) findViewById(R.id.toolbar_title);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        fab = (FloatingActionButton) findViewById(R.id.fab);
        hotel_detail_back_btn = (ImageView) findViewById(R.id.hotel_detail_back_btn);
        restaurant_featured_image = (ImageView) findViewById(R.id.restaurant_featured_image);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        /*expandableTextView = (ExpandableTextView) findViewById(R.id.expandableTextView);*/
        comments_recyclerview = (RecyclerView) findViewById(R.id.comments_recyclerview);
        /*adapter_hotel_NestedScrollView = (NestedScrollView) findViewById(R.id.adapter_hotel_NestedScrollView);*/
        content_hotel_ettelaatehotel = (TextView) findViewById(R.id.content_hotel_ettelaatehotel);
        content_restaurant_name = (TextView) findViewById(R.id.content_restaurant_name);
        content_restaurant_tier = (TextView) findViewById(R.id.content_restaurant_tier);
        content_restaurant_distance = (TextView) findViewById(R.id.content_restaurant_distance);
        content_restaurant_category = (TextView) findViewById(R.id.content_restaurant_category);
        content_restaurant_status = (TextView) findViewById(R.id.content_restaurant_status);
        content_restaurant_isopen = (TextView) findViewById(R.id.content_restaurant_isopen);
        content_restaurant_address = (TextView) findViewById(R.id.content_restaurant_address);
        content_restaurant_telephone = (TextView) findViewById(R.id.content_restaurant_telephone);
        content_restaurant_website = (TextView) findViewById(R.id.content_restaurant_website);

        restaurant_detail_map_linear = (LinearLayout) findViewById(R.id.restaurant_detail_map_linear);
        content_restaurant_ratingbar = (RatingBar) findViewById(R.id.content_restaurant_ratingbar);
        adapter_restaurant_ratingbar2 = (RatingBar) findViewById(R.id.adapter_restaurant_ratingbar2);
    }

    private void setTexts() {
        // Name
        content_restaurant_name.setText(restaurant.getName());
        String[] arr = restaurant.getName().split("[^\\p{InArabic}]+");
        for (int i = 0; i < arr.length; i++)
            Name += arr[i] + " ";
        if (Name != null || Name != "") {
            content_restaurant_name.setText(Name);
            toolbar_header.setText(Name);
        } else {
            content_restaurant_name.setText(restaurant.getName());
            toolbar_header.setText(restaurant.getName());
        }
        //Ratingbar
        if (restaurant.getRate() != null) {
            content_restaurant_ratingbar.setRating(Float.parseFloat(restaurant.getRate()));
            adapter_restaurant_ratingbar2.setRating(Float.parseFloat(restaurant.getRate()));
        } else {
            content_restaurant_ratingbar.setRating(0f);
            adapter_restaurant_ratingbar2.setRating(0f);
        }

        //set Image
        Picasso.with(getApplicationContext())
                .load(restaurant.getFeatured_photo_big())
                .error(R.mipmap.ic_launcher)
                .into(restaurant_featured_image);

        // Tire
        if (restaurant.getPrice_tier() != null) {
            String price_tire = "";
            for (int i = 0; i < Integer.parseInt(restaurant.getPrice_tier()); i++)
                price_tire += "$";
            content_restaurant_tier.setText("رده قیمت رستوران: " + price_tire);
        }

        // set category
        content_restaurant_category.setText(restaurant.getCategory());

        //calculate distance
        Location restaurantLocation = new Location("");
        restaurantLocation.setLatitude(restaurant.getLatitude());
        restaurantLocation.setLongitude(restaurant.getLongtitude());
        content_restaurant_distance.setText((int) (calculateDistance(currenLocation, restaurantLocation) / 1000)
                + " km");

        // is open
        if (restaurant.getIsopen() != null && restaurant.getIsopen().equals("true")) {
            content_restaurant_isopen.setText("باز است");
        }
        else {
            content_restaurant_isopen.setText("بسته است");
            content_restaurant_isopen.setTextColor(Color.RED);
        }
        // status
        if (restaurant.getStatus() != null)
            content_restaurant_status.setText(restaurant.getStatus());
        else {
            content_restaurant_status.setText("");
        }

        // address
        if (restaurant.getAddress() != null) {
            content_restaurant_address.setText(restaurant.getAddress());
        }
        else {
            content_restaurant_address.setText("آدرس این محل در سیستم موجود نیست");
            content_restaurant_address.setTextColor(Color.RED);
        }

        // telephone
        if (restaurant.getTelephone() != null) {
            content_restaurant_telephone.setText(restaurant.getTelephone());
            content_restaurant_telephone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + restaurant.getTelephone()));
                    startActivity(intent);
                }
            });

        }
        else {
            content_restaurant_address.setText("موجود نیست");
            content_restaurant_address.setTextColor(Color.RED);
        }

        // website
        if (restaurant.getWebsite() != null) {
            content_restaurant_website.setText(restaurant.getWebsite());
        }
        else {
            content_restaurant_website.setText("موجود نیست");
        }
    }

    private void setClicks() {
        restaurant_detail_map_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailActivity.this, RestaurantMapActivity.class);
                intent.putExtra("lat", restaurant.getLatitude());
                intent.putExtra("lng", restaurant.getLongtitude());
                intent.putExtra("name", Name);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RestaurantDetailActivity.this, RestaurantImageGalleryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", restaurant.getId());
                intent.putExtra("name", Name);
                startActivity(intent);
            }
        });

        restaurant_featured_image
                .setOnClickListener(new View.OnClickListener() {
                                                         @Override
                                                         public void onClick(View view) {
                                                             Intent intent = new Intent(RestaurantDetailActivity.this,
                                                                     RestaurantImageGalleryActivity.class);
                                                             intent.putExtra("id", restaurant.getId());
                                                             intent.putExtra("name", Name);
                                                             intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                             startActivity(intent);
                                                         }
                                                     }
        );
    }

    private void setCommentsAdapter() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        commentAdapter = new CommentAdapter(this, commentList);
        comments_recyclerview.setLayoutManager(mLayoutManager);
        comments_recyclerview.setItemAnimator(new DefaultItemAnimator());
        comments_recyclerview.setAdapter(commentAdapter);
    }

    private void prepareComments() {
        Comment comment1 = new Comment("کیفیت غذای عالی", "یکی از بهترین رستوران هایی بود که تا حالا رفتم", "2.6", "95/12/3");
        Comment comment2 = new Comment("فضای دلنشین", "فضای رستوران خیلی باحال بود!", "2.6", "96/1/15");
        commentList.add(comment1);
        commentList.add(comment2);
    }

    private void setFonts() {
        Typeface irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");
        SpannableString spannableString = new SpannableString("دیدگاه ها");
        spannableString.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString spannableString2 = new SpannableString("اطلاعات رستوران");
        spannableString2.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private float calculateDistance(Location location1, Location location2) {
        return location2.distanceTo(location1);
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }
}
