package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FarmerSell extends AppCompatActivity {
    Spinner spin, spin2;
    int quantity, price;
    String category, name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_sell);

        spin = findViewById(R.id.spinnerProductTypeFarmer);
        ArrayList<String> objects = new ArrayList<String>();
        objects.add("grains");
        objects.add("pulses");
        DropdownAdapter adapter = new DropdownAdapter(getApplicationContext() ,objects);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                category = objects.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spin2 = findViewById(R.id.spinnerProductNameFarmer);
        ArrayList<String> objects2 = new ArrayList<String>();
        objects2.add("rice");
        objects2.add("wheat");
        objects2.add("moongdal");
        objects2.add("uraddal");
        DropdownAdapter adapter2 = new DropdownAdapter(getApplicationContext() ,objects2);
        spin2.setAdapter(adapter2);
        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                name = objects2.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }


    public void OnClickSell(View view)
    {
        EditText pr = findViewById(R.id.priceFarmerSellProduct);
        EditText qt = findViewById(R.id.quantityProductFarmer);
        price = Integer.parseInt(pr.getText().toString());
        quantity = Integer.parseInt(qt.getText().toString());
    }

}