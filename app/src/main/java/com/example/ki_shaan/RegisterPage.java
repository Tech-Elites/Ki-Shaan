package com.example.ki_shaan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

import io.grpc.okhttp.internal.Util;

public class RegisterPage extends AppCompatActivity {

    EditText password,cPassword,lastName,firstName,email,mno;
    RadioGroup check;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        password=findViewById(R.id.registerPassword);
        progressBar=findViewById(R.id.progressBarRegister);
        progressBar.setVisibility(View.INVISIBLE);
        cPassword=findViewById(R.id.registerConfirmPassword);
        lastName=findViewById(R.id.registerLastName);
        firstName=findViewById(R.id.registerFirstName);
        email=findViewById(R.id.registerEmail);
        mno=findViewById(R.id.registerMobile);
        check=findViewById(R.id.registerRadioGroup);

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
                if(p.length()<7)
                {
                    new AlertDialog.Builder(this)
                            .setTitle("Ooops!!")
                            .setMessage("The passwords should atleast be 7 characters")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton("Ok",null)
                            .show();

                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(e,p).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                int id=check.getCheckedRadioButtonId();
                                RadioButton r=findViewById(id);
                                UserInfo us=new UserInfo(fn,ln,mn,e,r.getText().toString(),"null");
                                HashMap<String,Object> usMap=us.getHashMap();
                                Products p=new Products(-1,0);
                                HashMap<String,Object> pMap=p.getHashMap();
                                FirebaseUser u=FirebaseAuth.getInstance().getCurrentUser();
                                if(u!=null)
                                {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    FirebaseDatabase.getInstance().getReference().child("userinfo").child(r.getText().toString()).child(u.getUid()).setValue(usMap);
                                    if(r.getText().toString().compareTo("farmer")==0)
                                    {
                                        FirebaseDatabase.getInstance().getReference().child("products").child("grains").child("wheat").child(u.getUid()).setValue(pMap);
                                        FirebaseDatabase.getInstance().getReference().child("products").child("grains").child("rice").child(u.getUid()).setValue(pMap);
                                        FirebaseDatabase.getInstance().getReference().child("products").child("pulses").child("uraddal").child(u.getUid()).setValue(pMap);
                                        FirebaseDatabase.getInstance().getReference().child("products").child("pulses").child("moongdal").child(u.getUid()).setValue(pMap);
                                        FirebaseDatabase.getInstance().getReference().child("accounts").child(u.getUid()).child("totalassets").setValue(0);
                                        new AlertDialog.Builder(RegisterPage.this)
                                                .setTitle("Success!!")
                                                .setMessage("User Registered Successfully!!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                        startActivity(new Intent(RegisterPage.this,LandingPageFarmer.class));
                                                    }
                                                })
                                                .show();

                                    }
                                    else{
                                        new AlertDialog.Builder(RegisterPage.this)
                                                .setTitle("Success!!")
                                                .setMessage("User Registered Successfully!!")
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        finish();
                                                        startActivity(new Intent(RegisterPage.this,LandingPageCustomer.class));
                                                    }
                                                })
                                                .show();
                                    }

                                }

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
}