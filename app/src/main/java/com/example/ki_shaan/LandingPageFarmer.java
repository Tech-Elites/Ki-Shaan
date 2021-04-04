package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

public class LandingPageFarmer extends AppCompatActivity {

    ProgressBar p;
    public static String grpName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page_farmer);
        p=findViewById(R.id.progressBarLandingFarmer);
        p.setVisibility(View.INVISIBLE);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.logoutmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,LoginPage.class));
                return  true;
            default:
                return false;
        }

    }



    public void farmerLandingSell(View view) {
        startActivity(new Intent(this,FarmerSell.class));
    }

    public void farmerLandingCoop(View view) {
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            p.setVisibility(View.VISIBLE);
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("groups");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int flag=0;
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                        {
                            if(dataSnapshot1.getKey().compareTo("te")!=0&&dataSnapshot1.getKey().compareTo("ta")!=0&&dataSnapshot1.getKey().compareTo("gCode")!=0)
                            {
                                for(DataSnapshot dataSnapshot2:dataSnapshot1.getChildren())
                                {
                                    if(dataSnapshot2.getValue().toString().compareTo(firebaseUser.getUid())==0)
                                    {
                                        flag=1;
                                        grpName=dataSnapshot.getKey();
                                    }
                                }
                            }
                        }
                    }
                    if(flag==1)
                    {
                        Intent intent = (new Intent(LandingPageFarmer.this,CoopLandingPage.class));
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LandingPageFarmer.this).toBundle());
                    }
                    else
                    {
                        Intent intent = (new Intent(LandingPageFarmer.this,CreateJoinGrp.class));
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LandingPageFarmer.this).toBundle());
                    }
                    p.setVisibility(View.INVISIBLE);
                }



                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
        //for now

    }

    public void farmerLandingWallet(View view) {
        Intent intent = new Intent(this, FarmerAccountIndi.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

    public void farmerLandingAccount(View view) {
        Intent intent = new Intent(this, FarmerAccountDetails.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }
}