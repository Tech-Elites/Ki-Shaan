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

public class SimpleListViewAdapter extends ArrayAdapter<String> {
    public SimpleListViewAdapter(@NonNull Context context, ArrayList<String> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.simple_list_view, parent, false);
        }

        String s = (String) getItem(position);
        TextView textView1 = currentItemView.findViewById(R.id.simpleListViewText);
        textView1.setText(s);
        return currentItemView;
    }
}

//To use it
//        ListView lv = findViewById(R.id.abc);
//        ArrayList<String> objects = new ArrayList<String>();
//        objects.add("grains");
//        objects.add("pulses");
//        SimpleListViewAdapter adapter = new SimpleListViewAdapter(getApplicationContext(), objects);
//        lv.setAdapter(adapter);