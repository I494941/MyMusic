package com.wjf.mymusic.base;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import com.wjf.mymusic.R;
import com.wjf.mymusic.util.ScreenUtil;

/**
 * Created by ws on 2017/10/26 0026.
 */

public abstract class BaseToolbarActivity extends BaseAppCompatActivity {

    protected Toolbar mToolbar;
    protected View mStatusView;
    protected TextView mTvRight;
    protected ImageView mIvMore;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mToolbar = findViewById(R.id.toolbar);
        mStatusView = findViewById(R.id.statusView);
        mTvRight = findViewById(R.id.tv_right);
        mIvMore = findViewById(R.id.iv_more);

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