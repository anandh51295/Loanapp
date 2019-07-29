package com.example.loanapp.model;

import com.google.gson.annotations.SerializedName;

public class LoanModel {
    @SerializedName("id")
    int id;
    @SerializedName("custid")
    int custid;
    @SerializedName("accountid")
    String accountid;
    @SerializedName("amount")
    float amount;
    @SerializedName("date")
    String date;

    public LoanModel(int id,int custid,String accountid,float amount,String date){
        this.id=id;
        this.custid=custid;
        this.accountid=accountid;
        this.amount=amount;
        this.date=date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustid() {
        return custid;
    }

    public void setCustid(int custid) {
        this.custid = custid;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
