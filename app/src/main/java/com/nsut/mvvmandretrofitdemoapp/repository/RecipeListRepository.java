package com.nsut.mvvmandretrofitdemoapp.repository;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RecipeListRepository {

    private static RecipeListRepository instance;
    private MutableLiveData<List<Recipe>> recipeList;

    public static RecipeListRepository getInstance(){
        if(instance == null){
            instance = new RecipeListRepository();
        }
        return instance;
    }

    private RecipeListRepository(){
        recipeList = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipe(String type){
        return recipeList;
    }

}
