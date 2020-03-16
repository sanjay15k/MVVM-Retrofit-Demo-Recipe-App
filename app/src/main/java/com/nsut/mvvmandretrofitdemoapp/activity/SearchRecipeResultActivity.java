package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.adapters.RecipeListRecyclerViewAdapter;
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.SearchRecipeListViewModel;

import java.util.List;

public class SearchRecipeResultActivity extends BaseActivity {

    // Variables
    private LiveData<List<SearchRecipe>> mSearchRecipeList;
    private RecipeListRecyclerViewAdapter recipeListRecyclerViewAdapter;

    // Views
    private RecyclerView searchRecipeListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe_result);

        searchRecipeListRecyclerView = findViewById(R.id.searchRecipeListRecyclerView);

        String query = getIntent().getStringExtra("query");
        System.out.println("Query : "+query);

        SearchRecipeListViewModel searchRecipeListViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(SearchRecipeListViewModel.class);

        mSearchRecipeList = searchRecipeListViewModel.getSearchRecipeList();

        subscribeObserver();
        initRecyclerView();

        searchRecipeListViewModel.searchRecipeList(query);

//        new AsyncTaskTest().execute();

    }

    private void initRecyclerView(){
        recipeListRecyclerViewAdapter = new RecipeListRecyclerViewAdapter(1);
        searchRecipeListRecyclerView.setAdapter(recipeListRecyclerViewAdapter);
        searchRecipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeObserver() {
        mSearchRecipeList.observe(this, searchRecipes -> {
            System.out.println("Result from Activity- Search Recipe : "+searchRecipes);
            // call recyclerview adapter to set and update the values
            recipeListRecyclerViewAdapter.setSearchRecipeResultList(searchRecipes);
        });
    }
}
