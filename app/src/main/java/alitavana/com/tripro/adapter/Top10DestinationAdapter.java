package alitavana.com.tripro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.Destination;

/**
 * Created by Ali Tavana on 10/04/2017.
 */

public class Top10DestinationAdapter extends RecyclerView.Adapter<Top10DestinationAdapter.MyViewHolder> {
    List<Destination> top10destinationList;
    Context context;
    LayoutInflater inflater;

    public Top10DestinationAdapter(Context context, List<Destination> top10destinationList) {
        this.top10destinationList = top10destinationList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView adapter_top10_imageview;
        TextView adapter_top10_place_name;
        TextView adapter_top10_place_location;

        public MyViewHolder(View view) {
            super(view);
            adapter_top10_imageview = (ImageView) view.findViewById(R.id.adapter_top10_imageview);
            adapter_top10_place_name = (TextView) view.findViewById(R.id.adapter_top10_place_name);
            adapter_top10_place_location = (TextView) view.findViewById(R.id.adapter_top10_place_location);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_top10_destination_style, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Destination destination = top10destinationList.get(position);
        holder.adapter_top10_place_name.setText(destination.getDestination_name());
        holder.adapter_top10_place_location.setText(destination.getDestination_location());
    }

    @Override
    public int getItemCount() {
        return top10destinationList.size();
    }
}
