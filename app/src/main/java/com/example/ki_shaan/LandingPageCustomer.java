package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LandingPageCustomer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_customer);
    }

    public void onShopClick(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        startActivity(intent);
    }

    public void onAccountClick(View view) {
        Intent intent = new Intent(this, AccountPage.class);
        startActivity(intent);
    }

    public void onDonateClick(View view) {
        Intent intent = new Intent(this, DonatePage.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void onContactClick(View view) {
        Intent intent = new Intent(this, ContactUsPage.class);
        startActivity(intent);
    }
}