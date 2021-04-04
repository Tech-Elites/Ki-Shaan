package com.example.ki_shaan;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DonatePage extends AppCompatActivity {

    Button btn1;
    String amt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate_page);
    }

    public void donatePagedonate(View view) {
        EditText t1 = findViewById(R.id.DontationPageAmount);
        amt = t1.getText().toString();
        Donate(amt);
    }
    public void Donate(String amt) {
        if(TextUtils.isEmpty(amt)){
            new android.app.AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setTitle("Details not filled!")
                .setMessage("Fill all the details to proceed.")
                .setPositiveButton("Ok", null)
                .show();
        }
        else{
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
    }
}