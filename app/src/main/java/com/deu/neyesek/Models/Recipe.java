package com.deu.neyesek.Models;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String CategoryBread;
    private String recipeKey;
    private String Name;
    private String MainCategory;
    private String Cuisine;
    private String Image;
    private String ShortDescription;
    private ArrayList IngridientNames;
    private ArrayList Ingridients;
    private ArrayList Keywords;
    private ArrayList PrepDetails;
    private ArrayList RecipeDetails;

    public Recipe() {

    }

    public Recipe(String categoryBread, String name, String mainCategory, String cuisine, String image, String shortDescription, ArrayList ingridientNames, ArrayList ingridients, ArrayList keywords, ArrayList prepDetails, ArrayList recipeDetails) {
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

    public ArrayList getIngridientNames() {
        return IngridientNames;
    }

    public void setIngridientNames(ArrayList ingridientNames) {
        IngridientNames = ingridientNames;
    }

    public ArrayList getIngridients() {
        return Ingridients;
    }

    public void setIngridients(ArrayList ingridients) {
        Ingridients = ingridients;
    }

    public ArrayList getKeywords() {
        return Keywords;
    }

    public void setKeywords(ArrayList keywords) {
        Keywords = keywords;
    }

    public ArrayList getPrepDetails() {
        return PrepDetails;
    }

    public void setPrepDetails(ArrayList prepDetails) {
        PrepDetails = prepDetails;
    }

    public ArrayList getRecipeDetails() {
        return RecipeDetails;
    }

    public void setRecipeDetails(ArrayList recipeDetails) {
        RecipeDetails = recipeDetails;
    }
}