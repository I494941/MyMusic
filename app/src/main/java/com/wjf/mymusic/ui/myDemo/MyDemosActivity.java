package com.wjf.mymusic.ui.myDemo;

import android.view.View;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baseRecyclerViewActivity.BaseRecyclerViewActivity;

import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class MyDemosActivity extends BaseToolbarActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick({R.id.tv_multi_recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_multi_recyclerview:
                startActivity(BaseRecyclerViewActivity.class);
                break;
        }
    }
}
