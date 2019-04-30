package com.wjf.mymusic.ui.myDemo.baidumap;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

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

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class BaiduMapActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.tv2)
    TextView mTv2;

    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {

        mTv1.setText(R.string.BaiduMap);
        mTv3.setText(R.string.BaiduMapInfo);
        mTv3.setVisibility(View.VISIBLE);
        mTv4.setText(R.string.BaiduMapNavi);
        mTv4.setVisibility(View.VISIBLE);
        mTv5.setText(R.string.BaiduMapTrace);
        mTv5.setVisibility(View.VISIBLE);
        mTv2.setText(R.string.BaiduMapTrail);
    }

    @OnClick({R.id.tv1, R.id.tv3, R.id.tv4, R.id.tv5,  R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
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
            case R.id.tv3:
                startActivity(MapInfoActivity.class);
                break;
            case R.id.tv4:
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
            case R.id.tv5:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startActivity(TraceActivity.class);
                            }
                        });
                break;
            case R.id.tv2:
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
