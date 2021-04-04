package com.deu.neyesek.Models;

import java.util.List;

public class Recipe {
    private String CategoryBread;
    private String recipeKey;
    private String Name;
    private String MainCategory;
    private String Cuisine;
    private String Image;
    private String ShortDescription;
    private String IngridientNames;
    private String Ingridients;
    private String Keywords;
    private String PrepDetails;
    private String RecipeDetails;

    public Recipe() {

    }

    public Recipe(String categoryBread, String name, String mainCategory, String cuisine, String image, String shortDescription, String ingridientNames, String ingridients, String keywords, String prepDetails, String recipeDetails) {
        CategoryBread = categoryBread;
        Name = name;
        MainCategory = mainCategory;
        Cuisine = cuisine;
        Image = image;
        ShortDescription = shortDescription;
        IngridientNames = ingridientNames;
        Ingridients = ingridients;
        Keywords = keywords;
        PrepDetails = prepDetails;
        RecipeDetails = recipeDetails;
    }

    public String getCategoryBread() {
        return CategoryBread;
    }

    public void setCategoryBread(String categoryBread) {
        CategoryBread = categoryBread;
    }

    public String getRecipeKey() {
        return recipeKey;
    }

    public void setRecipeKey(String recipeKey) {
        this.recipeKey = recipeKey;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMainCategory() {
        return MainCategory;
    }

    public void setMainCategory(String mainCategory) {
        MainCategory = mainCategory;
    }

    public String getCuisine() {
        return Cuisine;
    }

    public void setCuisine(String cuisine) {
        Cuisine = cuisine;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getShortDescription() {
        return ShortDescription;
    }

    public void setShortDescription(String shortDescription) {
        ShortDescription = shortDescription;
    }

    public String getIngridientNames() {
        return IngridientNames;
    }

    public void setIngridientNames(String ingridientNames) {
        IngridientNames = ingridientNames;
    }

    public String getIngridients() {
        return Ingridients;
    }

    public void setIngridients(String ingridients) {
        Ingridients = ingridients;
    }

    public String getKeywords() {
        return Keywords;
    }

    public void setKeywords(String keywords) {
        Keywords = keywords;
    }

    public String getPrepDetails() {
        return PrepDetails;
    }

    public void setPrepDetails(String prepDetails) {
        PrepDetails = prepDetails;
    }

    public String getRecipeDetails() {
        return RecipeDetails;
    }

    public void setRecipeDetails(String recipeDetails) {
        RecipeDetails = recipeDetails;
    }
}