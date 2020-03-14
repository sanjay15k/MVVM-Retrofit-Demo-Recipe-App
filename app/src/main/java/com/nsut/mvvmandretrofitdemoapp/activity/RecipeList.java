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

public class RecipeList extends BaseActivity {

    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        listView = findViewById(R.id.listView);

        RecipeListViewModel recipeListViewModel = ViewModelProvider.
                AndroidViewModelFactory.getInstance(getApplication()).
                create(RecipeListViewModel.class);

        String type = getIntent().getStringExtra("type");
        LiveData<List<Recipe>> recipeList = recipeListViewModel.getRecipeList(type);

        recipeList.observe(this, recipes -> {
            ArrayList<String> arrayList = new ArrayList<>(recipes.size());
            for(Recipe recipe : recipes){
                arrayList.add(recipe.getTitle());
            }
            arrayAdapter.addAll(arrayList);
            arrayAdapter.notifyDataSetChanged();
        });

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(arrayAdapter);
    }
}
