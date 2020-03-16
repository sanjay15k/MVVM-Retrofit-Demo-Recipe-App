package com.nsut.mvvmandretrofitdemoapp.repository;


import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient.SearchRecipeApiClient;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SearchRecipeListRepository {

    private static SearchRecipeListRepository mInstance;
    private SearchRecipeApiClient searchRecipeApiClient;

    public static SearchRecipeListRepository getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeListRepository();
        }
        return mInstance;
    }

    private SearchRecipeListRepository(){
        searchRecipeApiClient = SearchRecipeApiClient.getInstance();
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return searchRecipeApiClient.getSearchRecipeList();
    }

    public void searchRecipeList(String query){
        searchRecipeApiClient.searchRecipeList(query);
    }

}
