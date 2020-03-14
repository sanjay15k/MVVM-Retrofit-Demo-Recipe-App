package com.nsut.mvvmandretrofitdemoapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private long id;

    @SerializedName("title")
    private String title;

    @SerializedName("image")
    private String imageUrl;

    @SerializedName("summary")
    private String shortSummary;

    @SerializedName("instructions")
    private String instruction;


    public Recipe(long id, String title, String imageUrl, String shortSummary, String instruction) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.shortSummary = shortSummary;
        this.instruction = instruction;
    }

    private Recipe(Parcel in) {
        id = in.readLong();
        title = in.readString();
        imageUrl = in.readString();
        shortSummary = in.readString();
        instruction = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(imageUrl);
        dest.writeString(shortSummary);
        dest.writeString(instruction);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

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

    public String getShortSummary() {
        return shortSummary;
    }

    public void setShortSummary(String shortSummary) {
        this.shortSummary = shortSummary;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortSummary='" + shortSummary + '\'' +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
