package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class FarmerAccountDetails extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    FirebaseUser u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_account_details);


        TextView t1 = findViewById(R.id.farmeraccountPageName);
        TextView t2 = findViewById(R.id.farmeraccountPageEmail);
        TextView t3 = findViewById(R.id.farmeraccountPageMobile);


        firebaseAuth = FirebaseAuth.getInstance();
        u = firebaseAuth.getCurrentUser();
        try {
            //Toast.makeText(AccountPage.this, u.getUid(), Toast.LENGTH_LONG).show();
            FirebaseDatabase.getInstance().getReference().child("userinfo").child("farmer").child(u.getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            UserInfo u = task.getResult().getValue(UserInfo.class);
                            t1.setText("Name- "+u.getFirstname());
                            t2.setText("Email- "+u.getEmail());
                            t3.setText("Mobile no- "+u.getMobile());

                        }
                    }
            );
        }
        catch (Exception e){
            System.out.println("AEPI"+e);
        }
    }
}