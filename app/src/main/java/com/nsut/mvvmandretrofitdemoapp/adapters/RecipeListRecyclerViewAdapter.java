package com.nsut.mvvmandretrofitdemoapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.activity.RecipeInstructionActivity;
import com.nsut.mvvmandretrofitdemoapp.activity.SearchRecipeInstructionActivity;
import com.nsut.mvvmandretrofitdemoapp.adapters.viewholders.RecipeListViewHolder;
import com.nsut.mvvmandretrofitdemoapp.adapters.viewholders.SearchRecipeResultListViewHolder;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.utils.GlideUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecipeListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int viewTagID;
    private List<Recipe> recipeList;
    private List<SearchRecipe> searchRecipeResultList;
    public RecipeListRecyclerViewAdapter(){};

    public RecipeListRecyclerViewAdapter(int viewTagID){
        this.viewTagID = viewTagID;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if(viewTagID == 1){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_search_recipe_result_list_item, parent, false);
            return new SearchRecipeResultListViewHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_recipe_list_item, parent, false);
            return new RecipeListViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(viewTagID == 1){
            initSearchRecipeResultBindViewHolder((SearchRecipeResultListViewHolder) holder, position);
        }
        else{
            initRecipeResultBindViewHolder((RecipeListViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if(viewTagID==1){
            if(searchRecipeResultList==null)
                return 0;
            return searchRecipeResultList.size();
        }
        else{
            if(recipeList == null)
                return 0;
            return recipeList.size();
        }
    }

    private void initSearchRecipeResultBindViewHolder(SearchRecipeResultListViewHolder holder, int position){
        SearchRecipe searchRecipe = searchRecipeResultList.get(position);
        GlideUtils.loadImageFromUrl(context, holder.searchRecipePhotoImageView, searchRecipe.getImageUrl(), R.drawable.default_recipe_image);
        holder.searchRecipeTitleTextView.setText(searchRecipe.getTitle());
        holder.showInstructionsImageButton.setOnClickListener(v -> {
            long recipeID = searchRecipe.getId();
            Intent intent = new Intent(context, SearchRecipeInstructionActivity.class);
            intent.putExtra("recipeID", recipeID);
            context.startActivity(intent);
        });
    }

    private void initRecipeResultBindViewHolder(RecipeListViewHolder holder, int position){
        Recipe recipe = recipeList.get(position);
        GlideUtils.loadImageFromUrl(context, holder.recipePhotoImageView, recipe.getImageUrl(), R.drawable.default_recipe_image);
        holder.recipeTitleTextView.setText(recipe.getTitle());
        holder.recipeSummaryTextView.setText(Html.fromHtml(recipe.getShortSummary()));
        holder.recipeCardView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeInstructionActivity.class);
            intent.putExtra("recipe", recipe);
            context.startActivity(intent);
        });
    }

    public void setRecipeList(List<Recipe> recipeList){
        this.recipeList = recipeList;
        notifyDataSetChanged();
    }

    public void setSearchRecipeResultList(List<SearchRecipe> searchRecipeResultList){
        this.searchRecipeResultList = searchRecipeResultList;
        notifyDataSetChanged();
    }

    public List<Recipe> getRecipeList(){
        return recipeList;
    }

}
