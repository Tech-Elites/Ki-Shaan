package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopActivity extends AppCompatActivity {

    ArrayList<ShopItemsClass> arrayList;
    ListView lv;
    ShopItemsCustomAdapter adapter;
    static ArrayList<ShopItemsClass> allProducts;
    ArrayList<String> grains,pulses;
    Products max;
    static ArrayList<String> sellerId;
    ArrayList<Integer> qtyavail;
    String tempSeller;
    int tempqty;
    ProgressBar p;int index=0;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        arrayList = new ArrayList<>();
        grains = new ArrayList<>();
        pulses = new ArrayList<>();
        allProducts = new ArrayList<>();
        sellerId = new ArrayList<>();
        qtyavail = new ArrayList<>();
        p=findViewById(R.id.shopActivityProgress);
        p.setVisibility(View.VISIBLE);
        getGrain();


    }

    void getGrain(){
        try {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("products").child("grains");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        grains.add(snapshot1.getKey());
                        System.out.println("XYAZ"+grains.get(0));
//                        Toast.makeText(ShopActivity.this, ""+snapshot1.getKey(), Toast.LENGTH_SHORT).show();

                    }
//                    getPulses();
                    calculateMin(grains,0);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    void getPulses(){
        try {
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("products").child("pulses");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        pulses.add(snapshot1.getKey());
                    }
                    calculateMinPulses(pulses,0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    void calculateMin(ArrayList<String> grains, int index){

            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("products").child("grains").child(grains.get(index));
            max = new Products(99999,0);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot snapshot1:snapshot.getChildren())
                    {
                        Products temp = snapshot1.getValue(Products.class);
                        if(temp.getPrice()<max.getPrice() && temp.getPrice()>0){
                            max=temp;
                            tempSeller=snapshot1.getKey();
                            tempqty=max.getQuantityavailable();
                        }
//                        Toast.makeText(ShopActivity.this, ""+snapshot1.getKey(), Toast.LENGTH_SHORT).show();

                    }
                    allProducts.add(new ShopItemsClass(grains.get(index),max.getPrice()));
                    sellerId.add(tempSeller);
                    qtyavail.add(tempqty);
                    if(index!=grains.size()-1){
                        calculateMin(grains, index+1);
                    }
                    else{
                        getPulses();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


    }

    void calculateMinPulses(ArrayList<String> grains, int index){

        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("products").child("pulses").child(grains.get(index));
        max = new Products(99999,0);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Products temp = snapshot1.getValue(Products.class);
                    if(temp.getPrice()<max.getPrice() && temp.getPrice()>0){
                        max=temp;
                        tempSeller=snapshot1.getKey();
                        tempqty=max.getQuantityavailable();
                    }
//                        Toast.makeText(ShopActivity.this, ""+snapshot1.getKey(), Toast.LENGTH_SHORT).show();

                }
                allProducts.add(new ShopItemsClass(grains.get(index),max.getPrice()));
                sellerId.add(tempSeller);
                qtyavail.add(tempqty);
                if(index!=grains.size()-1){
                    calculateMinPulses(grains, index+1);
                }
                else{
                    loadListView();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    void loadListView(){
        System.out.println("XOPP "+sellerId.get(0).toString());
        //Toast.makeText(this,allProducts.get(0).getPrice(), Toast.LENGTH_SHORT).show();
        lv = findViewById(R.id.customerShopListView);
        adapter = new ShopItemsCustomAdapter(this, allProducts);
        lv.setAdapter(adapter);
        p.setVisibility(View.INVISIBLE);
        lv.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(ShopActivity.this, EachShopItemPage.class);
                        intent.putExtra("title",allProducts.get(position).name);
                        intent.putExtra("price",allProducts.get(position).price);
                        intent.putExtra("sellerid",sellerId.get(position));
                        intent.putExtra("qtyavail",qtyavail.get(position));
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShopActivity.this).toBundle());
                        finish();
                    }
                }
        );

    }



    public void customerShopFilter(View view) {
    }

    public void customerShopSearch(View view) {
        EditText t1 = findViewById(R.id.editTextTextPersonName2);
        String name = t1.getText().toString();
        if(name.compareTo("")==0){
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Details not filled!")
                    .setMessage("Please enter a query.")
                    .setPositiveButton("Ok", null)
                    .show();
            loadListView();
        }else{

            ArrayList<ShopItemsClass> temp2 = new ArrayList<>();
            ArrayList<String> sellerIdTemp = new ArrayList<>();
            ArrayList<Integer> qtyavailTemp = new ArrayList<>();
            for(int i=0;i<allProducts.size();i++){
                if(allProducts.get(i).name.compareTo(name)==0){
                    temp2.add(allProducts.get(i));
                    sellerIdTemp.add(sellerId.get(i));
                    qtyavailTemp.add(qtyavail.get(i));
                }
            }

            lv = findViewById(R.id.customerShopListView);
            adapter = new ShopItemsCustomAdapter(this, temp2);
            lv.setAdapter(adapter);
            p.setVisibility(View.INVISIBLE);
            lv.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(ShopActivity.this, EachShopItemPage.class);
                            intent.putExtra("title",temp2.get(position).name);
                            intent.putExtra("price",temp2.get(position).price);
                            intent.putExtra("sellerid",sellerIdTemp.get(position));
                            intent.putExtra("qtyavail",qtyavailTemp.get(position));
                            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(ShopActivity.this).toBundle());
                            finish();
                        }
                    }
            );
        }
    }
}