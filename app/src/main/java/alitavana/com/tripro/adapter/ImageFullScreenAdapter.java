package alitavana.com.tripro.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.PictureModel;

/**
 * Created by Ali Tavana on 07/05/2017.
 */

public class ImageFullScreenAdapter extends PagerAdapter {

    private Activity _activity;
    private ArrayList<PictureModel> _imagePaths;
    private LayoutInflater inflater;

    // constructor
    public ImageFullScreenAdapter(Activity activity,
                                  ArrayList<PictureModel> imagePaths) {
        this._activity = activity;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return this._imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView adapter_slide_imageview;
        TextView adapter_slide_image_information;
        TextView adapter_slide_image_authorname;
        ImageView adapter_slide_author_imageview;
        Button btnClose;

        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.adapter_fullscreen_image, container,
                false);

        adapter_slide_imageview = (ImageView) viewLayout.findViewById(R.id.adapter_slide_imageview);
        adapter_slide_author_imageview = (ImageView) viewLayout.findViewById(R.id.adapter_slide_author_imageview);
        adapter_slide_image_information = (TextView) viewLayout.findViewById(R.id.adapter_slide_image_information);
        adapter_slide_image_authorname = (TextView) viewLayout.findViewById(R.id.adapter_slide_image_authorname);
        btnClose = (Button) viewLayout.findViewById(R.id.btnClose);

        Picasso.with(_activity)
                .load(_imagePaths.get(position).getImagePathBig())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.drawable.picasso_placeholder)
                .into(adapter_slide_imageview);
        Picasso.with(_activity)
                .load(_imagePaths.get(position).getAuthorName())
                .error(R.mipmap.ic_launcher)
                .into(adapter_slide_author_imageview);
        adapter_slide_image_information.setText(_imagePaths.get(position).getImageInformation());
        adapter_slide_image_authorname.setText(_imagePaths.get(position).getAuthorName());

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _activity.finish();
            }
        });

        ((ViewPager) container).addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}