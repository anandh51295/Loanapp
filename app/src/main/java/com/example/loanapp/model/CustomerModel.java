package com.example.loanapp.model;

import com.google.gson.annotations.SerializedName;

public class CustomerModel {
    @SerializedName("id")
    int id;
    @SerializedName("customerid")
    int customerid;
    @SerializedName("customername")
    String customername;
    @SerializedName("dob")
    String dob;

    public CustomerModel(int id,int customerid,String customername,String dob){
        this.id=id;
        this.customerid=customerid;
        this.customername=customername;
        this.dob=dob;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerid() {
        return customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
