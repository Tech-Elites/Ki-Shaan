package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.json.JSONException;
import org.json.JSONObject;

public class BorrowMoneyFarmer extends AppCompatActivity {

    TextView assetInfoIndiGrp;
    EditText borrowAmount,borrowReason;
    ProgressBar progressBarBorrowMoney;
    String temp="";
    int totalAssets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_money_farmer);
        assetInfoIndiGrp=findViewById(R.id.borrowMoneyTotalAssets);
        progressBarBorrowMoney=findViewById(R.id.progressBarBorrowMoney);
        progressBarBorrowMoney.setVisibility(View.INVISIBLE);
        borrowAmount=findViewById(R.id.borrowMoneyAskHelp);
        borrowReason=findViewById(R.id.borrowMoneyWhyHelp);
        fillTheTextField();

    }
    void fillTheTextField()
    {
        progressBarBorrowMoney.setVisibility(View.VISIBLE);

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("groups").child(LandingPageFarmer.grpName);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot:snapshot.getChildren()) {

                    if(dataSnapshot.getKey().compareTo("ta")==0) {
                        temp="Total asset owned by your grp-\n "+dataSnapshot.getValue().toString();
                        totalAssets=(int)Double.parseDouble(dataSnapshot.getValue().toString());
                    }



                }
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

                                    temp+="\nTotal asset owned by you- \n"+t;
                                    assetInfoIndiGrp.setText(temp);
                                    progressBarBorrowMoney.setVisibility(View.INVISIBLE);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onClickBorrowMoney(View view) {
        Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
        String amount,borrowReasonn;
        amount=borrowAmount.getText().toString();
        borrowReasonn=borrowReason.getText().toString();
        if(!TextUtils.isEmpty(amount)&&!TextUtils.isEmpty(amount))
        {
            int n=(int)Double.parseDouble(borrowAmount.getText().toString());

            //voting implementation here
            if(n<totalAssets)
            {
                int r=totalAssets-n;
                FirebaseDatabase.getInstance().getReference().child("groups").child(LandingPageFarmer.grpName).child("ta").setValue(r);
                FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
                if(u!=null) {
                    FirebaseDatabase.getInstance().getReference().child("accounts").child(u.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()) {
                                try {
                                    JSONObject js = new JSONObject(task.getResult().getValue().toString());
                                    int t = js.getInt("totalassets");
                                    t+= n;
                                    FirebaseDatabase.getInstance().getReference().child("accounts").child(u.getUid()).child("totalassets").setValue(t);
                                    assetInfoIndiGrp.setText("");
                                    new AlertDialog.Builder(BorrowMoneyFarmer.this)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Success!!")
                                            .setMessage("Money successfully borrowed")
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            })
                                            .show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });
                }

            }
            else
            {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setMessage("The amount is too much!!")
                        .setTitle("Oops!!")
                        .setPositiveButton("Ok",null)
                        .show();
            }


        }

    }
}