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

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.activity.HotelDetailActivity;
import alitavana.com.tripro.activity.RestaurantDetailActivity;
import alitavana.com.tripro.model.FoursquareModel;
import alitavana.com.tripro.model.Hotel;
import alitavana.com.tripro.model.Restaurant;

/**
 * Created by Ali Tavana on 21/04/2017.
 */

public class RestaurantAdapter extends BaseAdapter {
    ArrayList<FoursquareModel> restaurantList;
    Context context;
    LayoutInflater inflater;
    Location currentLocation;

    public RestaurantAdapter(Context context, ArrayList<FoursquareModel> restaurantList, Location location) {
        this.restaurantList = restaurantList;
        this.context = context;
        inflater = LayoutInflater.from(context);
        currentLocation = location;
    }

    @Override
    public int getCount() {
        return restaurantList.size();
    }

    @Override
    public Object getItem(int i) {
        return restaurantList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = inflater.inflate(R.layout.adapter_restaurant_list, null);

            holder.adapter_restaurant_imageview = (ImageView) convertView.findViewById(R.id.adapter_restaurant_imageview);
            holder.adapter_hotel_name = (TextView) convertView.findViewById(R.id.adapter_hotel_name);
            holder.adapter_restaurant_currency = (TextView) convertView.findViewById(R.id.adapter_restaurant_currency);
            holder.adapter_restaurant_category = (TextView) convertView.findViewById(R.id.adapter_restaurant_category);
            holder.adapter_restaurant_distance = (TextView) convertView.findViewById(R.id.adapter_restaurant_distance);
            holder.adapter_restaurant_ratingbar = (RatingBar) convertView.findViewById(R.id.adapter_restaurant_ratingbar);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }


        final FoursquareModel restaurant = restaurantList.get(position);

        // set name
        String Name = "";


        String[] arr = restaurant.getName().split("[^\\p{InArabic}]+");
        for (int i = 0; i < arr.length; i++)
            Name += arr[i] + " ";
        if (Name != null && !Name.equals("") && !Name.equals(" ")) {
            holder.adapter_hotel_name.setText(Name);
            Log.i("NameTest", Name);
        } else
            holder.adapter_hotel_name.setText(restaurant.getName());

        // set price tier
        if (restaurant.getPrice_tier() != null) {
            String price_tire = "";
            for (int i = 0; i < Integer.parseInt(restaurant.getPrice_tier()); i++)
                price_tire += "$";
            holder.adapter_restaurant_currency.setText("رده قیمت: " + price_tire);
        }
        // set category
        holder.adapter_restaurant_category.setText(restaurant.getCategory());
        // set image
        Picasso.with(context)
                .load(restaurant.getFeatured_photo())
                .error(R.mipmap.ic_launcher)
                .placeholder(R.drawable.picasso_placeholder)
                .into(holder.adapter_restaurant_imageview);

        //set rate
        if (restaurant.getRate() != null) {
            holder.adapter_restaurant_ratingbar.setRating(Float.parseFloat(restaurant.getRate()));
        } else {
            holder.adapter_restaurant_ratingbar.setRating(0f);
        }

        //calculate distance
        Location location2 = new Location("");
        location2.setLatitude(restaurant.getLatitude());
        location2.setLongitude(restaurant.getLongtitude());
        Float distance = (calculateDistance(currentLocation, location2) / 1000);
        if (distance > 1)
            holder.adapter_restaurant_distance.setText(Math.round(distance) + "km");
        else
            holder.adapter_restaurant_distance.setText(String.format("%.1f", distance) + "km");

        return convertView;
    }

    public class MyViewHolder {
        ImageView adapter_restaurant_imageview;
        TextView adapter_hotel_name;
        TextView adapter_restaurant_currency;
        TextView adapter_restaurant_category;
        TextView adapter_restaurant_distance;
        RatingBar adapter_restaurant_ratingbar;
    }

    private float calculateDistance(Location location1, Location location2) {
        return location2.distanceTo(location1);
    }
}
