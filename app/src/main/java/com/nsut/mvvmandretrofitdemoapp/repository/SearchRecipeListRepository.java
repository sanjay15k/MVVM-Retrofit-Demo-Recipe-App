package com.nsut.mvvmandretrofitdemoapp.repository;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient.SearchRecipeApiClient;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

public class SearchRecipeListRepository {

    private static SearchRecipeListRepository mInstance;
    private SearchRecipeApiClient searchRecipeApiClient;
    private MediatorLiveData<List<SearchRecipe>> mediatorLiveData;
    private MutableLiveData<Boolean> isQueryExhausted;

    public static SearchRecipeListRepository getInstance(){
        System.out.println("mInstanceView Model : "+mInstance);
        if(mInstance == null){
            mInstance = new SearchRecipeListRepository();
        }
        else {
            mInstance.isQueryExhausted.setValue(false);
            mInstance.searchRecipeApiClient.initSearchRecipeApiClient();
            System.out.println("mSearchRecipeList 1 : "+mInstance.searchRecipeApiClient.getmSearchRecipeList());
        }
        return mInstance;
    }

    private SearchRecipeListRepository(){
        searchRecipeApiClient = SearchRecipeApiClient.getInstance();
        isQueryExhausted = new MutableLiveData<>(false);
        initMediatorLiveData();
    }

    private void initMediatorLiveData(){
        mediatorLiveData = new MediatorLiveData<>();
        LiveData<List<SearchRecipe>> searchRecipeLiveData = searchRecipeApiClient.getSearchRecipeList();
        mediatorLiveData.addSource(searchRecipeLiveData, searchRecipes -> {
            if(searchRecipes != null){
//                System.out.println("Mediator data : "+searchRecipes);
                if(mediatorLiveData.getValue() != null) {
                    List<SearchRecipe> mSearchRecipeList = new ArrayList<>(mediatorLiveData.getValue());
                    mSearchRecipeList.addAll(searchRecipes);
                    mediatorLiveData.setValue(mSearchRecipeList);
                }
                else{
                    mediatorLiveData.setValue(searchRecipes);
                }
                doneQuery(searchRecipes);
            }
            else{
                // query in cache db
            }
        });
    }

    private void doneQuery(List<SearchRecipe> searchRecipeList){
        if(searchRecipeList == null || searchRecipeList.size() == 0){
            isQueryExhausted.setValue(true);
        }
        else {
            isQueryExhausted.setValue(false);
        }
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return mediatorLiveData;
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return searchRecipeApiClient.isNetworkTimeout();
    }

    public LiveData<Boolean> isQueryExhausted(){
        return isQueryExhausted;
    }

    public void searchRecipeList(String query){
        isQueryExhausted.setValue(false);
        searchRecipeApiClient.searchRecipeList(query);
    }

    public void cancelRequest(boolean isCancel){
        System.out.println("backpressed 3");
        searchRecipeApiClient.cancelRequest(isCancel);
    }

    public List<SearchRecipe> getmSearchRecipeList() {
        return searchRecipeApiClient.getmSearchRecipeList();
    }

}
