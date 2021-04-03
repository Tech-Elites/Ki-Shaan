package com.example.ki_shaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShopItemsCustomAdapter extends ArrayAdapter<ShopItemsClass> {
    public ShopItemsCustomAdapter(@NonNull Context context, ArrayList<ShopItemsClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.shop_list_layout, parent, false);
        }

        ShopItemsClass s = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.shoplistLayoutName);
        TextView textView2 = currentItemView.findViewById(R.id.shoplistLayoutPrice);
        textView1.setText("Product: "+s.getName());
        textView2.setText("Rs."+Integer.toString(s.getPrice()));
        return currentItemView;
    }
}
