package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

import io.grpc.okhttp.internal.Util;

public class RegisterPage extends AppCompatActivity {

    EditText password,cPassword,lastName,firstName,email,mno;
    RadioGroup check;
    String role;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        password=findViewById(R.id.registerPassword);
        cPassword=findViewById(R.id.registerConfirmPassword);
        lastName=findViewById(R.id.registerLastName);
        firstName=findViewById(R.id.registerFirstName);
        email=findViewById(R.id.registerEmail);
        mno=findViewById(R.id.registerMobile);
        check=findViewById(R.id.registerRadioGroup);
        check.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId)
                {
                    case (R.id.registerRadioCustomer):
                        role="cust";
                        break;
                    case(R.id.registerRadioFarmer):
                        role="farmer";
                        break;
                }
            }
        });
    }

    public void registerPageSubmit(View view) {
        String p,cp,fn,ln,e,mn;
        p=password.getText().toString();
        cp=cPassword.getText().toString();
        fn=firstName.getText().toString();
        ln=lastName.getText().toString();
        e=email.getText().toString();
        mn=mno.getText().toString();
        if(TextUtils.isEmpty(p)||TextUtils.isEmpty(cp)||TextUtils.isEmpty(fn)||TextUtils.isEmpty(ln)||TextUtils.isEmpty(e)||TextUtils.isEmpty(mn))
        {
            new AlertDialog.Builder(this)
                    .setTitle("Ooops!!")
                    .setMessage("Fill all the details in order to proceed!!")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton("Ok",null)
                    .show();
        }
        else
        {
            if(p.compareTo(cp)!=0)
            {
                new AlertDialog.Builder(this)
                        .setTitle("Ooops!!")
                        .setMessage("The passwords do not match!!")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Ok",null)
                        .show();

            }
            else
            {
                if(p.length()>6)
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Ooops!!")
                            .setMessage("The passwords should atleast be 7 characters")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok",null)
                            .show();

                }
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                        }
                        else
                        {
                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Ooops!!")
                                    .setMessage("Something went wrong!!")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton("Ok",null)
                                    .show();

                        }
                    }
                });
            }
        }
    }
}