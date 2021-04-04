package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CoopLandingPage extends AppCompatActivity {

    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coop_landing_page);
        tx=findViewById(R.id.coopLandingGroupName);
        tx.setText(LandingPageFarmer.grpName);
    }
    public void onClickAccount(View view)
    {
        startActivity(new Intent(this,GroupAccounts.class));
    }

}