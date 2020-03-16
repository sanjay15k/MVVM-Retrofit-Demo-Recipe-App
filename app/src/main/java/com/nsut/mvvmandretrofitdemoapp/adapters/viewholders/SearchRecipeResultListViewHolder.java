package com.nsut.mvvmandretrofitdemoapp.adapters.viewholders;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsut.mvvmandretrofitdemoapp.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SearchRecipeResultListViewHolder extends RecyclerView.ViewHolder {

    public ImageView searchRecipePhotoImageView;
    public TextView searchRecipeTitleTextView;
    public ImageButton showInstructionsImageButton;

    public SearchRecipeResultListViewHolder(@NonNull View itemView) {
        super(itemView);
        searchRecipePhotoImageView = itemView.findViewById(R.id.searchRecipePhotoImageView);
        searchRecipeTitleTextView = itemView.findViewById(R.id.searchRecipeTitleTextView);
        showInstructionsImageButton = itemView.findViewById(R.id.showInstructionsImageButton);
    }

}
