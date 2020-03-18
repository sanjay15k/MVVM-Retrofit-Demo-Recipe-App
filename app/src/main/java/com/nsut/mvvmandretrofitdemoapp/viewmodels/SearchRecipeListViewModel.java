package com.nsut.mvvmandretrofitdemoapp.viewmodels;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.repository.SearchRecipeListRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchRecipeListViewModel extends ViewModel {

    private static SearchRecipeListRepository mSearchRecipeListRepository;
    private boolean mIsPerformingQuery;

    public SearchRecipeListViewModel(){
        mIsPerformingQuery = false;
        mSearchRecipeListRepository = SearchRecipeListRepository.getInstance();
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return mSearchRecipeListRepository.getSearchRecipeList();
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return mSearchRecipeListRepository.isNetworkTimeout();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return mSearchRecipeListRepository.isQueryExhausted();
    }

    public void searchRecipeList(String query){
        mIsPerformingQuery = true;
        mSearchRecipeListRepository.searchRecipeList(query);
    }

    public boolean ismIsPerformingQuery() {
        if(mSearchRecipeListRepository.getmIsPerformingQuery().getValue() != null) {
            mIsPerformingQuery = mSearchRecipeListRepository.getmIsPerformingQuery().getValue();
        }
        return mIsPerformingQuery;
    }

    public void onBackPressed(boolean isBackPressed){
        mSearchRecipeListRepository.cancelRequest(isBackPressed);
    }

}
