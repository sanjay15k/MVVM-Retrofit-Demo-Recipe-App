package com.nsut.mvvmandretrofitdemoapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nsut.mvvmandretrofitdemoapp.AsyncTaskTest;
import com.nsut.mvvmandretrofitdemoapp.BaseActivity;
import com.nsut.mvvmandretrofitdemoapp.R;

import androidx.cardview.widget.CardView;

public class HomeScreen extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_home_screen);

        loadImages();

    }

    public void showRecipeList(View view) {
        Intent intent = new Intent(this, RecipeList.class);
        intent.putExtra("type", String.valueOf(view.getTag()));
        startActivity(intent);
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
