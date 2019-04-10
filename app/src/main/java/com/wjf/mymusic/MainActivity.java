package com.wjf.mymusic;

import com.google.android.material.navigation.NavigationView;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.wjf.mymusic.base.BaseAppCompatActivity;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.ui.about.AboutActivity;
import com.wjf.mymusic.ui.themeActivity.ThemeActivity;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.ScreenUtil;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/14.
 */
public class MainActivity extends BaseAppCompatActivity {

    @BindView(R.id.statusView)
    View mStatusView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private ImageView mNavHeadIv;
    private long exitTime = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        initStatusBar();
        initToolbar();
        initNav();
        refreshNightModeTitle();
    }

    private void initStatusBar() {
        setStatusBarTintColor(ContextCompat.getColor(this, R.color.transparent));
        ViewGroup.LayoutParams layoutParams = mStatusView.getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeightByReflact(this);
        LogUtil.e("getStatusBarHeight", ScreenUtil.getStatusBarHeightByReflact(this) + "");
        mStatusView.setLayoutParams(layoutParams);
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.drawer_menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNav() {
        View headerView = mNavView.getHeaderView(0);
        mNavHeadIv = headerView.findViewById(R.id.nav_head_bg_iv);
        Glide.with(MainActivity.this).load(sp.getString(Constants.BING_URL)).into(mNavHeadIv);
        mNavView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            switch (item.getItemId()) {
                case R.id.nav_theme:
                    startActivity(ThemeActivity.class);
                    finish();
                    break;
                case R.id.nav_night_mode:
                    if (sp.getInt(Constants.THEME_SELECT) == Constants.THEME_SIZE - 1) {
                        //当前为夜间模式，则恢复之前的主题
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        sp.putInt(Constants.THEME_SELECT, sp.getInt(Constants.PRE_THEME_SELECT));
                    } else {
                        //当前为白天模式，则切换到夜间模式
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        sp.putInt(Constants.PRE_THEME_SELECT, sp.getInt(Constants.THEME_SELECT));
                        sp.putInt(Constants.THEME_SELECT, Constants.THEME_SIZE - 1);
                    }
                    recreate();
                    break;
                case R.id.nav_about_me:
                    startActivity(AboutActivity.class);
                    break;
                case R.id.nav_quit:
                    finish();
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showShortToast(getString(R.string.re_press_quit));
                exitTime = System.currentTimeMillis();
            } else
                moveTaskToBack(true);
            return true;
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    private void refreshNightModeTitle() {
        if (sp.getInt(Constants.THEME_SELECT) == Constants.THEME_SIZE - 1)
            mNavView.getMenu().findItem(R.id.nav_night_mode).setTitle(R.string.day_mode);
        else
            mNavView.getMenu().findItem(R.id.nav_night_mode).setTitle(R.string.night_mode);
    }
}
