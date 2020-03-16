package com.nsut.mvvmandretrofitdemoapp;

import android.content.Context;
import android.os.AsyncTask;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.requests.ServiceGenerator;
import com.nsut.mvvmandretrofitdemoapp.requests.response.RecipeResponse;
import com.nsut.mvvmandretrofitdemoapp.requests.response.SearchRecipeResponse;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import retrofit2.Call;
import retrofit2.Response;

public class AsyncTaskTest extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        Call recipeListCall = ServiceGenerator.getRecipeApi().getSearchRecipeList("2", "dosa");
        try {
            Response response = recipeListCall.execute();
            System.out.println("DONE 3");
            if(response.code() == 200){
                SearchRecipeResponse searchRecipeResponse = (SearchRecipeResponse) response.body();
                if(searchRecipeResponse != null) {
                    List<SearchRecipe> recipeListResponse = searchRecipeResponse.getSearchRecipeList();
                    System.out.println(recipeListResponse);
                }
            }
            System.out.println("Response : "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
