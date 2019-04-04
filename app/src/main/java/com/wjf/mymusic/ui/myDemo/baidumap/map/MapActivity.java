package com.wjf.mymusic.ui.myDemo.baidumap.map;


import butterknife.BindView;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.*;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.util.LocationUtil;

/**
 * Created by wjf on 2019/3/25.
 */
public class MapActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private MyLocationConfiguration mLocationConfiguration;
    private LocationClient locationClient;

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

        mLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, BitmapDescriptorFactory.fromResource(R.drawable.location));
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);

        locationClient = new LocationClient(this);

        LocationUtil.getLocationContinue(locationClient, 1000, location -> {

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
        locationClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
}
