package com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient;

import com.nsut.mvvmandretrofitdemoapp.Executors.AppExecutors;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;
import com.nsut.mvvmandretrofitdemoapp.utils.Constants;

import java.io.IOException;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class SearchRecipeInstructionClient {

    private static SearchRecipeInstructionClient mInstance;
    private MutableLiveData<SearchRecipeInstruction> searchRecipeInstruction;
    private MutableLiveData<Boolean> isNetworkTimeout;
    private Future requestHandler;
    private ScheduledFuture networkTimeoutHandler;

    public static SearchRecipeInstructionClient getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeInstructionClient();
        }
        else{
            mInstance.searchRecipeInstruction.setValue(null);
            mInstance.isNetworkTimeout.setValue(false);
        }
        return mInstance;
    }

    private SearchRecipeInstructionClient(){
        searchRecipeInstruction = new MutableLiveData<>();
        isNetworkTimeout = new MutableLiveData<>();
    }

    public void cancelRequest(boolean isCancel){
        if(isCancel && requestHandler != null && !requestHandler.isDone()){
            if(requestHandler != null) {
                requestHandler.cancel(true);
            }
            if(networkTimeoutHandler != null) {
                networkTimeoutHandler.cancel(true);
            }
        }
    }

    public LiveData<SearchRecipeInstruction> getSearchRecipeInstruction() {
        return searchRecipeInstruction;
    }

    public LiveData<Boolean> isNetworkTimeout() {
        return isNetworkTimeout;
    }

    public void getSearchRecipeInstruction(long recipeID){
        ScheduledThreadPoolExecutor poolExecutor = AppExecutors.getInstance().getExecutor();
        requestHandler = poolExecutor.submit(new SearchRecipeInstructionRunnable(recipeID));
        scheduleTimeout(poolExecutor);
    }

    class SearchRecipeInstructionRunnable implements Runnable{

        private long recipeID;

        SearchRecipeInstructionRunnable(long recipeID){
            this.recipeID = recipeID;
        }

        @Override
        public void run() {
            Call recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeInstruction(recipeID);
            try {
                Response response = recipeListCall.execute();
                System.out.println("Response : "+response);
                if(response.code() == 200){
                    System.out.println("Inside response code");
                    System.out.println("Response body : "+response.body().toString());
                    SearchRecipeInstruction recipeInstruction = (SearchRecipeInstruction) response.body();
                    if(searchRecipeInstruction != null) {
                        searchRecipeInstruction.postValue(recipeInstruction);
                    }
                    else{
                        searchRecipeInstruction.setValue(null);
                    }
                }
                System.out.println("Data fetched : "+searchRecipeInstruction);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Request Completed");
        }

    }

    private void scheduleTimeout(ScheduledThreadPoolExecutor executor){
        networkTimeoutHandler = executor.schedule(() -> {
            if(!requestHandler.isDone()) {
                cancelRequest(true);
                isNetworkTimeout.postValue(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

}
