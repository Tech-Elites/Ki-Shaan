package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

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

public class JoinPage extends AppCompatActivity {

    String gcode;
    EditText searchGroups;
    ArrayList<String> namesOfAllGrps;
    ArrayList<String> gCodesList;
    ListView lv;
    SimpleListViewAdapter simpleListViewAdapter;
    ProgressBar p;
    public void getGCode(int which)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter the group code");

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.input_grp_code, (ViewGroup)findViewById(android.R.id.content), false);

        final EditText input = (EditText) viewInflated.findViewById(R.id.inputGroupCodeResult);

        builder.setView(viewInflated);

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichh) {
                dialog.dismiss();
                gcode = input.getText().toString();
                if(gcode.compareTo(gCodesList.get(which))==0)
                {
                    FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
                    if(u!=null)
                    {
                        FirebaseDatabase.getInstance().getReference().child("groups").child(namesOfAllGrps.get(which)).child("userids").push().setValue(u.getUid());
                        finish();
                        new AlertDialog.Builder(JoinPage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Success!!")
                                .setMessage("You have successfully joined the grp!!")
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        startActivity(new Intent(JoinPage.this,CoopLandingPage.class));
                                    }
                                })
                                .show();

                    }
                }
                else
                {
                    dialog.cancel();
                    new AlertDialog.Builder(JoinPage.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Oops!!")
                            .setMessage("Wrong Code!!")
                            .setPositiveButton("Ok",null)
                            .show();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }
    public void search(View view)
    {
        if(searchGroups.getText().toString().compareTo("")==0)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Oops!!")
                    .setMessage("Pls enter the group name before searching")
                    .setPositiveButton("Ok",null)
                    .show();

        }
        else
        {
            p.setVisibility(View.VISIBLE);
            namesOfAllGrps.clear();
            gCodesList.clear();
            DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("groups");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot:snapshot.getChildren())
                    {
                        String t=dataSnapshot.getKey();
                        String tCode="";
                        try {
                            JSONObject js=new JSONObject(dataSnapshot.getValue().toString());
                            tCode=js.get("gCode").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        String[] temp=t.split(" ");
                        for(String c:temp)
                        {
                            if(c.compareTo(searchGroups.getText().toString())==0)
                            {
                                namesOfAllGrps.add(t);
                                gCodesList.add(tCode);
                            }
                        }

                    }
                    if(namesOfAllGrps.size()==0)
                    {
                        new AlertDialog.Builder(JoinPage.this)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setTitle("Hmmm!!")
                                .setMessage("No matches!! Try something else!")
                                .setPositiveButton("Ok",null)
                                .show();
                    }
                    else
                    {
                        p.setVisibility(View.INVISIBLE);
                        Toast.makeText(JoinPage.this, "Here", Toast.LENGTH_SHORT).show();
                        simpleListViewAdapter=new SimpleListViewAdapter(JoinPage.this,namesOfAllGrps);
                        lv.setAdapter(simpleListViewAdapter);
                        return;
                    }
                    p.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        simpleListViewAdapter=new SimpleListViewAdapter(JoinPage.this,namesOfAllGrps);
        lv.setAdapter(simpleListViewAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_join_page);
        namesOfAllGrps=new ArrayList<>();
        gCodesList=new ArrayList<>();
        searchGroups=findViewById(R.id.joinPageSearchBar);
        lv=findViewById(R.id.joinPageListView);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getGCode(position);
            }
        });
        p=findViewById(R.id.progressBarSearchGrp);
        p.setVisibility(View.INVISIBLE);
    }


}