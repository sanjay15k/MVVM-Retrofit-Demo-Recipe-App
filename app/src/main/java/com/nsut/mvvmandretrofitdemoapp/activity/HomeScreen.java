package com.nsut.mvvmandretrofitdemoapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;

import androidx.appcompat.widget.SearchView;

public class HomeScreen extends BaseActivity {

    private SearchView searchView;

    @Override
    public void onBackPressed() {
        System.out.println("Called 1");
        String query = searchView.getQuery().toString().trim();

        if(searchView.hasFocus() || query.length()>0){
            defaultSearchView();
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home_screen);

        searchView = findViewById(R.id.searchView);

        loadImages();
        initSearchView();
    }

    private void initSearchView(){
        searchView.setIconifiedByDefault(false);

//        This was producing the error - popping keyboard on startup, it expands the SearchView and
//        taking focus automatically that causes the keyboard to open.
//        searchView.onActionViewExpanded();

        searchView.setOnQueryTextListener(new SearchViewListener());
        customizeSearchView(searchView);
    }

    private class SearchViewListener implements SearchView.OnQueryTextListener{
        @Override
        public boolean onQueryTextSubmit(String query) {
            Intent intent = new Intent(HomeScreen.this, SearchRecipeResultActivity.class);
            intent.putExtra("query", query);
            startActivity(intent);
            defaultSearchView();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    }

    public void showRecipeList(View view) {
        Intent intent = new Intent(this, RecipeListActivity.class);
        intent.putExtra("type", String.valueOf(view.getTag()));
        startActivity(intent);
    }

    private void defaultSearchView(){
        searchView.setQuery("", false);
        searchView.clearFocus();
    }

    private void customizeSearchView(SearchView searchView) {

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(Color.GRAY);
        searchAutoComplete.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        searchPlate.setBackgroundColor(Color.TRANSPARENT);

        ImageView searchCloseIcon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        searchCloseIcon.setColorFilter(Color.DKGRAY);

//        ImageView voiceIcon = (ImageView)searchView.findViewById(android.support.v7.appcompat.R.id.search_voice_btn);
//        voiceIcon.setImageResource(R.drawable.abc_ic_voice_search);

        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
        searchIcon.setImageResource(R.drawable.search_icon);
        searchIcon.setPadding(15,15,15,15);
    }

    private void loadImages(){
        Glide.with(this).load(R.drawable.main_course).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView1));
        Glide.with(this).load(R.drawable.breakfast).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView2));
        Glide.with(this).load(R.drawable.snack).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView3));
        Glide.with(this).load(R.drawable.salad).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView4));
        Glide.with(this).load(R.drawable.soup).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView5));
        Glide.with(this).load(R.drawable.dessert).apply(new RequestOptions().centerCrop()).into((ImageView) findViewById(R.id.circleImageView6));
    }

}
