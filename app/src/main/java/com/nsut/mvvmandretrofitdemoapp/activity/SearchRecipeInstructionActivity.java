package com.nsut.mvvmandretrofitdemoapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.listener.CreateApiCall;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipeInstruction;
import com.nsut.mvvmandretrofitdemoapp.utils.GlideUtils;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.SearchRecipeInstructionViewModel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

public class SearchRecipeInstructionActivity extends BaseActivity implements CreateApiCall {

    // Variables
    private long recipeID;
    private SearchRecipeInstructionViewModel mViewModel;
    private LiveData<SearchRecipeInstruction> searchRecipeInstruction;

    // Views
    private LinearLayout recipeInstructionLinearLayout;
    private ImageView recipePhotoImageView;
    private TextView recipeTitleTextView;
    private TextView recipeInstructionTextView;
    private TextView healthScoreTextView;
    private TextView cookingTimeTextView;
    private TextView servingCountTextView;
    private Button openRecipeUrlButton;

    @Override
    public void onBackPressed() {
        mViewModel.onBackPressed(true);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instruction);

        recipeInstructionLinearLayout = findViewById(R.id.recipeInstructionLinearLayout);
        recipePhotoImageView = findViewById(R.id.recipePhotoImageView);
        recipeTitleTextView = findViewById(R.id.recipeTitleTextView);
        recipeInstructionTextView = findViewById(R.id.recipeInstructionTextView);
        healthScoreTextView = findViewById(R.id.healthScoreTextView);
        cookingTimeTextView = findViewById(R.id.cookingTimeTextView);
        servingCountTextView = findViewById(R.id.servingCountTextView);
        openRecipeUrlButton = findViewById(R.id.openRecipeUrlButton);

        recipeID = getIntent().getLongExtra("recipeID", 0);

        mViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(SearchRecipeInstructionViewModel.class);

        subscribeObserver();
        sendApiRequest();
    }

    private void subscribeObserver() {
        searchRecipeInstruction = mViewModel.getSearchRecipeInstruction();
        searchRecipeInstruction.observe(this, searchRecipeInstruction -> setRecipeData(searchRecipeInstruction));
        mViewModel.isNetworkTimeout().observe(this, this::networkTimeoutOccurred);
    }

    @Override
    public void sendApiRequest() {
        recipeInstructionLinearLayout.setVisibility(View.GONE);
        showProgressBar(true);
        mViewModel.getSearchRecipeInstruction(recipeID);
    }

    private void networkTimeoutOccurred(Boolean isNetworkTimeout){
        if(isNetworkTimeout){
            Snackbar.make(recipeInstructionLinearLayout, "Network Timeout Occurred", BaseTransientBottomBar.LENGTH_LONG).show();
            showRetryButton(true, this);
            showProgressBar(false);
        }
    }

    private void setRecipeData(SearchRecipeInstruction searchRecipeInstruction){
        if(searchRecipeInstruction != null) {
            if(!mViewModel.ismIsPerformingQuery()) {
                showProgressBar(false);
                recipeInstructionLinearLayout.setVisibility(View.VISIBLE);
                GlideUtils.loadImageFromUrl(this, recipePhotoImageView, searchRecipeInstruction.getImageUrl(), R.drawable.default_recipe_image);
                recipeTitleTextView.setText(searchRecipeInstruction.getTitle());
                recipeInstructionTextView.setText(Html.fromHtml(searchRecipeInstruction.getInstruction()));
                healthScoreTextView.setText(String.valueOf(searchRecipeInstruction.getHealthScore()));
                cookingTimeTextView.setText(String.valueOf(searchRecipeInstruction.getCookingTime()));
                servingCountTextView.setText(String.valueOf(searchRecipeInstruction.getServingCount()));
                openRecipeUrlButton.setOnClickListener(v -> openUrlInBrowser(searchRecipeInstruction.getSourceUrl()));
            }
        }
    }

    private void openUrlInBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }


}
