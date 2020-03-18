package com.nsut.mvvmandretrofitdemoapp.requests.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;

import java.util.List;

public class SearchRecipeResponse {

    @SerializedName("results")
    @Expose
    private List<SearchRecipe> searchRecipeList;

    @SerializedName("baseUri")
    @Expose
    private String imageBaseUrl;

    public SearchRecipeResponse(List<SearchRecipe> searchRecipeList, String imageBaseUrl) {
        this.searchRecipeList = searchRecipeList;
        this.imageBaseUrl = imageBaseUrl;
    }

    public List<SearchRecipe> getSearchRecipeList(){
        for(SearchRecipe searchRecipe : searchRecipeList){
            String imageUrl = imageBaseUrl.concat(searchRecipe.getImageUrl());
            searchRecipe.setImageUrl(imageUrl);
        }
        return searchRecipeList;
    }

    @Override
    public String toString() {
        return "SearchRecipeResponse{" +
                "searchRecipeList=" + searchRecipeList +
                ", imageBaseUrl='" + imageBaseUrl + '\'' +
                '}';
    }
}
