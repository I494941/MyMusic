package com.wjf.mymusic.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.wjf.mymusic.R;


public class LoadingDialog extends Dialog {

    private Context mContext;

    public LoadingDialog(Context context) {
        this(context, R.style.ProgressDialog);
    }

    public LoadingDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
        setContentView(R.layout.loading_dialog);
        getWindow().getAttributes().gravity = Gravity.CENTER;
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        ImageView imageView = findViewById(R.id.ivLoading);
        Glide.with(mContext).asGif().load(R.drawable.loading_gif).into(imageView);
    }
}
