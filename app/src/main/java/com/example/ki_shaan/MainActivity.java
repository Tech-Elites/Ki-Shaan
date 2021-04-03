package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        p=findViewById(R.id.progressBarMainActivity);
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
        if (u != null) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("userinfo").child("customer");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    p.setVisibility(View.INVISIBLE);
                    int flag = 1;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (u.getUid().compareTo(dataSnapshot.getKey()) == 0) {
                            flag=0;
                            finish();

                            Intent i = new Intent(MainActivity.this, LandingPageCustomer.class);
                            startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                            break;
                        }
                    }
                    if (flag == 1) {

                        finish();
                        Intent i = new Intent(MainActivity.this, LandingPageFarmer.class);
                        startActivity(i, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else
        {
            finish();
            startActivity(new Intent(this, LoginPage.class));

        }
    }
    
}