package alitavana.com.tripro.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.PictureModel;
import alitavana.com.tripro.model.TripImage;

/**
 * Created by Ali Tavana on 03/06/2017.
 */

public class HotelNoPicassoGalleryAdapter  extends BaseAdapter {
    private Context context;
    private ArrayList<TripImage> pictureList;

    public HotelNoPicassoGalleryAdapter(Context context, ArrayList<TripImage> pictureList) {
        this.context = context;
        this.pictureList = pictureList;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.adapter_hotel_gallery, null);

            // set value into textview


        } else {
            gridView = (View) convertView;
        }

        ImageView imageView = (ImageView) gridView
                .findViewById(R.id.grid_item_image);

        // Image
        //Image
        try {

            byte[] data = pictureList.get(position).getPhotoValue();
            Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
            imageView.setImageBitmap(bmp);
            Log.d("hoteladapter", "bmp: " + bmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gridView;
    }

    @Override
    public int getCount() {
        return pictureList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
