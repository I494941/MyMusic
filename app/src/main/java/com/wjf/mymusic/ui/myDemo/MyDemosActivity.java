package com.wjf.mymusic.ui.myDemo;

import android.view.View;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.BaiduMapActivity;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.EventBusFirstActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.RecyclerViewActivity;

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

    @OnClick({R.id.tv_recyclerview, R.id.tv_eventbus, R.id.tv_baidu_map})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_recyclerview:
                startActivity(RecyclerViewActivity.class);
                break;
            case R.id.tv_eventbus:
                startActivity(EventBusFirstActivity.class);
                break;
            case R.id.tv_baidu_map:
                startActivity(BaiduMapActivity.class);
                break;
        }
    }
}
