package com.example.ki_shaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DonatePage extends AppCompatActivity {

    Button btn1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_page);
        btn1=findViewById(R.id.button4);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(DonatePage.this);
                dlgAlert.setMessage("Thank you for your valuable donation!!");
                dlgAlert.setTitle("Thank you!");
                dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText edittext = findViewById(R.id.DontationPageAmount);
                        edittext.setText("");
                    }
                }).setCancelable(true);
                dlgAlert.create().show();
            }
        });
    }

    public void onClickDonate(View view) {
    }
}