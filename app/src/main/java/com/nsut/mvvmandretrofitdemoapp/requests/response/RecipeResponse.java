package com.nsut.mvvmandretrofitdemoapp.requests.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;

import java.util.List;

public class RecipeResponse {

    @SerializedName("recipes")
    @Expose
    private List<Recipe> recipeList;

    public List<Recipe> getRecipeList() {
        return recipeList;
    }
}
