package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class AccountPage extends AppCompatActivity {
    String name,email,mobile,userrole;
    FirebaseAuth firebaseAuth;
    FirebaseUser u;

    //ProgressBar p = findViewById(R.id.customerAccountProgress);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        //p.setVisibility(View.VISIBLE);

        TextView t1 = findViewById(R.id.accountPageName);
        TextView t2 = findViewById(R.id.accountPageEmail);
        TextView t3 = findViewById(R.id.accountPageMobile);
        TextView t4 = findViewById(R.id.accountPageRole);


        firebaseAuth = FirebaseAuth.getInstance();
        u = firebaseAuth.getCurrentUser();
        try {
            //Toast.makeText(AccountPage.this, u.getUid(), Toast.LENGTH_LONG).show();
            FirebaseDatabase.getInstance().getReference().child("userinfo").child("customer").child(u.getUid()).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            try {
                                UserInfo u = task.getResult().getValue(UserInfo.class);
                                t1.setText(u.firstname);
                                Toast.makeText(AccountPage.this, u.firstname, Toast.LENGTH_LONG).show();
                            }
                            catch (Exception e){
                                System.out.println("AEPI"+e);
                            }
                            //Toast.makeText(AccountPage.this, task.getResult().getValue().toString(), Toast.LENGTH_SHORT).show();
                            System.out.print("AESI"+task.getResult().getValue().toString());

                        }
                    }
            );
        }
        catch (Exception e){
            System.out.println("AEPI"+e);
        }


    }
}