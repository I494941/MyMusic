package com.wjf.mymusic.app;

import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.sp.SharePreferenceManager;

/**
 * Created by wjf on 2019/1/19.
 */
public class MyApplication extends Application {

    private static Context mContext;
    protected SharePreferenceManager sp = new SharePreferenceManager(this);

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        initNightMode();
    }

    protected void initNightMode() {
        if (sp.getInt(Constants.THEME_SELECT) == Constants.THEME_SIZE - 1)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    public static Context getContext() {
        return mContext;
    }
}
