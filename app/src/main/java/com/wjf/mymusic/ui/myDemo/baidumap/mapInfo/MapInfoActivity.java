package com.wjf.mymusic.ui.myDemo.baidumap.mapInfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.baidu.location.BDLocation;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.ui.myDemo.baidumap.service.LocationService;
import com.wjf.mymusic.util.LocationUtil;
import com.wjf.mymusic.util.LogUtil;

/**
 * Created by wjf on 2019/3/28.
 */
public class MapInfoActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map_info;
    }

    @Override
    protected void initViewsAndEvents() {
        if (LocationService.isLocating) {
            mTv1.setText("正在定位……");
        } else {
            mTv1.setText("开启服务");
        }
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                Intent mIntent = new Intent(MapInfoActivity.this, LocationService.class);
                startService(mIntent);
                mTv1.setText("正在定位……");
                break;
            case R.id.tv2:
                Intent mIntent2 = new Intent(MapInfoActivity.this, LocationService.class);
                stopService(mIntent2);
                mTv1.setText("开启服务");
                break;
            case R.id.tv3:
                LocationUtil.getLocation(mContext, new LocationUtil.OnResponse() {
                    @Override
                    public void getAddrDetail(BDLocation location) {

                        LogUtil.e("1111====MapInfoActivity====", "location.getLocType() = " + location.getLocType());
                        LogUtil.e("1111====MapInfoActivity====", "location.getLongitude() = " + location.getLongitude());
                        LogUtil.e("1111====MapInfoActivity====", "location.getLatitude() = " + location.getLatitude());
                        LogUtil.e("1111====MapInfoActivity====", "location.getAddrStr() = " + location.getAddrStr());
                        LogUtil.e("1111====MapInfoActivity====", "location.getCountry() = " + location.getCountry());
                        LogUtil.e("1111====MapInfoActivity====", "location.getProvince() = " + location.getProvince());
                        LogUtil.e("1111====MapInfoActivity====", "location.getCity() = " + location.getCity());
                        LogUtil.e("1111====MapInfoActivity====", "location.getDistrict() = " + location.getDistrict());
                        LogUtil.e("1111====MapInfoActivity====", "location.getStreet() = " + location.getStreet());
                        LogUtil.e("1111====MapInfoActivity====", "location.getLocationDescribe() = " + location.getLocationDescribe());
                    }
                });
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
