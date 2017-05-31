package alitavana.com.tripro.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import alitavana.com.tripro.R;
import alitavana.com.tripro.model.Comment;

/**
 * Created by Ali Tavana on 30/04/2017.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {
    List<Comment> commentList;
    Context context;
    LayoutInflater inflater;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.commentList = commentList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_comment_list_style, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.adapter_comment_title.setText(comment.getCommentTitle());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView adapter_comment_imageview;
        TextView adapter_comment_title;
        TextView adapter_comment_text;
        public MyViewHolder(View view) {
            super(view);
            adapter_comment_imageview = (ImageView) view.findViewById(R.id.adapter_comment_imageview);
            adapter_comment_title = (TextView) view.findViewById(R.id.adapter_comment_title);
            adapter_comment_text = (TextView) view.findViewById(R.id.adapter_comment_text);
        }
    }
}