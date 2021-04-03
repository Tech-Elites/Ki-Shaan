package com.example.ki_shaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ContactUsPage extends AppCompatActivity {

    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us_page);
        b1=findViewById(R.id.submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
        });
    }
}