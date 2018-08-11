package com.fickinger.arnaud.inreal.util;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fickinger.arnaud.inreal.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class GlideUtil {
    public static void loadImage(String url, ImageView imageView) {
        Context context = imageView.getContext();
        ColorDrawable cd = new ColorDrawable(ContextCompat.getColor(context, R.color.blue_grey_500));
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(cd)
                        .centerCrop())
                .transition(withCrossFade())
                .into(imageView);
    }

    public static void loadThumbnail(String url, ImageView imageView) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.blank)
                        .dontAnimate()
                        .fitCenter())
                .into(imageView);
    }

    public static void loadProfileIcon(String url, ImageView imageView) {
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.ic_person_outline_black_24dp)
                        .dontAnimate()
                        .fitCenter())
                .into(imageView);
    }
}
