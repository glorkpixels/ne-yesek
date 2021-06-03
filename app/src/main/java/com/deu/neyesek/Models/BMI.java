package com.deu.neyesek.Models;

public class BMI {
    private double weight;
    private double height;
    private double bmi;

    public BMI(double weight, double height, double bmi) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public BMI() {
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }
}
