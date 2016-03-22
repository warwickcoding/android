package com.warwickcodingapp.AdapterClasses;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ShopItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<ShopItem> shopItems;

    public ShopItemAdapter(Context context, ArrayList<ShopItem> allShopItems) {
        this.context = context;
        this.shopItems = allShopItems;
    }

    @Override
    public int getCount() {
        return shopItems.size();
    }

    @Override
    public Object getItem(int position) {
        return shopItems.get(position);
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
            convertView = mInflater.inflate(R.layout.grid_shop, null);
            holder.shopImage = (ImageView) convertView.findViewById(R.id.shopImage);
            holder.price = (TextView) convertView.findViewById(R.id.price);
            holder.coins = (TextView) convertView.findViewById(R.id.coins);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ShopItem s = shopItems.get(position);
        if (s != null) {
            String coins = NumberFormat.getNumberInstance(Locale.US).format(s.getCoins());
            coins = coins.replace(",", " ");
            holder.coins.setText(s.getItemCost());
            holder.price.setText(coins + " " + context.getResources().getString(R.string.shop_textView_coins));
            holder.shopImage.setImageResource(s.getItemImage());
        }

        return convertView;
    }

    private class ViewHolder {
        TextView price;
        TextView coins;
        ImageView shopImage;
    }
}
