package com.tweentyscoops.contactworkshop.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ImageLoader {

    public static void url(ImageView view, String url) {
        RequestOptions options = new RequestOptions().placeholder(android.R.color.darker_gray)
                .dontAnimate()
                .dontTransform()
                .error(android.R.color.darker_gray);
        Glide.with(view.getContext())
                .load(url)
                .apply(options)
                .into(view);
    }
}