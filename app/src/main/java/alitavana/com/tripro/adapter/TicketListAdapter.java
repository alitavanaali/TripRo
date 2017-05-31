package alitavana.com.tripro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.Ticket;

/**
 * Created by Ali Tavana on 13/05/2017.
 */

public class TicketListAdapter  extends BaseAdapter implements Filterable {
    List<Ticket> ticketList;
    List<Ticket> filteredTicketList;
    Context context;
    LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    public TicketListAdapter(Context context, List<Ticket> ticketList) {
        this.ticketList = ticketList;
        this.filteredTicketList = ticketList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public int getCount() {
        return filteredTicketList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredTicketList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_ticket_list, null);

            holder.adapter_ticket_price = (TextView) convertView.findViewById(R.id.adapter_ticket_price);
            holder.adapter_ticket_duration = (TextView) convertView.findViewById(R.id.adapter_ticket_duration);
            holder.adapter_ticket_time = (TextView) convertView.findViewById(R.id.adapter_ticket_time);
            holder.adapter_ticket_airport = (TextView) convertView.findViewById(R.id.adapter_ticket_airport);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Ticket ticket = filteredTicketList.get(position);
        holder.adapter_ticket_price.setText(ticket.getPrice());
        holder.adapter_ticket_duration.setText(ticket.getDuration());
        holder.adapter_ticket_time.setText(ticket.getTimeIn() + " - " + ticket.getTimeOut());
        holder.adapter_ticket_airport.setText(ticket.getAirportIn() + " ، " + ticket.getAirportOut());

        return convertView;
    }

    private class ViewHolder {
        TextView adapter_ticket_price;
        TextView adapter_ticket_duration;
        TextView adapter_ticket_time;
        TextView adapter_ticket_airport;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString();

            FilterResults results = new FilterResults();

            final List<Ticket> list = ticketList;

            int count = list.size();
            final ArrayList<Ticket> nlist = new ArrayList<>(count);

            String cityName ;
            String cityCountry ;

            for (int i = 0; i < count; i++) {
                cityName = list.get(i).getPrice().toLowerCase();
                cityCountry = list.get(i).getAirportIn().toLowerCase();
                if (cityName.contains(filterString) || cityCountry.contains(filterString)) {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredTicketList = (ArrayList<Ticket>) results.values;
            notifyDataSetChanged();
        }

    }
}


