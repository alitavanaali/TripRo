package alitavana.com.tripro.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.adapter.CommentAdapter;
import alitavana.com.tripro.adapter.EmtiyazAdapter;
import alitavana.com.tripro.model.Comment;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Ali Tavana on 29/05/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    ImageView profile_imageview_back, profile_imageview;
    RecyclerView profile_review_listview;
    CommentAdapter commentAdapter;
    ArrayList<Comment> commentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/irsans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        prepareData();
        getComponents();
        setComponents();
        setAdapter();
    }

    private void getComponents(){
        profile_imageview_back = (ImageView) findViewById(R.id.profile_imageview_back);
        profile_imageview = (ImageView) findViewById(R.id.profile_imageview);

        profile_review_listview = (RecyclerView) findViewById(R.id.profile_review_listview);
        profile_review_listview.setNestedScrollingEnabled(false);
    }

    private void setComponents(){
        Picasso.with(this)
                .load("http://whatistop.com/wp-content/uploads/2016/10/photo_2016-10-07_14-04-11.jpg")
                .transform(new BlurTransformation(this))
                .error(R.mipmap.ic_launcher)
                .into(profile_imageview_back);
        Picasso.with(this)
                .load("http://whatistop.com/wp-content/uploads/2016/10/photo_2016-10-07_14-04-11.jpg")
                .error(R.mipmap.ic_launcher)
                .into(profile_imageview);
    }

    private void setAdapter(){
        commentAdapter =new CommentAdapter(this, commentList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        commentAdapter = new CommentAdapter(this, commentList);
        profile_review_listview.setLayoutManager(mLayoutManager);
        profile_review_listview.setItemAnimator(new DefaultItemAnimator());
        profile_review_listview.setAdapter(commentAdapter);
    }

    private void prepareData(){
        Comment comment1 = new Comment("رستوران شاندیز قم", "", "", "");
        Comment comment2 = new Comment("رستوران شاندیز کیش", "", "", "");
        commentList.add(comment1);
        commentList.add(comment2);

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onBackPressedHandler(View view) {
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
