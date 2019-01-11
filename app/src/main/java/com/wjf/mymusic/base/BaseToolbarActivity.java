package com.wjf.mymusic.base;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import com.wjf.mymusic.R;

/**
 * Created by ws on 2017/10/26 0026.
 */

public abstract class BaseToolbarActivity extends BaseAppCompatActivity {
    protected Toolbar mToolbar;
    protected TextView mTvTitle, mTvRight;
    protected ImageView mIvMore;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = ButterKnife.findById(this, R.id.toolbar);
        mTvTitle = ButterKnife.findById(this, R.id.tv_title);
        mTvRight = ButterKnife.findById(this, R.id.tv_right);
        mIvMore = ButterKnife.findById(this, R.id.toolbar_iv_more);

        initToolbar();
    }

    public void initToolbar() {
        if (mToolbar != null) {
            mToolbar.setNavigationIcon(R.drawable.arrow_back);
            setSupportActionBar(mToolbar);
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.setDisplayShowTitleEnabled(false);
                //actionBar.setHomeButtonEnabled(true);
                actionBar.setDisplayHomeAsUpEnabled(true);
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