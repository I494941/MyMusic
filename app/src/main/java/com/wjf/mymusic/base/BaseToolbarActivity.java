package com.wjf.mymusic.base;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wjf.mymusic.R;
import com.wjf.mymusic.util.ScreenUtil;

import butterknife.ButterKnife;

/**
 * Created by ws on 2017/10/26 0026.
 */

public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    protected Toolbar mToolbar;
    protected View mStatusView;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mStatusView = ButterKnife.findById(this, R.id.statusView);

        initStatusBar();
        initToolbar();
    }

    private void initStatusBar() {
        setStatusBarTintColor(ContextCompat.getColor(this, R.color.transparent));
        ViewGroup.LayoutParams layoutParams = mStatusView.getLayoutParams();
        layoutParams.height = ScreenUtil.getStatusBarHeightByReflact(this);
        mStatusView.setLayoutParams(layoutParams);
    }

    public void initToolbar() {
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true);
                actionBar.setHomeAsUpIndicator(R.drawable.arrow_back);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            back();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void back() {
        finish();
    }
}