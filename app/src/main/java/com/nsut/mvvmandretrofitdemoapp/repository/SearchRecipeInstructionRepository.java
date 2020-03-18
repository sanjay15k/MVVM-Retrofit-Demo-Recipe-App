package com.nsut.mvvmandretrofitdemoapp.repository;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient.SearchRecipeInstructionClient;

import androidx.lifecycle.LiveData;

public class SearchRecipeInstructionRepository {

    private static SearchRecipeInstructionRepository mInstance;
    private SearchRecipeInstructionClient searchRecipeInstructionClient;

    public static SearchRecipeInstructionRepository getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeInstructionRepository();
        }
        else{
            mInstance.searchRecipeInstructionClient.initSearchRecipeInstructionApiClient();
        }
        return mInstance;
    }

    private SearchRecipeInstructionRepository(){
        searchRecipeInstructionClient = SearchRecipeInstructionClient.getInstance();
    }

    public LiveData<SearchRecipeInstruction> getSearchRecipeInstruction() {
        return searchRecipeInstructionClient.getSearchRecipeInstruction();
    }

    public void getSearchRecipeInstruction(long recipeID){
        searchRecipeInstructionClient.getSearchRecipeInstruction(recipeID);
    }

    public LiveData<Boolean> isNetworkTimeout() {
        return searchRecipeInstructionClient.isNetworkTimeout();
    }

    public void cancelRequest(boolean isCancel){
        searchRecipeInstructionClient.cancelRequest(isCancel);
    }

    public LiveData<Boolean> getmIsPerformingQuery() {
        return searchRecipeInstructionClient.getmIsPerformingQuery();
    }

}
