package com.warwickcodingapp.AdapterClasses;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.warwickcodingapp.ModelClasses.Comment;
import com.warwickcodingapp.ModelClasses.User;
import com.warwickcodingapp.R;
import com.warwickcodingapp.ServiceClasses.PictureServices;

import java.util.ArrayList;

public class CommentListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Comment> comments;

    public CommentListAdapter(Context context, ArrayList<Comment> allComments) {
        this.context = context;
        this.comments = allComments;
    }

    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.listview_comment_item, null);
            holder.comment = (TextView) convertView.findViewById(R.id.comment);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment c = comments.get(position);
        if (c != null) {
            holder.comment.setText(c.getComment());
        }

        return convertView;
    }

    private class ViewHolder {
        TextView comment;
    }
}
