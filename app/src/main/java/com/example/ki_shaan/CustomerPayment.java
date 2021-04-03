package com.example.ki_shaan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CustomerPayment extends AppCompatActivity {

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
}