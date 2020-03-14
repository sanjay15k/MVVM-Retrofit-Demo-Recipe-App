package com.nsut.mvvmandretrofitdemoapp.viewmodels;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.repository.RecipeListRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecipeListViewModel extends ViewModel {

    private RecipeListRepository recipeListRepository;

    public RecipeListViewModel(){
        recipeListRepository = RecipeListRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecipeList(String type){
        return recipeListRepository.getRecipe(type);
    }

}
