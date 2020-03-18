package com.nsut.mvvmandretrofitdemoapp.models;

import com.google.gson.annotations.SerializedName;

public class SearchRecipeInstruction {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("instructions")
    private String instruction;

    @SerializedName("healthScore")
    private long healthScore;

    @SerializedName("readyInMinutes")
    private long cookingTime;

    @SerializedName("servings")
    private long servingCount;

    @SerializedName("sourceUrl")
    private String sourceUrl;

    public SearchRecipeInstruction(long id, String title, String imageUrl, String instruction, long healthScore, long cookingTime, long servingCount, String sourceUrl) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.instruction = instruction;
        this.healthScore = healthScore;
        this.cookingTime = cookingTime;
        this.servingCount = servingCount;
        this.sourceUrl = sourceUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public long getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(long healthScore) {
        this.healthScore = healthScore;
    }

    public long getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(long cookingTime) {
        this.cookingTime = cookingTime;
    }

    public long getServingCount() {
        return servingCount;
    }

    public void setServingCount(long servingCount) {
        this.servingCount = servingCount;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Override
    public String toString() {
        return "SearchRecipeInstruction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", instruction='" + instruction + '\'' +
                ", healthScore=" + healthScore +
                ", cookingTime=" + cookingTime +
                ", servingCount=" + servingCount +
                ", sourceUrl='" + sourceUrl + '\'' +
                '}';
    }
}
