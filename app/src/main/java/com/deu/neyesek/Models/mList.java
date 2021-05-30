package com.deu.neyesek.Models;

public class mList {
    String mKey;
// this is a model for our pull from database to decide whether a student take that class or not
    public mList(String mKey) {
        this.mKey = mKey;
    }
    public mList() {

    }

    public String getmKey() {
        return mKey;
    }

    public void setcKey(String mKey) {
        this.mKey = mKey;
    }
}
