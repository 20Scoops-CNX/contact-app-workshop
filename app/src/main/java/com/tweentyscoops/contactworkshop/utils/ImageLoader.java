package com.tweentyscoops.contactworkshop.utils;

import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageLoader {

    public static void url(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .placeholder(android.R.color.darker_gray)
                .error(android.R.color.darker_gray)
                .into(view);
    }
}