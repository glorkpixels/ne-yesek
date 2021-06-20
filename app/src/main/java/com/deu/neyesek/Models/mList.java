package com.deu.neyesek.Models;

public class mList {
    String mKey;
    String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
