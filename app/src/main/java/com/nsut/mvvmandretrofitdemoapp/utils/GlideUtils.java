package com.nsut.mvvmandretrofitdemoapp.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nsut.mvvmandretrofitdemoapp.R;

public class GlideUtils {

    public static void loadImageFromUrl(Context context, ImageView imageView, String url, int defaultImageID){
        Glide.with(context)
                .load(url)
                .placeholder(defaultImageID)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }

}
