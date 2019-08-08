package com.example.loanapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

//import net.sqlcipher.database.SQLiteDatabase;
//import net.sqlcipher.database.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "loan.db";
    public static final String TABLE_NAME = "customertable";
    public static final String COL_01 = "ID";
    public static final String COL_02 = "CUSTOMERID";
    public static final String COL_03 = "CUSTOMERNAME";
    public static final String COL_04 = "DOB";

    public static final String TABLE2_NAME = "loantable";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "CUSTID";
    public static final String COL_3 = "ACCOUNTID";
    public static final String COL_4 = "AMOUNT";
    public static final String COL_5 = "DATE";

    public static final String TABLE3_NAME = "repaymentstable";
    public static final String COL1 = "ID";
    public static final String COL2 = "ACCOUNTID";
    public static final String COL3 = "INSTALLMENTAMOUNT";
    public static final String COL4 = "DATE";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);
//        SQLiteDatabase.loadLibs(context);
//        String dbPath = context.getDatabasePath("loan.db").getPath();
//        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath,"dbPassword", null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CUSTOMERID INTEGER,CUSTOMERNAME VARCHAR, DOB VARCHAR)");
        db.execSQL("create table " + TABLE2_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,CUSTID INTEGER,ACCOUNTID INTEGER, AMOUNT FLOAT,DATE VARCHAR)");
        db.execSQL("create table " + TABLE3_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNTID INTEGER, INSTALLMENTAMOUNT FLOAT,DATE TIMESTAMP)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE3_NAME);
    }

    public boolean insertcustomer(int id,String name,String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_02, id);
            contentValues.put(COL_03, name);
            contentValues.put(COL_04, dob);
            try {
                db.insert(TABLE_NAME, null, contentValues);
                return true;
            } catch (Exception rt) {
                rt.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean insertloan(int mcid,String lid,float amount,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_2, mcid);
            contentValues.put(COL_3, lid);
            contentValues.put(COL_4, amount);
            contentValues.put(COL_5, date);
            try {
                db.insert(TABLE2_NAME, null, contentValues);
                return true;
            } catch (Exception rt) {
                rt.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertinstallment(String acid,float amount,String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
                       ContentValues contentValues = new ContentValues();
            contentValues.put(COL2, acid);
            contentValues.put(COL3, amount);
            contentValues.put(COL4, date);
            try {
                db.insert(TABLE3_NAME, null, contentValues);
                return true;
            } catch (Exception rt) {
                rt.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cursor getcustomer() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from customertable";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            //cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getloan(int cid) {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select * from loantable where CUSTID="+cid+"";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            //cursor.moveToFirst();
        }
        return cursor;
    }

    public Cursor getinstallment() {
        SQLiteDatabase database = this.getWritableDatabase();
        String query = "select DATE,INSTALLMENTAMOUNT from repaymentstable";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            //cursor.moveToFirst();
        }
        return cursor;
    }

//    public Cursor getinstallment(String date) {
//        SQLiteDatabase database = this.getWritableDatabase();
//        //String query = "select c.CUSTOMERNAME,r.INSTALLMENTAMOUNT from repaymentstable as r,loantable as l, customertable as c where r.DATETIME='"+date+"' and l.ACCOUNTID = r.ACCOUNTID and l.CUSTID = c.CUSTOMERID";
//        String query = "select INSTALLMENTAMOUNT from repaymentstable where DATETIME= '"+date+"'";
//        Cursor cursor = database.rawQuery(query, null);
//        if (cursor != null) {
////            cursor.moveToFirst();
//        }
//        return cursor;
//    }
}
