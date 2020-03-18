package com.nsut.mvvmandretrofitdemoapp.repository;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient.RecipeListClient;

import java.util.List;

import androidx.lifecycle.LiveData;

public class RecipeListRepository {

    private static RecipeListRepository instance;
    private RecipeListClient recipeListClient;

    public static RecipeListRepository getInstance(){
        if(instance == null){
            instance = new RecipeListRepository();
        }
        return instance;
    }

    private RecipeListRepository(){
        recipeListClient = RecipeListClient.getInstance();
    }

    public LiveData<List<Recipe>> getRecipe(){
        return recipeListClient.getRecipe();
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return recipeListClient.isNetworkTimeout();
    }

    public void searchRecipe(String type){
        System.out.println("GET RECIPE 3");
        recipeListClient.searchRecipe(type);
    }

    public void cancelRequest(boolean isCancel){
        recipeListClient.cancelRequest(isCancel);
    }

}