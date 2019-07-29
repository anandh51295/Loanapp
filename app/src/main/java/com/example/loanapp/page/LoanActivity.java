package com.example.loanapp.page;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.loanapp.R;
import com.example.loanapp.adopter.LoanAdopter;
import com.example.loanapp.helper.DatabaseHelper;
import com.example.loanapp.model.LoanModel;

import java.util.ArrayList;

public class LoanActivity extends AppCompatActivity {
int cid;
RecyclerView recyclerView;
FloatingActionButton floatingActionButton;
    LoanAdopter adopter;
    ArrayList<LoanModel> data = new ArrayList<>();
    DatabaseHelper db;
    LoanModel ldata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        setTitle("Loan List");

        try {
            Intent i = getIntent();
            cid=i.getIntExtra("customerid",0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView=findViewById(R.id.loanrecycler);
        floatingActionButton=findViewById(R.id.fabaddloan);

        db = new DatabaseHelper(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        try {
            Cursor cursor = db.getloan(cid);
            while (cursor.moveToNext()) {
                ldata = new LoanModel(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4));
                data.add(ldata);

            }
            adopter = new LoanAdopter(data);
            recyclerView.setAdapter(adopter);
        } catch (Exception r) {
            r.printStackTrace();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanActivity.this,AddloanActivity.class);
                intent.putExtra("customerid",cid);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            data.clear();
        } catch (Exception t) {
            t.printStackTrace();
        }
        try {
            Cursor cursor = db.getloan(cid);
            while (cursor.moveToNext()) {
                ldata = new LoanModel(cursor.getInt(0),cursor.getInt(1),cursor.getString(2),cursor.getFloat(3),cursor.getString(4));
                data.add(ldata);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        adopter.notifyDataSetChanged();
        recyclerView.setAdapter(adopter);
    }
}
