package com.wjf.mymusic.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by wangshuai on 2018/5/17 0017 10:56.
 */
public class GlideUtil {
    public static void setImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context).load(url).apply(options).into(iv);
    }

    public static void setImage(Context context, String url, ImageView iv, int drawable) {
        RequestOptions options = new RequestOptions()
                .placeholder(drawable)
                .error(drawable);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    public static void setCircleImage(Context context, String url, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideCircleTransform())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    public static void setCircleImage(Context context, String url, ImageView iv, int drawable) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideCircleTransform())
                .placeholder(drawable)
                .error(drawable);
        Glide.with(context)
                .load(url)
                .apply(options)
                .into(iv);
    }

    public static void setCircleImage(Context context, Bitmap bm, ImageView iv, int drawable) {
        RequestOptions options = new RequestOptions()
                .transform(new GlideCircleTransform())
                .placeholder(drawable)
                .error(drawable);
        Glide.with(context)
                .load(bm)
                .apply(options)
                .into(iv);
    }

    public static void setImageResource(Context context, int drawable, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .centerCrop();
        Glide.with(context).load(drawable).apply(options).into(iv);
    }

    public static void setImageResourceNoCache(Context context, int drawable, ImageView iv) {
        RequestOptions options = new RequestOptions()
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .centerCrop();
        Glide.with(context).load(drawable).apply(options).into(iv);
    }
}
