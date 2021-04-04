package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ki_shaan.ShopActivity.allProducts;

public class FarmerAccountIndi extends AppCompatActivity {

    ListView lv;
    IndiAdaptor adaptor;
    ArrayList<OrdersClass> arrayList;
    ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_account_indi);
        p=findViewById(R.id.progressBar147);
        p.setVisibility(View.VISIBLE);
        lv=findViewById(R.id.indiAccountListFarmer);
        arrayList= new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("accounts").child(user.getUid()).child("orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    OrdersClass temp = snapshot1.getValue(OrdersClass.class);
                    arrayList.add(temp);
                }
                fillList();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    void fillList(){
        adaptor = new IndiAdaptor(this, arrayList);
        lv.setAdapter(adaptor);
        updateTotalAs();
    }

    void updateTotalAs(){

//
//
        FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
        if(u!=null)
        {
            FirebaseDatabase.getInstance().getReference().child("accounts").child(u.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful())
                    {
                        try {
                            JSONObject js=new JSONObject(task.getResult().getValue().toString());
                            int t=js.getInt("totalassets");
//                            Toast.makeText(FarmerAccountIndi.this, t+"", Toast.LENGTH_SHORT).show();
                            TextView t1 = findViewById(R.id.farmerAccountTotalAsset);
                            t1.setText("Total assets: Rs. "+t);
                            p.setVisibility(View.INVISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

        }
    }
}