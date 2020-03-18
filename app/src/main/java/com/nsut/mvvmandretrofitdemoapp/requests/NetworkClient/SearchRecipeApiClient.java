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
    private SearchRecipeListRunnable searchRecipeListRunnable;
    private MutableLiveData<Boolean> mIsPerformingQuery;


    public static SearchRecipeApiClient getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeApiClient();
        }
        return mInstance;
    }

    private SearchRecipeApiClient(){
        mSearchRecipeList = new MutableLiveData<>();
        isNetworkTimeout = new MutableLiveData<>();
        mIsPerformingQuery = new MutableLiveData<>();
    }

    public void initSearchRecipeApiClient(){
        mInstance.mSearchRecipeList.setValue(null);
        mInstance.isNetworkTimeout.setValue(false);
        mInstance.mIsPerformingQuery.setValue(false);
    }


    // cancels the request
    public void cancelRequest(boolean isCancel){
        getCancelThread(isCancel).start();
    }

    public LiveData<List<SearchRecipe>> getSearchRecipeList(){
        return mSearchRecipeList;
    }

    public LiveData<Boolean> isNetworkTimeout(){
        return isNetworkTimeout;
    }

    public LiveData<Boolean> getmIsPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void searchRecipeList(String query){
        mIsPerformingQuery.setValue(true);
        ScheduledThreadPoolExecutor executor = AppExecutors.getInstance().getExecutor();
        searchRecipeListRunnable = new SearchRecipeListRunnable(query);
        Future requestHandler = executor.submit(searchRecipeListRunnable);
        scheduleTimeout(executor, requestHandler);
    }

    class SearchRecipeListRunnable implements Runnable{

        private String query;
        private Call recipeListCall;
        private boolean isCancel;

        SearchRecipeListRunnable(String query){
            this.query = query;
        }

        @Override
        public void run() {
            recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeList(Constants.SEARCH_RECIPE_COUNT, query);
            try {
                Response response = recipeListCall.execute();
                if(isCancel){
                    System.out.println("Cancelling request");
                    recipeListCall.cancel();
                    mIsPerformingQuery.postValue(false);
                    return;
                }
                if(response.code() == 200){
                    SearchRecipeResponse searchRecipeResponse = (SearchRecipeResponse) response.body();
                    if(searchRecipeResponse != null) {
                        List<SearchRecipe> recipeListResponse = searchRecipeResponse.getSearchRecipeList();
                        mIsPerformingQuery.postValue(false);
                        if(isCancel){
                            mSearchRecipeList.setValue(null);
                        }
                        else{
                            mSearchRecipeList.postValue(recipeListResponse);
                        }
                    }
                    else{
                        mSearchRecipeList.setValue(null);
                    }
                }
                System.out.println("Response : "+response);
            } catch (IOException e) {
                e.printStackTrace();
                mSearchRecipeList.setValue(null);
            }
        }

        void cancelRequest(boolean isCancel){
            this.isCancel = isCancel;
        }

    }

    private void scheduleTimeout(ScheduledThreadPoolExecutor executor, Future requestHandler){
         executor.schedule(() -> {
             if(requestHandler.isCancelled())
                 return;
             if(!requestHandler.isDone()) {
                cancelRequest(true);
                isNetworkTimeout.postValue(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private Thread getCancelThread(boolean isCancel){
        return new Thread(() -> {
            while (searchRecipeListRunnable == null);
            searchRecipeListRunnable.cancelRequest(isCancel);
        });
    }

}
