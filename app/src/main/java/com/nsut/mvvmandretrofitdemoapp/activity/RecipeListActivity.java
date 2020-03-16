package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.adapters.RecipeListRecyclerViewAdapter;
import com.nsut.mvvmandretrofitdemoapp.listener.CreateApiCall;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeListActivity extends BaseActivity implements CreateApiCall {

    // Variables
    private RecipeListRecyclerViewAdapter mAdapter;
    private RecipeListViewModel recipeListViewModel;
    private LiveData<List<Recipe>> recipeList;
    private LiveData<Boolean> isNetworkTimeout;
    private String type;

    // Views
    private RecyclerView recipeListRecyclerView;
    private CoordinatorLayout baseLayoutRecipeList;

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
        reinitializeRecipeList();
        sendApiRequest();
    }

    private void getRecipe(){
        System.out.println("GET RECIPE 1");
        recipeListViewModel.searchRecipe(type);
    }

    private void subscribeObserver(){
        recipeList = recipeListViewModel.getRecipeList();
        isNetworkTimeout = recipeListViewModel.isNetworkTimeout();

        recipeList.observe(this, this::addDataToRecyclerView);
        isNetworkTimeout.observe(this, this::networkTimeoutOccurred);
    }

    private void addDataToRecyclerView(List<Recipe> recipes){
        mAdapter.setRecipeList(recipes);
        showProgressBar(false);
    }

    private void networkTimeoutOccurred(Boolean isNetworkTimeout){
        if(isNetworkTimeout){
            Snackbar.make(baseLayoutRecipeList, "Network Timeout Occurred", BaseTransientBottomBar.LENGTH_LONG).show();
            showRetryButton(true, this);
            showProgressBar(false);
        }
    }

    @Override
    public void sendApiRequest() {
        new Handler().postDelayed(this::getRecipe, 5000);
        showRetryButton(false, null);
        showProgressBar(true);
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
