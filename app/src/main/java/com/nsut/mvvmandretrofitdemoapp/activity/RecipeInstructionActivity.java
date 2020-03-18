package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.utils.GlideUtils;

public class RecipeInstructionActivity extends BaseActivity {

    // Views
    private ImageView recipePhotoImageView;
    private TextView recipeTitleTextView;
    private TextView recipeInstructionTextView;
    private TextView healthScoreTextView;
    private TextView cookingTimeTextView;
    private TextView servingCountTextView;
    private Button openRecipeUrlButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_instruction);

        recipePhotoImageView = findViewById(R.id.recipePhotoImageView);
        recipeTitleTextView = findViewById(R.id.recipeTitleTextView);
        recipeInstructionTextView = findViewById(R.id.recipeInstructionTextView);
        healthScoreTextView = findViewById(R.id.healthScoreTextView);
        cookingTimeTextView = findViewById(R.id.cookingTimeTextView);
        servingCountTextView = findViewById(R.id.servingCountTextView);
        openRecipeUrlButton = findViewById(R.id.openRecipeUrlButton);

        Recipe recipe = getIntent().getParcelableExtra("recipe");

        setRecipeData(recipe);

    }

    private void setRecipeData(Recipe recipe){
        GlideUtils.loadImageFromUrl(this, recipePhotoImageView, recipe.getImageUrl(), R.drawable.default_recipe_image);
        recipeTitleTextView.setText(recipe.getTitle());
        recipeInstructionTextView.setText(Html.fromHtml(recipe.getInstruction()));
        healthScoreTextView.setText(String.valueOf(recipe.getHealthScore()));
        cookingTimeTextView.setText(String.valueOf(recipe.getCookingTime()));
        servingCountTextView.setText(String.valueOf(recipe.getServingCount()));
        openRecipeUrlButton.setOnClickListener(v -> openUrlInBrowser(recipe.getSourceUrl()));
    }

    private void openUrlInBrowser(String url){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
