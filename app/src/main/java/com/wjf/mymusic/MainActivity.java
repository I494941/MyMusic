package com.wjf.mymusic;

import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjf.mymusic.base.BaseAppCompatActivity;
import com.wjf.mymusic.constants.Constants;
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
    @BindView(R.id.tv_title)
    TextView mTvTitle;
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

        mTvTitle.setText(R.string.app_name);
        initToolbar();
        initNav();
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
            actionBar.setDisplayShowTitleEnabled(false);
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
        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_theme:
                        startActivity(ThemeActivity.class);
                        finish();
                        break;
                    case R.id.nav_night_mode:
                        showShortToast("模式");
                        break;
                    case R.id.nav_about_me:
                        showShortToast("关于");
                        break;
                    case R.id.nav_quit:
                        finish();
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showShortToast("再按一次退出!");
                exitTime = System.currentTimeMillis();
            } else
                moveTaskToBack(true);
            return true;
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }
}
