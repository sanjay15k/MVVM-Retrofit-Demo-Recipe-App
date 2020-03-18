package com.nsut.mvvmandretrofitdemoapp.requests.NetworkClient;

import com.nsut.mvvmandretrofitdemoapp.Executors.AppExecutors;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;
import com.nsut.mvvmandretrofitdemoapp.utils.Constants;

import java.io.IOException;
import java.util.concurrent.Future;
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
    private MutableLiveData<Boolean> mIsPerformingQuery;
    private SearchRecipeInstructionRunnable searchRecipeInstructionRunnable;

    public static SearchRecipeInstructionClient getInstance(){
        if(mInstance == null){
            mInstance = new SearchRecipeInstructionClient();
        }
        return mInstance;
    }

    private SearchRecipeInstructionClient(){
        searchRecipeInstruction = new MutableLiveData<>();
        isNetworkTimeout = new MutableLiveData<>();
        mIsPerformingQuery = new MutableLiveData<>();
    }

    public void initSearchRecipeInstructionApiClient(){
        mInstance.searchRecipeInstruction.setValue(null);
        mInstance.isNetworkTimeout.setValue(false);
        mInstance.mIsPerformingQuery.setValue(false);
    }

    public void cancelRequest(boolean isCancel){
        getCancelThread(isCancel).start();
    }

    public LiveData<SearchRecipeInstruction> getSearchRecipeInstruction() {
        return searchRecipeInstruction;
    }

    public LiveData<Boolean> isNetworkTimeout() {
        return isNetworkTimeout;
    }


    public LiveData<Boolean> getmIsPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void getSearchRecipeInstruction(long recipeID){
        ScheduledThreadPoolExecutor poolExecutor = AppExecutors.getInstance().getExecutor();
        searchRecipeInstructionRunnable = new SearchRecipeInstructionRunnable(recipeID);
        Future requestHandler = poolExecutor.submit(searchRecipeInstructionRunnable);
        scheduleTimeout(poolExecutor, requestHandler);
    }

    class SearchRecipeInstructionRunnable implements Runnable{

        private long recipeID;
        private Call recipeListCall;
        private boolean isCancel;

        SearchRecipeInstructionRunnable(long recipeID){
            this.recipeID = recipeID;
        }

        @Override
        public void run() {
            recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeInstruction(recipeID);
            try {
                Response response = recipeListCall.execute();
                if(isCancel){
                    System.out.println("Cancelling request");
                    recipeListCall.cancel();
                    mIsPerformingQuery.postValue(false);
                    return;
                }
                if(response.code() == 200){
                    SearchRecipeInstruction recipeInstruction = (SearchRecipeInstruction) response.body();
                    if(searchRecipeInstruction != null) {
                        mIsPerformingQuery.postValue(false);
                        if(isCancel){
                            searchRecipeInstruction.setValue(null);
                        }
                        else{
                            searchRecipeInstruction.postValue(recipeInstruction);
                        }
                    }
                    else{
                        searchRecipeInstruction.setValue(null);
                    }
                }
                System.out.println("Response : "+response);
                System.out.println("Data fetched : "+searchRecipeInstruction);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Request Completed");
        }

        void cancelRequest(boolean isCancel){
            this.isCancel = isCancel;
        }

    }

    private void scheduleTimeout(ScheduledThreadPoolExecutor executor, Future requestHandler){
        executor.schedule(() -> {
            if(!requestHandler.isDone()) {
                cancelRequest(true);
                isNetworkTimeout.postValue(true);
            }
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private Thread getCancelThread(boolean isCancel){
        return new Thread(() -> {
            while (searchRecipeInstructionRunnable == null);
            searchRecipeInstructionRunnable.cancelRequest(isCancel);
        });
    }

}
