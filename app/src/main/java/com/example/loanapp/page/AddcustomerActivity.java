package com.example.loanapp.page;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loanapp.R;
import com.example.loanapp.helper.DatabaseHelper;

import java.util.Calendar;

public class AddcustomerActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText ed1, ed2;
    TextView tv1;
    Button btn;
    String sdateneed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addcustomer);
        setTitle("Add Customer");
        db = new DatabaseHelper(getApplicationContext());
        ed1 = findViewById(R.id.et_cid);
        ed2 = findViewById(R.id.et_cn);
        tv1 = findViewById(R.id.et_dob);
        btn = findViewById(R.id.btn_l);
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(AddcustomerActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        selectedmonth = selectedmonth + 1;
                        sdateneed = selectedyear + "-" + selectedmonth + "-" + selectedday;
                        tv1.setText(sdateneed);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id,name,dob;
                id=ed1.getText().toString();
                name=ed2.getText().toString();
                dob=tv1.getText().toString();
                if(!id.isEmpty()&&id.length()>=8){
                    if(!name.isEmpty()&&name.length()>3){
                        if(dob.equals("Select DOB")){
                            err("Please Select DOB");
                        }else{
                            int nid=Integer.parseInt(id);
                            boolean ck = db.insertcustomer(nid, name, dob);
                            if (ck) {
                                Toast.makeText(getApplicationContext(), "Customer Added", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Customer not Added", Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        err("Please Enter Name above 3 character");
                    }
                }else{
                    err("Please Enter 8 digit Customer Number");
                }
            }
        });

    }
    public void err(String error){
        Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();
    }
}
