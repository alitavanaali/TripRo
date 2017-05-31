package alitavana.com.tripro.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.activity.CityActivity;
import alitavana.com.tripro.activity.SearchCityActivity;
import alitavana.com.tripro.model.City;

import static alitavana.com.tripro.activity.MainActivity.MaghsadCity;

/**
 * Created by Ali Tavana on 12/05/2017.
 */

public class SearchCityAdapter extends BaseAdapter implements Filterable {
    List<City> cityList;
    List<City> filteredCityList;
    Context context;
    LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    public SearchCityAdapter(Context context, List<City> cityList) {
        this.cityList = cityList;
        this.filteredCityList = cityList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public int getCount() {
        return filteredCityList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredCityList.get(i);
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
            convertView = inflater.inflate(R.layout.adapter_city_search, null);

            holder.adapter_city_name = (TextView) convertView.findViewById(R.id.adapter_city_name);
            holder.adapter_city_country = (TextView) convertView.findViewById(R.id.adapter_city_country);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        City city = filteredCityList.get(position);
        holder.adapter_city_name.setText(city.getName());
        holder.adapter_city_country.setText(city.getCountry());

        return convertView;
    }

    private class ViewHolder {
        TextView adapter_city_name;
        TextView adapter_city_country;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString();

            FilterResults results = new FilterResults();

            final List<City> list = cityList;

            int count = list.size();
            final ArrayList<City> nlist = new ArrayList<>(count);

            String cityName ;
            String cityCountry ;

            for (int i = 0; i < count; i++) {
                cityName = list.get(i).getName().toLowerCase();
                cityCountry = list.get(i).getCountry().toLowerCase();
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
            filteredCityList = (ArrayList<City>) results.values;
            notifyDataSetChanged();
        }

    }
}

