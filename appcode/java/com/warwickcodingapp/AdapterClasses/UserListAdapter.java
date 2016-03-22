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

import com.warwickcodingapp.ModelClasses.User;
import com.warwickcodingapp.R;
import com.warwickcodingapp.ServiceClasses.PictureServices;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class UserListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> users;

    public UserListAdapter(Context context, ArrayList<User> allUsers) {
        this.context = context;
        this.users = allUsers;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
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

            convertView = mInflater.inflate(R.layout.listview_user_item, null);
            holder.profilePicture = (ImageView) convertView.findViewById(R.id.profilePicture);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.email = (TextView) convertView.findViewById(R.id.email);
            holder.age = (TextView) convertView.findViewById(R.id.age);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User u = users.get(position);
        if (u != null) {
            holder.name.setText(u.getName());
            holder.email.setText(u.getEmail());
            holder.age.setText(u.getAge()+ "");
            holder.rating.setRating(u.getRating());
            holder.profilePicture.setImageBitmap(PictureServices.getCircleProfilePicture(u, context));
        }

        return convertView;
    }

    private class ViewHolder {
        TextView name;
        TextView email;
        ImageView profilePicture;
        TextView age;
        RatingBar rating;
    }
}
