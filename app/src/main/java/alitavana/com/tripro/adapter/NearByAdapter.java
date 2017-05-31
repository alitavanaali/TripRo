package alitavana.com.tripro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.NearByPlaces;

/**
 * Created by Ali Tavana on 11/05/2017.
 */

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.MyViewHolder> {
    List<NearByPlaces> nearByPlacesList;
    Context context;
    LayoutInflater inflater;

    public NearByAdapter(Context context, List<NearByPlaces> nearByPlacesList) {
        this.nearByPlacesList = nearByPlacesList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_city_nearby, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NearByPlaces nearByPlace = nearByPlacesList.get(position);
        holder.adapter_nearby_name.setText(nearByPlace.getName());
        holder.adapter_nearby_destiny.setText(nearByPlace.getDestiny());
        holder.adapter_nearby_touristic.setText(nearByPlace.getTouristicPlaces());

        /*holder.adapter_hotel_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                *//*Intent intent = new Intent(context, HotelDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*//*
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return nearByPlacesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView adapter_nearby_imageview;
        TextView adapter_nearby_name;
        TextView adapter_nearby_destiny;
        TextView adapter_nearby_touristic;
        /*LinearLayout adapter_hotel_linear;*/
        public MyViewHolder(View view) {
            super(view);
            adapter_nearby_imageview = (ImageView) view.findViewById(R.id.adapter_nearby_imageview);
            adapter_nearby_destiny = (TextView) view.findViewById(R.id.adapter_nearby_destiny);
            adapter_nearby_touristic = (TextView) view.findViewById(R.id.adapter_nearby_touristic);
            adapter_nearby_name = (TextView) view.findViewById(R.id.adapter_nearby_name);
            /*adapter_hotel_linear = (LinearLayout) view.findViewById(R.id.adapter_hotel_linear);*/
        }
    }
}

