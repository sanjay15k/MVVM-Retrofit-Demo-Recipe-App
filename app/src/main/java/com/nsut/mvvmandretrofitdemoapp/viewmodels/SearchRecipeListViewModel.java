package com.nsut.mvvmandretrofitdemoapp.viewmodels;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.repository.SearchRecipeListRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchRecipeListViewModel extends ViewModel {

    private static SearchRecipeListRepository mSearchRecipeListRepository;

    public SearchRecipeListViewModel(){
        mSearchRecipeListRepository = SearchRecipeListRepository.getInstance();
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return mSearchRecipeListRepository.getSearchRecipeList();
    }

    public void searchRecipeList(String query){
        mSearchRecipeListRepository.searchRecipeList(query);
    }

}
