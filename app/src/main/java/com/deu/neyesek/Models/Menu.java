package com.deu.neyesek.Models;

public class Menu {

    private String Breakfast;
    private String Lunch;
    private String Dinner;
    private int Day;


    public Menu(String breakfast, String lunch, String dinner, int day) {
        Breakfast = breakfast;
        Lunch = lunch;
        Dinner = dinner;
        Day = day;
    }

    public Menu() {
    }

    public String getBreakfast() {
        return Breakfast;
    }

    public void setBreakfast(String breakfast) {
        Breakfast = breakfast;
    }

    public String getLunch() {
        return Lunch;
    }

    public void setLunch(String lunch) {
        Lunch = lunch;
    }

    public String getDinner() {
        return Dinner;
    }

    public void setDinner(String dinner) {
        Dinner = dinner;
    }

    public int getDay() {
        return Day;
    }

    public void setDay(int day) {
        Day = day;
    }
}
