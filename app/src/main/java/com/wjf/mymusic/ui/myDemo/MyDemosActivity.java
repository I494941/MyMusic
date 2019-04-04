package com.wjf.mymusic.ui.myDemo;

import android.Manifest;
import android.view.View;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.map.MapActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.mapInfo.MapInfoActivity;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.EventBusFirstActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi.MultiRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base.BaseRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered.StaggeredRecyclerViewActivity;

import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class MyDemosActivity extends BaseToolbarActivity {

    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick({R.id.tv_base_recyclerview, R.id.tv_multi_recyclerview, R.id.tv_staggered_recyclerview,
            R.id.tv_eventbus, R.id.tv_baidu_map, R.id.tv_baidu_map_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_base_recyclerview:
                startActivity(BaseRecyclerViewActivity.class);
                break;
            case R.id.tv_multi_recyclerview:
                startActivity(MultiRecyclerViewActivity.class);
                break;
            case R.id.tv_staggered_recyclerview:
                startActivity(StaggeredRecyclerViewActivity.class);
                break;
            case R.id.tv_eventbus:
                startActivity(EventBusFirstActivity.class);
                break;
            case R.id.tv_baidu_map:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startActivity(MapActivity.class);
                            }
                        });
                break;
            case R.id.tv_baidu_map_info:
                startActivity(MapInfoActivity.class);
                break;
        }
    }
}
