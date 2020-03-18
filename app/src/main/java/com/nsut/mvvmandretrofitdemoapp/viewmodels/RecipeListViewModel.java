package com.nsut.mvvmandretrofitdemoapp.viewmodels;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.repository.RecipeListRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class RecipeListViewModel extends ViewModel {

    private RecipeListRepository recipeListRepository;

    public RecipeListViewModel(){
        recipeListRepository = RecipeListRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipeList(){
        return recipeListRepository.getRecipe();
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return recipeListRepository.isNetworkTimeout();
    }

    public void searchRecipe(String type){
        System.out.println("GET RECIPE 2");
        recipeListRepository.searchRecipe(type);
    }

    public void onBackPressed(boolean isBackPressed){
        recipeListRepository.cancelRequest(isBackPressed);
    }

}
