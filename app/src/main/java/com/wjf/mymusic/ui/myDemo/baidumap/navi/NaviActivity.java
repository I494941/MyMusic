package com.wjf.mymusic.ui.myDemo.baidumap.navi;


import android.view.View;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base.BaseRecyclerViewActivity;

import butterknife.OnClick;

/**
 * Created by wjf on 2019/3/25.
 */
public class NaviActivity extends BaseToolbarActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_navi;
    }

    @Override
    protected void initViewsAndEvents() {


    }

    @OnClick({R.id.tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:

                break;
        }
    }
}
