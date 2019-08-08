package com.example.loanapp;

import android.app.ActivityManager;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.loanapp.adopter.CustomerAdopter;
import com.example.loanapp.helper.DatabaseHelper;
import com.example.loanapp.helper.Remainder;
import com.example.loanapp.model.CustomerModel;
import com.example.loanapp.page.AddcustomerActivity;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fab;
    CustomerAdopter adopter;
    ArrayList<CustomerModel> data = new ArrayList<>();
    DatabaseHelper db;
    CustomerModel cdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.customerRecycler);
        db = new DatabaseHelper(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        try {
            Cursor cursor = db.getcustomer();
            while (cursor.moveToNext()) {
                cdata = new CustomerModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
                data.add(cdata);

            }
            adopter = new CustomerAdopter(data);
            recyclerView.setAdapter(adopter);
        } catch (Exception r) {
            r.printStackTrace();
        }
        fab = findViewById(R.id.addcust);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddcustomerActivity.class);
                startActivity(intent);
            }
        });

        if (!isServiceRunning(Remainder.class)) {
            Intent intent = new Intent(MainActivity.this, Remainder.class);
            intent.setAction(Remainder.ACTION_START_FOREGROUND_SERVICE);
            startService(intent);
        }

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

            Cursor cursor = db.getcustomer();
            while (cursor.moveToNext()) {
                cdata = new CustomerModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3));
                data.add(cdata);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        adopter.notifyDataSetChanged();
        recyclerView.setAdapter(adopter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        // Retrieve the SearchView and plug it into SearchManager
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search(searchView);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adopter.getFilter().filter(newText);
                return true;
            }
        });
    }

    public boolean isServiceRunning(Class serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
