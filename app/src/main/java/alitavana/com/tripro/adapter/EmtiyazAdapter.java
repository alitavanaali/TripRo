package alitavana.com.tripro.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.City;
import alitavana.com.tripro.model.Comment;

/**
 * Created by Ali Tavana on 29/05/2017.
 */

public class EmtiyazAdapter extends BaseAdapter implements Filterable {
    List<Comment> commentList;
    List<Comment> filteredComment;
    Context context;
    LayoutInflater inflater;
    private ItemFilter mFilter = new ItemFilter();

    public EmtiyazAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.filteredComment = commentList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    @Override
    public int getCount() {
        return filteredComment.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredComment.get(i);
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
            convertView = inflater.inflate(R.layout.adapter_comment_list_style, null);

            holder.adapter_comment_imageview = (ImageView) convertView.findViewById(R.id.adapter_comment_imageview);
            holder.adapter_comment_title = (TextView) convertView.findViewById(R.id.adapter_comment_title);
            holder.adapter_comment_text = (TextView) convertView.findViewById(R.id.adapter_comment_text);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.adapter_comment_title.setText(filteredComment.get(position).getCommentTitle());

        return convertView;
    }

    private class ViewHolder {
        ImageView adapter_comment_imageview;
        TextView adapter_comment_title;
        TextView adapter_comment_text;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString();

            FilterResults results = new FilterResults();

            final List<Comment> list = commentList;

            int count = list.size();
            final ArrayList<City> nlist = new ArrayList<>(count);

            String commentTitle ;
            String commentText ;

            for (int i = 0; i < count; i++) {
                commentTitle = list.get(i).getCommentTitle().toLowerCase();
                commentText = list.get(i).getCommentText().toLowerCase();
                /*if (cityName.contains(filterString) || cityCountry.contains(filterString)) {
                    nlist.add(list.get(i));
                }*/
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredComment = (ArrayList<Comment>) results.values;
            notifyDataSetChanged();
        }

    }
}
