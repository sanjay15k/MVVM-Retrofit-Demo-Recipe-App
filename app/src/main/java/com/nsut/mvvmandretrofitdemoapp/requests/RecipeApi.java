package com.nsut.mvvmandretrofitdemoapp.requests;

import com.nsut.mvvmandretrofitdemoapp.models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("/")
    Call<List<Recipe>> getRecipeList(@Query("number") String count);

}
