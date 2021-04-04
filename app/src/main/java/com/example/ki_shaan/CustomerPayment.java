package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class CustomerPayment extends AppCompatActivity {
    String cardno;
    String edate;
    String cvv;int flag = 0;
    int groupAsset;
    EditText expirydate;
    int year,month,day;
    String name;
    int price;
    String sellerid;
    int userQty;
    String groupName="";
    int current, curqty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment);
        Bundle bundle = getIntent().getExtras();
        name = bundle.getString("name");
        price = bundle.getInt("price");
        sellerid = bundle.getString("sellerid");
        userQty = bundle.getInt("userQty");
        expirydate=findViewById(R.id.Expiry);
        final Calendar calendar=Calendar.getInstance();
        expirydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(CustomerPayment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        expirydate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void paymentPagepayment(View view) {
        EditText t1 = findViewById(R.id.cardno);
        EditText t2 = findViewById(R.id.Expiry);
        EditText t3 = findViewById(R.id.cvv);
        cardno = t1.getText().toString();
        edate = t2.getText().toString();
        cvv = t3.getText().toString();
//        pay(cardno,edate,cvv);
        addToAccounts();
    }

    void pay(String cardno, String edate,String cvv) {
        if (TextUtils.isEmpty(cardno) || TextUtils.isEmpty(edate) || TextUtils.isEmpty(cvv)) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Details not filled!")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok", null)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Success!")
                    .setMessage("Payment is successful")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addToAccounts();
//                            finish();
                        }
                    })
                    .show();
        }
    }

    void addToAccounts(){

        try {
            FirebaseDatabase.getInstance().getReference().child("accounts").child(sellerid).get().addOnCompleteListener(
                    new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            try {
                                JSONObject js = new JSONObject(task.getResult().getValue().toString());
                                current = js.getInt("totalassets");
                                //Toast.makeText(CustomerPayment.this, current+" ", Toast.LENGTH_SHORT).show();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
//                            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
                            int tempUpdate = (int) (current+0.9f*userQty*price);
                            groupAsset=(int)(userQty*price*0.1);
//                            Toast.makeText(CustomerPayment.this, groupAsset+"", Toast.LENGTH_SHORT).show();
                            FirebaseDatabase.getInstance().getReference().child("accounts").child(sellerid).child("totalassets").setValue(tempUpdate);
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            if(user!=null) {
                                OrdersClass oc = new OrdersClass(user.getEmail(), name, price, userQty);
                                HashMap<String, Object> hm = oc.getHashMap();
                                FirebaseDatabase.getInstance().getReference().child("accounts").child(sellerid).child("orders").push().setValue(hm);
                            }
                            String cat;
                            if( name.compareTo("rice")==0 || name.compareTo("wheat")==0){
                                cat="grains";
                            }
                            else{
                                cat="pulses";
                            }

                            try {
                                FirebaseDatabase.getInstance().getReference().child("products").child(cat).child(name).child(sellerid).get().addOnCompleteListener(
                                        new OnCompleteListener<DataSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DataSnapshot> task) {
                                                try {
                                                    JSONObject js = new JSONObject(task.getResult().getValue().toString());
                                                    curqty = js.getInt("quantityavailable");
                                                    Toast.makeText(CustomerPayment.this, current+" ", Toast.LENGTH_SHORT).show();
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                FirebaseDatabase.getInstance().getReference().child("products").child(cat).child(name).child(sellerid).child("quantityavailable").setValue(curqty-userQty);
                                                addGroupAsset();


                                            }
                                        }
                                );
                            }
                            catch (Exception e){
                                System.out.println("AEPI"+e);
                            }

                        }
                    }
            );
        }
        catch (Exception e){
            System.out.println("AEPI"+e);
        }





        }

        void addGroupAsset(){

            System.out.println("LODU");
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("groups");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            if (dataSnapshot1.getKey().compareTo("te") != 0 && dataSnapshot1.getKey().compareTo("ta") != 0 && dataSnapshot1.getKey().compareTo("gCode")!=0) {
                                for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                                    if (dataSnapshot2.getValue().toString().compareTo(sellerid) == 0) {

                                        flag = 1;
                                        System.out.println("AXYT"+flag);
                                        groupName = dataSnapshot.getKey();
                                        break;
                                    }
                                }
                            }
                            if(flag==1){
                                break;
                            }
                        }
                        if(flag==1){
                            break;
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            if (flag == 1) {
                //Toast.makeText(CustomerPayment.this, groupName, Toast.LENGTH_SHORT).show();
                FirebaseDatabase.getInstance().getReference().child("groups").child(groupName).get().addOnCompleteListener(
                        new OnCompleteListener<DataSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                                        int totalAssets = Integer.parseInt(task.getResult().getValue().toString());
                                try {
                                    JSONObject js = new JSONObject(task.getResult().getValue().toString());
                                    groupAsset+=js.getInt("ta");
                                            FirebaseDatabase.getInstance().getReference().child("groups").child(groupName).child("ta").setValue(groupAsset);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                Toast.makeText(CustomerPayment.this, task.getResult().getValue()+"", Toast.LENGTH_SHORT).show();
//                                        groupAsset+=totalAssets;
//

                            }
                        }
                );
            }
        }


}