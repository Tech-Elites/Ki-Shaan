package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class CreateJoinGrp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_join_grp);
    }

    public void onCreateGroupMode(View view) {
        finish();
        startActivity(new Intent(this,CreateGroupPage.class));
    }

    public void onJoinGroupMode(View view) {
        finish();
        startActivity(new Intent(this,JoinPage.class));
    }
}