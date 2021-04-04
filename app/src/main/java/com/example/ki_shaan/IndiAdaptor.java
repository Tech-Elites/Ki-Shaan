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

public class IndiAdaptor extends ArrayAdapter<OrdersClass> {
    public IndiAdaptor(@NonNull Context context, ArrayList<OrdersClass> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.indi_account_layout_list_view, parent, false);
        }

        OrdersClass s = getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.indiAccountLayoutCustEmail);
        TextView textView2 = currentItemView.findViewById(R.id.indiAccountLayoutItemName);
        TextView textView3 = currentItemView.findViewById(R.id.indiAccountLayoutPrice);
        TextView textView4 = currentItemView.findViewById(R.id.indiAccountLayoutQuantity);
        textView1.setText("Customer Email: "+s.getEmail());
        textView2.setText("Item: "+s.getName());
        textView3.setText("Price: "+s.getPrice());
        textView4.setText("Quantity: "+s.getQuantity());
        return currentItemView;
    }

}
