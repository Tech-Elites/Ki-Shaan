package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerPayment extends AppCompatActivity {
    String cardno;
    String edate;
    String cvv;
    EditText expirydate;
    int year,month,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_payment);
        expirydate=findViewById(R.id.Expiry);
        final Calendar calendar=Calendar.getInstance();
        expirydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(CustomerPayment.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        expirydate.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
    }
    public void paymentPagepayment(View view) {
        EditText t1 = findViewById(R.id.cardno);
        EditText t2 = findViewById(R.id.Expiry);
        EditText t3 = findViewById(R.id.cvv);
        cardno = t1.getText().toString();
        edate = t2.getText().toString();
        cvv = t3.getText().toString();
        pay(cardno,edate,cvv);
    }

    void pay(String cardno, String edate,String cvv) {
        if (TextUtils.isEmpty(cardno) || TextUtils.isEmpty(edate) || TextUtils.isEmpty(cvv)) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Details not filled!")
                    .setMessage("Fill all the details to proceed.")
                    .setPositiveButton("Ok", null)
                    .show();
        } else {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .setTitle("Success!")
                    .setMessage("Payment is successful")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }
    }
}