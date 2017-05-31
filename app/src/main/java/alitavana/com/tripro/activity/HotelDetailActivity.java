package alitavana.com.tripro.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.CommentAdapter;
import alitavana.com.tripro.model.Comment;
import alitavana.com.tripro.typeface.CustomTypefaceSpan;
import at.blogc.android.views.ExpandableTextView;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HotelDetailActivity extends AppCompatActivity {
    boolean FLAG_COLLAPSED = true;
    ImageView hotel_detail_back_btn;
    /*TabLayout tabLayout;*/
    FloatingActionButton fab;
    ExpandableTextView expandableTextView;
    List<Comment> commentList = new ArrayList<>();
    RecyclerView comments_recyclerview;
    CommentAdapter commentAdapter;
    NestedScrollView adapter_hotel_NestedScrollView;
    TextView content_hotel_ettelaatehotel;
    LinearLayout hotel_detail_map_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getComponents();
        setClicks();
        setFonts();
        prepareComments();
        setCommentsAdapter();
    }

    private void getComponents() {
        TextView toolbar_header = (TextView) findViewById(R.id.toolbar_title);
        toolbar_header.setText("هتل هشت بهشت اصفهان");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, HotelImageGalleryActivity.class);
                startActivity(intent);
            }
        });

        hotel_detail_back_btn = (ImageView) findViewById(R.id.hotel_detail_back_btn);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    FLAG_COLLAPSED = true;
                    hotel_detail_back_btn.setVisibility(View.VISIBLE);

                    /*tabLayout.setAlpha(0.0f);
                    tabLayout.setVisibility(View.VISIBLE);
                    tabLayout.animate()
                            .setDuration(1000)
                            .translationY(0)
                            .alpha(1.0f);

                    fab.setVisibility(View.INVISIBLE);*/
                } else if (verticalOffset == 0) {
                    FLAG_COLLAPSED = false;
                    hotel_detail_back_btn.setVisibility(View.INVISIBLE);

                    /*fab.setVisibility(View.VISIBLE);*/
                } else {
                    // Somewhere in between
                    /*tabLayout.animate()
                            .setDuration(1000)
                            .translationY(0)
                            .alpha(0f);
                    tabLayout.setVisibility(View.GONE);*/
                }
            }
        });

        /*tabLayout = (TabLayout) findViewById(R.id.tabs);*/

        expandableTextView = (ExpandableTextView) findViewById(R.id.expandableTextView);
        expandableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandableTextView.toggle();
            }
        });

        comments_recyclerview = (RecyclerView) findViewById(R.id.comments_recyclerview);

        adapter_hotel_NestedScrollView = (NestedScrollView) findViewById(R.id.adapter_hotel_NestedScrollView);

        content_hotel_ettelaatehotel = (TextView) findViewById(R.id.content_hotel_ettelaatehotel);

        hotel_detail_map_linear = (LinearLayout) findViewById(R.id.hotel_detail_map_linear);
    }

    private void setClicks(){
        hotel_detail_map_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HotelDetailActivity.this, HotelMapActivity.class);
                startActivity(intent);
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
        Comment comment1 = new Comment("هتل خوب با برخورد مناسب", "یکی از بهترین هتل هایی بود که تا حالا رفتم", "2.6", "95/12/3");
        Comment comment2 = new Comment("تخت های کثیف ضعف بزرگ", "تخت ها متاسفانه همه کثیف بودند و رسیدگی نمی شد", "2.6", "96/1/15");
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment1);
        commentList.add(comment2);
        commentList.add(comment1);
    }

    private void setFonts() {
        Typeface irsans = Typeface.createFromAsset(getApplicationContext().getAssets(), "font/irsans.ttf");
        SpannableString spannableString = new SpannableString("دیدگاه ها");
        spannableString.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        SpannableString spannableString2 = new SpannableString("اطلاعات هتل ها");
        spannableString2.setSpan(new CustomTypefaceSpan("", irsans), 0, spannableString2.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

        /*tabLayout.addTab(tabLayout.newTab().setText(spannableString));
        tabLayout.addTab(tabLayout.newTab().setText(spannableString2));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    // didgah
                    adapter_hotel_NestedScrollView.smoothScrollTo(0, 1160);
                } else {
                    //ettelaate hotel
                    adapter_hotel_NestedScrollView.smoothScrollTo(0, 200);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    // didgah
                    adapter_hotel_NestedScrollView.smoothScrollTo(0, 1160);
                } else {
                    //ettelaate hotel
                    adapter_hotel_NestedScrollView.smoothScrollTo(0, 200);
                }
            }
        });*/
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public void onBackPressedHandler(View view) {
        onBackPressed();
    }
}
