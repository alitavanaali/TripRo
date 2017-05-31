package alitavana.com.tripro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.PictureModel;

/**
 * Created by Ali Tavana on 06/05/2017.
 */

public class HotelGalleryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<PictureModel> pictureList;

    public HotelGalleryAdapter(Context context, ArrayList<PictureModel> pictureList) {
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
        Picasso.with(context)
                .load(pictureList.get(position).getImagePath())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.drawable.picasso_placeholder)
                .into(imageView);
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