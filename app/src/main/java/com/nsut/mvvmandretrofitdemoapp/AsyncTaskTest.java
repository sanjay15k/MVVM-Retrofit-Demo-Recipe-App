package com.nsut.mvvmandretrofitdemoapp;

import android.os.AsyncTask;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class AsyncTaskTest extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        Call recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeInstruction(488686);
        try {
            Response response = recipeListCall.execute();
            if(response.code() == 200){
                SearchRecipeInstruction searchRecipeInstruction = (SearchRecipeInstruction) response.body();
                if(searchRecipeInstruction != null) {
                    System.out.println(searchRecipeInstruction);
                }
            }
            System.out.println("Response : "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Request Completed");
        return null;
    }
}
