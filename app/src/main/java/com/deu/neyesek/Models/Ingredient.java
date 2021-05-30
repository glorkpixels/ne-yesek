package com.deu.neyesek.Models;

public class Ingredient {


    private String TurkishName;
    private String ingredientKey;
    private String MainImage;
    private String GraphImageOne;
    private String GraphImageTwo;
    private String ServingSize;
    private String Calorie;
    private String TotalCarb;
    private String TotalFat;
    private String TotalProtein;
    private String EnglishName;
    private String Sugar;
    private String[] IngredientInformation;

    public Ingredient(String turkishName, String ingredientKey, String mainImage, String graphImageOne, String graphImageTwo, String servingSize, String calorie, String totalCarb, String totalFat, String totalProtein, String englishName, String sugar, String[] ingredientInformation) {
        TurkishName = turkishName;
        this.ingredientKey = ingredientKey;
        MainImage = mainImage;
        GraphImageOne = graphImageOne;
        GraphImageTwo = graphImageTwo;
        ServingSize = servingSize;
        Calorie = calorie;
        TotalCarb = totalCarb;
        TotalFat = totalFat;
        TotalProtein = totalProtein;
        EnglishName = englishName;
        Sugar = sugar;
        IngredientInformation = ingredientInformation;
    }

    public Ingredient() {
    }

    public String getTurkishName() {
        return TurkishName;
    }

    public void setTurkishName(String turkishName) {
        TurkishName = turkishName;
    }

    public String getIngredientKey() {
        return ingredientKey;
    }

    public void setIngredientKey(String ingredientKey) {
        this.ingredientKey = ingredientKey;
    }

    public String getMainImage() {
        return MainImage;
    }

    public void setMainImage(String mainImage) {
        MainImage = mainImage;
    }

    public String getGraphImageOne() {
        return GraphImageOne;
    }

    public void setGraphImageOne(String graphImageOne) {
        GraphImageOne = graphImageOne;
    }

    public String getGraphImageTwo() {
        return GraphImageTwo;
    }

    public void setGraphImageTwo(String graphImageTwo) {
        GraphImageTwo = graphImageTwo;
    }

    public String getServingSize() {
        return ServingSize;
    }

    public void setServingSize(String servingSize) {
        ServingSize = servingSize;
    }

    public String getCalorie() {
        return Calorie;
    }

    public void setCalorie(String calorie) {
        Calorie = calorie;
    }

    public String getTotalCarb() {
        return TotalCarb;
    }

    public void setTotalCarb(String totalCarb) {
        TotalCarb = totalCarb;
    }

    public String getTotalFat() {
        return TotalFat;
    }

    public void setTotalFat(String totalFat) {
        TotalFat = totalFat;
    }

    public String getTotalProtein() {
        return TotalProtein;
    }

    public void setTotalProtein(String totalProtein) {
        TotalProtein = totalProtein;
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public String[] getIngredientInformation() {
        return IngredientInformation;
    }

    public void setIngredientInformation(String[] ingredientInformation) {
        IngredientInformation = ingredientInformation;
    }
}
