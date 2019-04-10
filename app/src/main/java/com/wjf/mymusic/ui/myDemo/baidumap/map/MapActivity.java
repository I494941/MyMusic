package com.wjf.mymusic.ui.myDemo.baidumap.map;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.util.LocationUtil;

import butterknife.BindView;

/**
 * Created by wjf on 2019/3/25.
 */
public class MapActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private MyLocationConfiguration mLocationConfiguration;
    private LocationClient mLocationClient;
    private int mSpan = 1000;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViewsAndEvents() {
        mBaiduMap = mMapView.getMap();
        ////普通地图 ,mBaiduMap是地图控制器对象
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        ////卫星地图
        //mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
        ////开启交通图
        //mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(18));

        mLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null);
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        mLocationClient = new LocationClient(this);

        LocationUtil.getLocationContinue(mLocationClient, mSpan, location -> {

            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);
        });
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mLocationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
