package com.example.ki_shaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CreateGroupPage extends AppCompatActivity {

    EditText grpName,memberCap,grpCode,cGcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group_page);
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
            Toast.makeText(this, "Here", Toast.LENGTH_SHORT).show();
            FirebaseUser u= FirebaseAuth.getInstance().getCurrentUser();
            if(u!=null)
            {
                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("userids").push().setValue(u.getUid());
                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("ta").setValue(0);
                FirebaseDatabase.getInstance().getReference().child("groups").child(gName).child("te").setValue(0);
                finish();
            }

        }
    }
}