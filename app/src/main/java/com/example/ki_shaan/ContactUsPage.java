package com.example.ki_shaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactUsPage extends AppCompatActivity {

    String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_page);
    }
    public void contactPagecontact(View view) {
        EditText t1 = findViewById(R.id.contactPageName);
        msg = t1.getText().toString();
        Submit(msg);
    }
    public void Submit(String amt) {
        if(TextUtils.isEmpty(amt)){
            new android.app.AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Details not filled!")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok", null)
                    .show();
        }
        else{
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(ContactUsPage.this);
            dlgAlert.setMessage("Your query will be resolved as soon as possible.");
            dlgAlert.setTitle("Thank you!");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText edittext = findViewById(R.id.contactPageName);
                    edittext.setText("");
                }
            }).setCancelable(true);
            dlgAlert.create().show();
        }
    }
}