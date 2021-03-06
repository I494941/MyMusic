/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wjf.mymusic.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.wjf.mymusic.R;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.sp.SharePreferenceManager;
import com.wjf.mymusic.util.ToastUtil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseAppCompatActivity extends AppCompatActivity {

    protected Context mContext = null;
    private Unbinder mUnbinder;

    protected SharePreferenceManager sp = new SharePreferenceManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTheme();
        setTranslucentStatus();
        mContext = this;
        BaseAppManager.getInstance().addActivity(this);
        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }
        initViewsAndEvents();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            try {
                mUnbinder.unbind();
                mUnbinder = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        BaseAppManager.getInstance().removeActivity(this);
    }

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();

    /**
     * startActivity
     *
     * @param clazz
     */
    protected void startActivity(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * startActivity with bundle
     *
     * @param clazz
     * @param bundle
     */
    protected void startActivity(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * startActivityForResult
     *
     * @param clazz
     * @param requestCode
     */
    protected void startActivityForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz
     * @param requestCode
     * @param bundle
     */
    protected void startActivityForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * set status bar translucency
     *
     * @param on
     */
    public void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    public void setTranslucentStatus() {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else {
            setTranslucentStatus(true);
        }
    }

    public void setStatusBarTintColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(color);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintColor(color);
        }
    }

    public void showShortToast(String msg) {
        ToastUtil.show(this, msg);
    }

    protected void initTheme() {
        int themeId = sp.getInt(Constants.THEME_SELECT);
        switch (themeId) {
            case 0:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.biliPink));
                setTheme(R.style.BiLiPinkTheme);
                break;
            case 1:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.zhihuBlue));
                setTheme(R.style.ZhiHuBlueTheme);
                break;
            case 2:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.kuanGreen));
                setTheme(R.style.KuAnGreenTheme);
                break;
            case 3:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.cloudRed));
                setTheme(R.style.CloudRedTheme);
                break;
            case 4:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.tengluoPurple));
                setTheme(R.style.TengLuoPurpleTheme);
                break;
            case 5:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.seaBlue));
                setTheme(R.style.SeaBlueTheme);
                break;
            case 6:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.grassGreen));
                setTheme(R.style.GrassGreenTheme);
                break;
            case 7:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.coffeeBrown));
                setTheme(R.style.CoffeeBrownTheme);
                break;
            case 8:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.lemonOrange));
                setTheme(R.style.LemonOrangeTheme);
                break;
            case 9:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.startSkyGray));
                setTheme(R.style.StartSkyGrayTheme);
                break;
            case 10:
                setStatusBarTintColor(ContextCompat.getColor(this, R.color.nightActionbar));
                setTheme(R.style.NightModeTheme);
                break;
        }
    }
}
