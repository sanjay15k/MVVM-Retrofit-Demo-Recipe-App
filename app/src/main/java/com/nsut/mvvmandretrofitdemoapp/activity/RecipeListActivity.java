package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.adapters.RecipeListRecyclerViewAdapter;
import com.nsut.mvvmandretrofitdemoapp.listener.CreateApiCall;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements CreateApiCall {

    // Variables
    private RecipeListRecyclerViewAdapter mAdapter;
    private RecipeListViewModel recipeListViewModel;
    private LiveData<List<Recipe>> recipeList;
    private String type;

    // Views
    private CoordinatorLayout baseLayoutRecipeList;
    private RecyclerView recipeListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        baseLayoutRecipeList = findViewById(R.id.baseLayoutRecipeList);
        recipeListRecyclerView = findViewById(R.id.recipeListRecyclerView);

        type = getIntent().getStringExtra("type");

        recipeListViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(RecipeListViewModel.class);

        initRecyclerView();

        subscribeObserver();
        sendApiRequest();
    }

    private void initRecyclerView(){
        mAdapter = new RecipeListRecyclerViewAdapter();
        recipeListRecyclerView.setAdapter(mAdapter);
        recipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeObserver(){
        recipeList = recipeListViewModel.getRecipeList();
        if(recipeList.getValue() != null) {
            recipeList.getValue().clear();
        }
        System.out.println("Recipe List initially : "+recipeList);
        recipeList.observe(this, this::addDataToRecyclerView);
        recipeListViewModel.isNetworkTimeout().observe(this, this::networkTimeoutOccurred);
    }

    @Override
    public void sendApiRequest() {
        new Handler().postDelayed(() -> recipeListViewModel.searchRecipe(type), 1000);
        System.out.println("List initially : "+ mAdapter.getRecipeList());
        showRetryButton(false, null);
        showProgressBar(true);
    }

    private void networkTimeoutOccurred(Boolean isNetworkTimeout){
        if(isNetworkTimeout){
            Snackbar.make(baseLayoutRecipeList, "Network Timeout Occurred", BaseTransientBottomBar.LENGTH_LONG).show();
            showRetryButton(true, this);
            showProgressBar(false);
        }
    }

    private void addDataToRecyclerView(List<Recipe> recipes){
        if(recipes !=null && recipes.size()>0) {
            mAdapter.setRecipeList(recipes);
            System.out.println("Recipe List Finally : "+recipes.get(0).getTitle());
            showProgressBar(false);
        }
    }

}
