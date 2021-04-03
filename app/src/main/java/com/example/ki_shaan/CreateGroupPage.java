package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateGroupPage extends AppCompatActivity {

    EditText grpName,memberCap,grpCode,cGcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        grpName=findViewById(R.id.createGroupName);
        memberCap=findViewById(R.id.createGroupMemberCap);
        grpCode=findViewById(R.id.createGroupJoinCode);
        cGcode=findViewById(R.id.createGroupConfirmCode);
        setContentView(R.layout.activity_create_group_page);
    }

    public void onClickCreateGroup(View view) {
        String cGcodee,gName,gCode;
        gName=grpName.getText().toString();
        gCode=grpCode.getText().toString();
        cGcodee=cGcode.getText().toString();
        //if(grpName)
    }
}