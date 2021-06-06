package com.deu.neyesek.Models;

public class Ingredient {

    private String IngredientKey;
    private String TurkishName;
    private String Calorie;
    private String ServingSize;
    private String Protein;
    private String Fat;
    private String Sugar;
    private String Sodium;
    private String EnglishName;
    private String Carbohydrates;
    private String Cholesterol;
    private String SaturatedFat;
    private String Fiber;
    private String Potassium;

    public Ingredient() {
    }

    public Ingredient(String turkishName, String calorie, String servingSize, String protein, String fat, String sugar, String sodium, String englishName, String carbohydrates, String cholesterol, String saturatedFat, String fiber, String potassium) {
        TurkishName = turkishName;
        Calorie = calorie;
        ServingSize = servingSize;
        Protein = protein;
        Fat = fat;
        Sugar = sugar;
        Sodium = sodium;
        EnglishName = englishName;
        Carbohydrates = carbohydrates;
        Cholesterol = cholesterol;
        SaturatedFat = saturatedFat;
        Fiber = fiber;
        Potassium = potassium;
    }

    public String getTurkishName() {
        return TurkishName;
    }

    public void setTurkishName(String turkishName) {
        TurkishName = turkishName;
    }

    public String getCalorie() {
        return Calorie;
    }

    public void setCalorie(String calorie) {
        Calorie = calorie;
    }

    public String getServingSize() {
        return ServingSize;
    }

    public void setServingSize(String servingSize) {
        ServingSize = servingSize;
    }

    public String getProtein() {
        return Protein;
    }

    public void setProtein(String protein) {
        Protein = protein;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String getSodium() {
        return Sodium;
    }

    public void setSodium(String sodium) {
        Sodium = sodium;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getCarbohydrates() {
        return Carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        Carbohydrates = carbohydrates;
    }

    public String getCholesterol() {
        return Cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        Cholesterol = cholesterol;
    }

    public String getSaturatedFat() {
        return SaturatedFat;
    }

    public void setSaturatedFat(String saturatedFat) {
        SaturatedFat = saturatedFat;
    }

    public String getFiber() {
        return Fiber;
    }

    public void setFiber(String fiber) {
        Fiber = fiber;
    }

    public String getPotassium() {
        return Potassium;
    }

    public void setPotassium(String potassium) {
        Potassium = potassium;
    }

    public String getIngredientKey() {
        return IngredientKey;
    }

    public void setIngredientKey(String ingredientKey) {
        IngredientKey = ingredientKey;
    }


    public String getStringOfValues(){
/*
        private String EnglishName;
        private String IngredientKey;
        private String TurkishName;
        private String Calorie;
        private String ServingSize;

        private String Protein;
        private String Fat;
        private String Sugar;
        private String Sodium;

        private String Carbohydrates;
        private String Cholesterol;
        private String SaturatedFat;
        private String Fiber;

        private String Potassium;*/
        return  "Carbohydrates: " + getCarbohydrates() + "\n" + "Protein: " + getProtein() + "\n" + "Fat: " + getFat() + "\n" + "Sugar: " + getSugar() + "\n" + "Sodium: " + getSodium() + "\n" + "Cholesterol: " + getCholesterol() + "\n"
                + "SaturatedFat: " + getSaturatedFat() + "\n"  + "Fiber: " + getFiber() + "\n" + "Potassium: " + getPotassium() + "\n"
                ;

    }

    public String getShortDesc(){
/*
        private String EnglishName;
        private String IngredientKey;
        private String TurkishName;
        private String Calorie;
        private String ServingSize;

        private String Protein;
        private String Fat;
        private String Sugar;
        private String Sodium;

        private String Carbohydrates;
        private String Cholesterol;
        private String SaturatedFat;
        private String Fiber;

        private String Potassium;*/
        return  getCalorie() + "\n" + getServingSize();

    }
}
