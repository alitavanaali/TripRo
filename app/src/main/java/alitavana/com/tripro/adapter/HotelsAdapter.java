package alitavana.com.tripro.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import com.squareup.picasso.Picasso;

import java.util.List;

import alitavana.com.tripro.AliPersianCalendar;
import alitavana.com.tripro.R;
import alitavana.com.tripro.activity.HotelDetailActivity;
import alitavana.com.tripro.model.Hotel;
import ir.mirrajabi.persiancalendar.core.models.PersianDate;

import static alitavana.com.tripro.activity.MainActivity.DateEnter;
import static alitavana.com.tripro.activity.MainActivity.DateExit;

/**
 * Created by Ali Tavana on 19/04/2017.
 */

public class HotelsAdapter extends BaseAdapter {
    List<Hotel> hotelList;
    Context context;
    LayoutInflater inflater;
    Location currentLocation;

    public HotelsAdapter(Context context, List<Hotel> hotelList, Location location) {
        this.hotelList = hotelList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        currentLocation = location;
    }

    private void calculatedate(){

    }


    @Override
    public Object getItem(int i) {
        return hotelList.get(i);
    }

    @Override
    public int getCount() {
        return hotelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.adapter_hotel_list, null);


            holder.adapter_hotel_imageview = (ImageView) convertView.findViewById(R.id.adapter_hotel_imageview);
            holder.adapter_hotel_price = (TextView) convertView.findViewById(R.id.adapter_hotel_price);
            holder.adapter_restaurant_distance = (TextView) convertView.findViewById(R.id.adapter_restaurant_distance);
            holder.adapter_hotel_linear = (LinearLayout) convertView.findViewById(R.id.adapter_hotel_linear);
            holder.adapter_hotel_name = (TextView) convertView.findViewById(R.id.adapter_hotel_name);
            holder.adapter_hotel_ratingBar = (RatingBar) convertView.findViewById(R.id.adapter_hotel_ratingbar);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        final Hotel hotel = hotelList.get(position);

        holder.adapter_hotel_name.setText(hotel.getHotelName());
        /*if (hotel.getPhotos() != null){
            Log.d("myLog", "B");
            if (hotel.getPhotos().size() > 0){
                Picasso.with(context).load(String.valueOf( "91.99.96.10:8102/PondMS/" + hotel.getPhotos().get(0).getDownloadLink()))
                        .into(holder.adapter_hotel_imageview);
            }
        }*/
        Log.i("HotelAdapter", "Name: " + hotel.getHotelName() + " getAddress: " + hotel.getAddress());

        //price
        if (hotel.getPrices() != null && hotel.getPrices().size() >0)
            holder.adapter_hotel_price.setText(hotel.getPrices().get(0).getPrice());
        //calculate distance
        Location location2 = new Location("");
        location2.setLatitude(hotel.getLat());
        location2.setLongitude(hotel.getLng());
        Float distance = (calculateDistance(currentLocation, location2) / 1000);
        if (distance > 1)
            holder.adapter_restaurant_distance.setText(Math.round(distance) + "km");
        else
            holder.adapter_restaurant_distance.setText(String.format("%.1f", distance) + "km");

        //rate
        /*holder.adapter_hotel_ratingBar.setRating(hotel.getHotelRate());*/

        // set image
        if (hotel.getPhotos() != null) {
            if (hotel.getPhotos().size() > 0) {
                Picasso.with(context)
                        .load(String.valueOf("91.99.96.10:8102/PondMS/" + hotel.getPhotos().get(0).getDownloadLink()))
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.drawable.picasso_placeholder)
                        .into(holder.adapter_hotel_imageview);
                Log.d("hotelsadapter",String.valueOf("91.99.96.10:8102/PondMS/" + hotel.getPhotos().get(0).getDownloadLink()) + "" );
            }
        }


        return convertView;

    }

    private float calculateDistance(Location location1, Location location2) {
        return location2.distanceTo(location1);
    }

    public class MyViewHolder {

        ImageView adapter_hotel_imageview;
        TextView adapter_hotel_name;
        TextView adapter_hotel_price;
        TextView adapter_restaurant_distance;
        LinearLayout adapter_hotel_linear;
        RatingBar adapter_hotel_ratingBar;

    }
}
