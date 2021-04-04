package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGroupPage extends AppCompatActivity {

    EditText grpName,memberCap,grpCode,cGcode;
    ProgressBar pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);
        pr=findViewById(R.id.progressBarCreateGrp);
        pr.setVisibility(View.INVISIBLE);
        grpName=findViewById(R.id.createGroupName);
        memberCap=findViewById(R.id.createGroupMemberCap);
        grpCode=findViewById(R.id.createGroupJoinCode);
        cGcode=findViewById(R.id.createGroupConfirmCode);
    }

    public void onClickCreateGroup(View view) {
        String cGcodee,gName,gCode;
        gName=grpName.getText().toString();
        gCode=grpCode.getText().toString();
        cGcodee=cGcode.getText().toString();

        if(gName.compareTo("")==0||gCode.compareTo("")==0||cGcodee.compareTo("")==0)
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Oops!!")
                    .setMessage("Fill in all the details to proceed!!")
                    .show();
        }
        else
        {
            FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
            if(u!=null)
            {
                pr.setVisibility(View.VISIBLE);
                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            pr.setVisibility(View.INVISIBLE);
                            Toast.makeText(CreateGroupPage.this, "Here!", Toast.LENGTH_SHORT).show();
                            if(task.getResult().getValue()==null)
                            {
                                LandingPageFarmer.grpName=gName;
                                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("userids").push().setValue(u.getUid());
                                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("ta").setValue(0);
                                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("te").setValue(0);
                                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("gCode").setValue(gCode);
                                new AlertDialog.Builder(CreateGroupPage.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Success!!")
                                        .setMessage("The grp name has been registered!!")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        })
                                        .show();

                                finish();
                            }
                            else
                            {
                                new AlertDialog.Builder(CreateGroupPage.this)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Oops!!")
                                        .setMessage("Choose another name!! This name already exists!")
                                        .show();

                            }
                        }
                    }
                });
            }

        }
    }
}