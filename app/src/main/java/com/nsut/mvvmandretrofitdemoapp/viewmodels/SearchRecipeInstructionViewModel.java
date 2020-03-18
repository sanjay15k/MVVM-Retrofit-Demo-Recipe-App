package com.nsut.mvvmandretrofitdemoapp.viewmodels;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.repository.SearchRecipeInstructionRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class SearchRecipeInstructionViewModel extends ViewModel {

    private SearchRecipeInstructionRepository mRepository;

    public SearchRecipeInstructionViewModel(){
        mRepository = SearchRecipeInstructionRepository.getInstance();
    }

    public LiveData<SearchRecipeInstruction> getSearchRecipeInstruction() {
        return mRepository.getSearchRecipeInstruction();
    }

    public void getSearchRecipeInstruction(long recipeID){
        mRepository.getSearchRecipeInstruction(recipeID);
    }

    public LiveData<Boolean> isNetworkTimeout() {
        return mRepository.isNetworkTimeout();
    }

    public void onBackPressed(boolean isBackPressed){
        mRepository.cancelRequest(isBackPressed);
    }

}
