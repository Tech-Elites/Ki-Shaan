package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GroupAccounts extends AppCompatActivity {


    ArrayList<String> namesOfAllUsers;
    ArrayList<String> usrIds;
    ListView lv;
    SimpleListViewAdapter simpleListViewAdapter;
    ProgressBar p;
    TextView tx;
    public void fill()
    {

            p.setVisibility(View.VISIBLE);
            namesOfAllUsers.clear();
            usrIds.clear();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("groups").child(LandingPageFarmer.grpName);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren()) {
                        if(dataSnapshot.getKey().compareTo("userids")==0)
                        {
                            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                            {
                                Toast.makeText(GroupAccounts.this, ""+dataSnapshot1.getValue().toString(), Toast.LENGTH_SHORT).show();
                                usrIds.add(dataSnapshot1.getValue().toString());
                            }
                        }
                        else
                        {
                            if(dataSnapshot.getKey().compareTo("ta")==0) {
                                tx.setText("Total asset- \nRs."+dataSnapshot.getValue().toString());
                            }


                        }
                    }
                    getNames(usrIds,0);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            //simpleListViewAdapter=new SimpleListViewAdapter(GroupAccounts.this, namesOfAllUsers);
            //lv.setAdapter(simpleListViewAdapter);
    }
    void getNames(ArrayList<String> userid,int index)
    {


        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("userinfo").child("farmer");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp="";
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    if(snapshot1.getKey().compareTo(userid.get(index))==0)
                    {
                        UserInfo u=snapshot1.getValue(UserInfo.class);
                        temp=u.getFirstname()+" "+u.getLastname();
                        namesOfAllUsers.add(temp);
                    }

                }
                if(index!=userid.size()-1){
                    getNames( userid,index+1);
                }
                else{
                    p.setVisibility(View.INVISIBLE);
                    simpleListViewAdapter=new SimpleListViewAdapter(GroupAccounts.this,namesOfAllUsers);
                    lv.setAdapter(simpleListViewAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_accounts);
        namesOfAllUsers =new ArrayList<>();
        usrIds=new ArrayList<>();
        tx=findViewById(R.id.grpAccountTotalAsset);
        lv=findViewById(R.id.listOfGrpMembersAccounts);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        p=findViewById(R.id.progressBarGrpAccount);
        p.setVisibility(View.INVISIBLE);
        fill();

    }
}