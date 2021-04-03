package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EachShopItemPage extends AppCompatActivity {

    String name;
    int price;
    String sellerid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_shop_item_page);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("title");
        price = bundle.getInt("price");
        sellerid = bundle.getString("sellerid");


        TextView t1 = findViewById(R.id.EachItemPageTitle);
        TextView t2 = findViewById(R.id.EachItemPagePrice);
        t1.setText("Product: "+name);
        t2.setText("Rs."+Integer.toString(price));

    }

    public void buyThisItemClick(View view) {
    }
}