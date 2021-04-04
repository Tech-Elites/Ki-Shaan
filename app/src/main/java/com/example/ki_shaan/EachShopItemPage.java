package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
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
        if(userQty>qtyavail || userQty<=0){
            new AlertDialog.Builder(EachShopItemPage.this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Quantity not available.")
                    .setMessage("Please select item within quantity.")
                    .setPositiveButton("Okay",null)
                    .show();
        }else{
            Intent intent = new Intent(this, CustomerPayment.class);
            intent.putExtra("name",name);
            intent.putExtra("price",price);
            intent.putExtra("sellerid",sellerid);
            intent.putExtra("userQty",userQty);
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        }
    }
}