package com.wjf.mymusic.ui.myDemo.baidumap;

import android.Manifest;
import android.view.View;

import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.map.MapActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.mapInfo.MapInfoActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.navi.NavigationUtil;
import com.wjf.mymusic.ui.myDemo.baidumap.trace.TraceActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.trail.TrailActivity;
import com.wjf.mymusic.util.ToastUtil;

import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class BaiduMapActivity extends BaseToolbarActivity {

    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_baidu_map;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick({R.id.tv_baidu_map, R.id.tv_baidu_map_info, R.id.tv_baidu_map_navi, R.id.tv_baidu_map_trace, R.id.tv_baidu_map_trail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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
            case R.id.tv_baidu_map_navi:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                                    ToastUtil.show(mContext, "正在算路，请稍等...");
                                    NavigationUtil.routeplanToNavi(mContext, BNRoutePlanNode.CoordinateType.BD09LL, 116, 35, "我的位置", 117, 36, null);

                                }
                            }
                        });
                break;
            case R.id.tv_baidu_map_trace:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startActivity(TraceActivity.class);
                            }
                        });
                break;
            case R.id.tv_baidu_map_trail:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startActivity(TrailActivity.class);
                            }
                        });
                break;
        }
    }
}
