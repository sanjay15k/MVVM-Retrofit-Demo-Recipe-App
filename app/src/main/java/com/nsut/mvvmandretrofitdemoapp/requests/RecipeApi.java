package com.nsut.mvvmandretrofitdemoapp.requests;

import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.requests.response.RecipeResponse;
import com.nsut.mvvmandretrofitdemoapp.requests.response.SearchRecipeResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("random/")
    Call<RecipeResponse> getRecipeList(@Query("number") String count, @Query("type") String type);

    @GET("search")
    Call<SearchRecipeResponse> getSearchRecipeList(@Query("number") String count, @Query("query") String query);

    @GET("{recipeID}/information")
    Call<SearchRecipeInstruction> getSearchRecipeInstruction(@Path("recipeID") long recipeID);

}
