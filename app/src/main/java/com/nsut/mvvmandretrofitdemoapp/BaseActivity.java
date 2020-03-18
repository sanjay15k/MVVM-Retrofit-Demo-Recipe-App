package com.nsut.mvvmandretrofitdemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.nsut.mvvmandretrofitdemoapp.listener.CreateApiCall;

public abstract class BaseActivity extends AppCompatActivity {

    private Button retryButton;
    private ImageView progressBarImageView;
    private RelativeLayout noResultsFoundLayout;

    @Override
    public void setContentView(int layoutResID) {
        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.frameLayout);
        retryButton = constraintLayout.findViewById(R.id.retryButton);
        progressBarImageView = constraintLayout.findViewById(R.id.progressBarImageView);
        noResultsFoundLayout = constraintLayout.findViewById(R.id.noResultsFoundLayout);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);
        super.setContentView(constraintLayout);

        init();
        loadProgressGifImageView();
    }

    private void init(){
        retryButton.setVisibility(View.GONE);
        progressBarImageView.setVisibility(View.GONE);
        noResultsFoundLayout.setVisibility(View.GONE);
    }

    private void loadProgressGifImageView(){
        Glide.with(this).asGif().load(R.drawable.loader).into(progressBarImageView);
    }

    public void showRetryButton(boolean isVisible, CreateApiCall retrySendingRequest){
        if(retrySendingRequest != null) {
            retryButton.setOnClickListener(v -> retrySendingRequest.sendApiRequest());
        }
        retryButton.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    public void showProgressBar(boolean isVisible){
        progressBarImageView.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    public void showNoResultsFoundLayout(boolean isVisible){
        noResultsFoundLayout.setVisibility(isVisible?View.VISIBLE:View.GONE);
    }

    // Hiding the status bar
    public void hideStatusBar(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
