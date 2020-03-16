package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.adapters.RecipeListRecyclerViewAdapter;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeListActivity extends BaseActivity {

    private RecyclerView recipeListRecyclerView;
    private RecipeListRecyclerViewAdapter mAdapter;
    private RecipeListViewModel recipeListViewModel;
    private LiveData<List<Recipe>> recipeList;

    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListRecyclerView = findViewById(R.id.recipeListRecyclerView);

        type = getIntent().getStringExtra("type");

        recipeListViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(RecipeListViewModel.class);

        initRecyclerView();

        subscribeObserver();
        reinitializeRecipeList();
        getRecipe();
    }

    private void getRecipe(){
        System.out.println("GET RECIPE 1");
        recipeListViewModel.searchRecipe(type);
    }

    private void subscribeObserver(){
        recipeList = recipeListViewModel.getRecipeList();
        recipeList.observe(this, this::addDataToRecyclerView);
    }

    private void addDataToRecyclerView(List<Recipe> recipes){
        mAdapter.setRecipeList(recipes);
    }

    private void initRecyclerView(){
        mAdapter = new RecipeListRecyclerViewAdapter();
        recipeListRecyclerView.setAdapter(mAdapter);
        recipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void reinitializeRecipeList(){
        if(recipeList !=null && recipeList.getValue() !=null ) {
            recipeList.getValue().clear();
        }
    }

}
