package com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient;

import com.nsut.mvvmandretrofitdemoapp.Executors.AppExecutors;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;
import com.nsut.mvvmandretrofitdemoapp.requests.response.RecipeResponse;
import com.nsut.mvvmandretrofitdemoapp.utils.Constants;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class RecipeListClient {

    private static RecipeListClient instance;
    private MutableLiveData<List<Recipe>> recipeList;
    private MutableLiveData<Boolean> isNetworkTimeout;
    private SearchRecipeRunnable searchRecipeRunnable;

    public static RecipeListClient getInstance(){
        if(instance == null){
            instance = new RecipeListClient();
        }
        return instance;
    }

    private RecipeListClient(){
        recipeList = new MutableLiveData<>();
        isNetworkTimeout = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipe(){
        return recipeList;
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return isNetworkTimeout;
    }

    public void searchRecipe(String type){
        System.out.println("GET RECIPE 4");
        if(searchRecipeRunnable != null){
            searchRecipeRunnable = null;
        }
        searchRecipeRunnable = new SearchRecipeRunnable(Constants.RECIPE_COUNT, type);
        ScheduledThreadPoolExecutor poolExecutor = AppExecutors.getInstance().getExecutor();

        Future handler = poolExecutor.submit(searchRecipeRunnable);
        poolExecutor.schedule(() -> {
            if(!handler.isDone()) {
                System.out.println("**** TIME OUT ****");
                handler.cancel(true);
                isNetworkTimeout.postValue(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }


    private class SearchRecipeRunnable implements Runnable{

        private String recipeCount;
        private String type;

        SearchRecipeRunnable(String recipeCount, String type){
            this.recipeCount = recipeCount;
            this.type = type;
        }

        @Override
        public void run() {
            Call recipeListCall = ServiceGenerator.getRecipeApi().getRecipeList(recipeCount, type);
            try {
                Response response = recipeListCall.execute();
                if(response.code() == 200){
                    RecipeResponse recipeResponse = (RecipeResponse) response.body();
                    if(recipeResponse != null) {
                        List<Recipe> recipeListResponse = recipeResponse.getRecipeList();
                        if(recipeListResponse ==null || recipeListResponse.size()==0){
                            recipeList.setValue(null);
                        }
                        else{
                            recipeList.postValue(recipeListResponse);
                        }
                    }
                }
                System.out.println("Response : "+response);
            } catch (IOException e) {

                e.printStackTrace();
            }
            System.out.println("Request Completed");
        }
    }

}
