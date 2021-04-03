package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EachShopItemPage extends AppCompatActivity {

    String name;
    int price,qtyavail;
    String sellerid;
    int userQty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_each_shop_item_page);

        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("title");
        price = bundle.getInt("price");
        sellerid = bundle.getString("sellerid");
        qtyavail = bundle.getInt("qtyavail");


        TextView t1 = findViewById(R.id.EachItemPageTitle);
        TextView t2 = findViewById(R.id.EachItemPagePrice);
        TextView t3 = findViewById(R.id.qtyavailableText);
        t3.setText("Quantity available: "+qtyavail);
        t1.setText("Product: "+name);
        t2.setText("Rs."+Integer.toString(price)+" per kg");



    }

    public void buyThisItemClick(View view) {
        EditText t4 = findViewById(R.id.qtyInput);
        userQty = Integer.parseInt(t4.getText().toString());
        if(userQty>qtyavail){

        }else{

        }
    }
}