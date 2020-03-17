package com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient;

import com.nsut.mvvmandretrofitdemoapp.Executors.AppExecutors;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;
import com.nsut.mvvmandretrofitdemoapp.requests.response.SearchRecipeResponse;
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

public class SearchRecipeApiClient {

    private static SearchRecipeApiClient mInstance;
    private MutableLiveData<List<SearchRecipe>> mSearchRecipeList;
    private MutableLiveData<Boolean> isNetworkTimeout;

    public static SearchRecipeApiClient getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeApiClient();
        }
        return mInstance;
    }

    private SearchRecipeApiClient(){
        mSearchRecipeList = new MutableLiveData<>();
        isNetworkTimeout = new MutableLiveData<>();
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return mSearchRecipeList;
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return isNetworkTimeout;
    }

    public void searchRecipeList(String query){
        ScheduledThreadPoolExecutor executor = AppExecutors.getInstance().getExecutor();
        Future handler = executor.submit(new SearchRecipeListRunnable(query));
        scheduleTimeout(executor, handler);
    }

    class SearchRecipeListRunnable implements Runnable{

        private String query;

        SearchRecipeListRunnable(String query){
            this.query = query;
        }

        @Override
        public void run() {
            Call recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeList("5", query);
            try {
                Response response = recipeListCall.execute();
                System.out.println("DONE 3");
                if(response.code() == 200){
                    SearchRecipeResponse searchRecipeResponse = (SearchRecipeResponse) response.body();
                    if(searchRecipeResponse != null) {
                        List<SearchRecipe> recipeListResponse = searchRecipeResponse.getSearchRecipeList();
                        System.out.println(recipeListResponse);
                        mSearchRecipeList.postValue(recipeListResponse);
                    }
                }
                System.out.println("Response : "+response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void scheduleTimeout(ScheduledThreadPoolExecutor executor, Future handler){
        executor.schedule(() -> {
            if(!handler.isDone()){
                handler.cancel(true);
                isNetworkTimeout.postValue(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);

    }

}
