package com.wjf.mymusic;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wjf.mymusic.base.BaseAppCompatActivity;

import butterknife.BindView;

/**
 * Created by wjf on 2019/1/14.
 */
public class MainActivity extends BaseAppCompatActivity implements MainContract.View {

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

    private MainPresenter mPrensenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViewsAndEvents() {
        mTvTitle.setText("MyMusic");
        initToolbar();
        initNav();

        mPrensenter = new MainPresenter(this, this);
        mPrensenter.getBingPic();
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
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initNav() {
        View headerView = mNavView.getHeaderView(0);
        mNavHeadIv = headerView.findViewById(R.id.nav_head_bg_iv);

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mDrawerLayout.closeDrawers();
                switch (item.getItemId()) {
                    case R.id.nav_theme:
                        showShortToast("主题");
                        break;
                    case R.id.nav_night_mode:
                        showShortToast("模式");
                        break;
                    case R.id.nav_about_me:
                        showShortToast("关于");
                        break;
                    case R.id.nav_logout:
                        showShortToast("退出");
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

    @Override
    public void getBingPicSucc(String str) {
        Glide.with(MainActivity.this).load(str).into(mNavHeadIv);
    }
}
