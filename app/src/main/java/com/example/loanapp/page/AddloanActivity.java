package com.example.loanapp.page;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanapp.R;
import com.example.loanapp.helper.DatabaseHelper;

import java.util.Calendar;

public class AddloanActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText editText1, editText2;
    TextView textView;
    Button button;
    String sdateneed;
    int cid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addloan);
        setTitle("Add Loan");
        db = new DatabaseHelper(getApplicationContext());
        editText1 = findViewById(R.id.et_lid);
        editText2 = findViewById(R.id.et_bal);
        textView = findViewById(R.id.et_date);
        button = findViewById(R.id.btn_l);
        try {
            Intent i = getIntent();
            cid=i.getIntExtra("customerid",0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddloanActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        sdateneed = selectedyear + "-" + selectedmonth + "-" + selectedday;
                        textView.setText(sdateneed);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lid,bal,date;
                lid=editText1.getText().toString();
                bal=editText2.getText().toString();
                date=textView.getText().toString();
                if(!lid.isEmpty()&&lid.length()>=12){
                    if(!bal.isEmpty()){
                        if(date.equals("Select Date")){
                            err("Please Select Date");
                        }else{
//                            int id=Integer.parseInt(lid);
                            boolean c = db.insertloan(cid,lid, Float.parseFloat(bal), date);
                            if (c) {
                                Toast.makeText(getApplicationContext(), "Loan Added", Toast.LENGTH_LONG).show();
                                boolean d = db.insertinstallment(lid,Float.parseFloat(bal),date);
                                if(d){
                                    Log.d("Installment","added");
                                }else{
                                    Log.d("Installment","not added");
                                }
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Loan not Added", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        err("Please Enter the Balance");
                    }
                }else{
                    err("Please Enter 12 Digit Account ID");
                }
            }
        });
    }

    public void err(String error){
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
    }
}
