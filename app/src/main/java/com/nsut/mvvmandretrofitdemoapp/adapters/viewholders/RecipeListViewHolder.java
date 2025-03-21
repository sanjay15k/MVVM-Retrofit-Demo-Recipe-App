package com.nsut.mvvmandretrofitdemoapp.adapters.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsut.mvvmandretrofitdemoapp.R;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListViewHolder extends RecyclerView.ViewHolder {

    public CardView recipeCardView;
    public ImageView recipePhotoImageView;
    public TextView recipeTitleTextView;
    public TextView recipeSummaryTextView;

    public RecipeListViewHolder(@NonNull View itemView) {
        super(itemView);
        recipeCardView = itemView.findViewById(R.id.recipeCardView);
        recipePhotoImageView = itemView.findViewById(R.id.recipePhotoImageView);
        recipeTitleTextView = itemView.findViewById(R.id.recipeTitleTextView);
        recipeSummaryTextView = itemView.findViewById(R.id.recipeSummaryTextView);
    }

}
