package com.nsut.mvvmandretrofitdemoapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;
import com.nsut.mvvmandretrofitdemoapp.models.Recipe;
import com.nsut.mvvmandretrofitdemoapp.viewmodels.RecipeListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecipeList extends BaseActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private RecipeListViewModel recipeListViewModel;
    private LiveData<List<Recipe>> recipeList;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        listView = findViewById(R.id.listView);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
        type = getIntent().getStringExtra("type");

        recipeListViewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())
                .create(RecipeListViewModel.class);

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
        recipeList.observe(this, this::addDataToListView);
    }

    private void addDataToListView(List<Recipe> recipes){
        System.out.println(recipes);
        ArrayList<String> arrayList = new ArrayList<>(recipes.size());
        for(Recipe recipe : recipes){
            arrayList.add(recipe.getTitle());
        }
        arrayAdapter.addAll(arrayList);
        arrayAdapter.notifyDataSetChanged();
    }

    private void reinitializeRecipeList(){
        if(recipeList !=null && recipeList.getValue() !=null ) {
            recipeList.getValue().clear();
        }
    }

}
