package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
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
import com.nsut.mvvmandretrofitdemoapp.models.SearchRecipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.SearchRecipeListViewModel;

import java.util.List;

public class SearchRecipeResultActivity extends BaseActivity implements CreateApiCall {

    // Variables
    private SearchRecipeListViewModel searchRecipeListViewModel;
    private LiveData<List<SearchRecipe>> mSearchRecipeList;
    private RecipeListRecyclerViewAdapter recipeListRecyclerViewAdapter;
    private String query;

    // Views
    private CoordinatorLayout baseLayoutSearchRecipeList;
    private RecyclerView searchRecipeListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe_result);

        baseLayoutSearchRecipeList = findViewById(R.id.baseLayoutSearchRecipeList);
        searchRecipeListRecyclerView = findViewById(R.id.searchRecipeListRecyclerView);

        query = getIntent().getStringExtra("query");
        System.out.println("Query : "+query);

        searchRecipeListViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(SearchRecipeListViewModel.class);

        subscribeObserver();
        initRecyclerView();
//        reinitializeSearchRecipeResultList();
        sendApiRequest();

//        new AsyncTaskTest().execute();

    }

    private void initRecyclerView(){
        recipeListRecyclerViewAdapter = new RecipeListRecyclerViewAdapter(1);
        searchRecipeListRecyclerView.setAdapter(recipeListRecyclerViewAdapter);
        searchRecipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeObserver() {
        mSearchRecipeList = searchRecipeListViewModel.getSearchRecipeList();
        mSearchRecipeList.observe(this, searchRecipes -> {
            System.out.println("Result from Activity- Search Recipe : "+searchRecipes);
            showProgressBar(false);
            recipeListRecyclerViewAdapter.setSearchRecipeResultList(searchRecipes);
        });

        searchRecipeListViewModel.isNetworkTimeout().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                networkTimeoutOccurred(aBoolean);
            }
        });
    }

    private void networkTimeoutOccurred(Boolean isNetworkTimeout){
        if(isNetworkTimeout){
            Snackbar.make(baseLayoutSearchRecipeList, "Network Timeout Occurred", BaseTransientBottomBar.LENGTH_LONG).show();
            showRetryButton(true, this);
            showProgressBar(false);
        }
    }

    @Override
    public void sendApiRequest() {
        new Handler().postDelayed(()->{searchRecipeListViewModel.searchRecipeList(query);}, 5000);
        showRetryButton(false, null);
        showProgressBar(true);
    }

    private void reinitializeSearchRecipeResultList(){
        if(mSearchRecipeList !=null && mSearchRecipeList.getValue() !=null ) {
            mSearchRecipeList.getValue().clear();
        }
    }

}
