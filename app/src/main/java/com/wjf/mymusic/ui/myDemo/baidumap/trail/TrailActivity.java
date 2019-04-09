package com.wjf.mymusic.ui.myDemo.baidumap.trail;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.util.LocationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wjf on 2019/4/8.
 */
public class TrailActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private float mCurrentZoom;
    private int mSpan = 3000;
    private boolean isFirstLoc = true;

    private  LatLng last;
    private  List<LatLng> points = new ArrayList<>();
    private double mCurrentLat;
    private double mCurrentLon;
    private MyLocationData locData;
    private MapStatus.Builder builder;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViewsAndEvents() {
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置地图 缩放监听
        initMapStatusChangeListener();

        //设置定位图标类型为跟随模式
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.FOLLOWING, true, null));

//        initLocation();
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

    private void initMapStatusChangeListener() {
        /**添加地图缩放状态变化监听，当手动放大或缩小地图时，拿到缩放后的比例，然后获取到下次定位，
         *  给地图重新设置缩放比例，否则地图会重新回到默认的mCurrentZoom缩放比例
         */
        mCurrentZoom = 18;
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus arg0) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus arg0) {
                mCurrentZoom = arg0.zoom;//获取手指缩放地图后的值
            }

            @Override
            public void onMapStatusChange(MapStatus arg0) {
            }
        });
    }
//
//    private void initLocation() {
//        mLocationClient = new LocationClient(this);
//        LocationUtil.getLocationContinue(mLocationClient, mSpan, location -> {
//            if (location == null || mMapView == null) {
//                return;
//            }
//
//            //            //注意:这里只要gps点，需要在室外定
//            //            if (location.getLocType() == BDLocation.TypeGpsLocation) {
//
//            if (isFirstLoc) {//首次定位
//                /**第一个点很重要，决定了轨迹的效果，gps刚接收到信号时返回的一些点精度不高，
//                 * 尽量选一个精度相对较高的起始点，这个过程大概从gps刚接收到信号后5-10秒就可以完成，不影响效果。
//                 * 注：gps接收卫星信号少则十几秒钟，多则几分钟，
//                 * 如果长时间手机收不到gps，退出，重启手机再试，这是硬件的原因
//                 */
//
//                //选一个精度相对较高的起始点
//                LatLng ll = getMostAccuracyLocation(location);
//                if (ll == null) {
//                    return;
//                }
//                isFirstLoc = false;
//                points.add(ll);//加入集合
//                last = ll;
//
//                //显示当前定位点，缩放地图
//                locateAndZoom(location, ll);
//
//                //标记起点图层位置
//                MarkerOptions oStart = new MarkerOptions();// 地图标记覆盖物参数配置类
//                oStart.position(points.get(0));// 覆盖物位置点，第一个点为起点
//                oStart.icon(startBD);// 设置覆盖物图片
//                mBaiduMap.addOverlay(oStart); // 在地图上添加此图层
//                return;//画轨迹最少得2个点，首地定位到这里就可以返回了
//            }
//
//            //从第二个点开始
//            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//
//            /*
//            * sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，
//              可以设置点之间距离大于为5米才添加到集合中
//            */
//            if (DistanceUtil.getDistance(last, ll) < 5) {
//                return;
//            }
//
//            points.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中
//
//            last = ll;
//
//            //显示当前定位点，缩放地图
//            locateAndZoom(location, ll);
//
//            //清除上一次轨迹，避免重叠绘画
//            mMapView.getMap().clear();
//
//            //起始点图层也会被清除，重新绘画
//            MarkerOptions oStart = new MarkerOptions();
//            oStart.position(points.get(0));
//            oStart.icon(startBD);
//            mBaiduMap.addOverlay(oStart);
//
//            //将points集合中的点绘制轨迹线条图层，显示在地图上
//            OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(points);
//            mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
//            //            }
//        });
//    }

    /**
     * 首次定位很重要，选一个精度相对较高的起始点
     * 注意：如果一直显示gps信号弱，说明过滤的标准过高了，
     * 你可以将location.getRadius()>25中的过滤半径调大，比如>50，
     * 并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
     * 这里不是固定死的，你可以根据你的需求调整。
     */
    private LatLng getMostAccuracyLocation(final BDLocation location) {

        if (location.getRadius() > 25) {//gps位置精度大于25米的点直接弃用，
            return null;
        }

        LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

        if (last == null) {
            last = ll;
            return null;
        }

        if (DistanceUtil.getDistance(last, ll) > 5) {
            last = ll;
            points.clear();//有两点位置大于5，重新来过
            return null;
        }
        points.add(ll);
        last = ll;
        //有5个连续的点之间的距离小于5，认为gps已稳定，以最新的点为起始点
        if (points.size() >= 5) {
            points.clear();
            return ll;
        }
        return null;
    }
    
    //显示当前定位点，缩放地图
    private void locateAndZoom(BDLocation location, LatLng ll) {
        /**
         * 记录当前经纬度，当位置不变，手机转动，取得方向传感器的方向，
         给地图重新设置位置参数，在跟随模式下可使地图箭头随手机转动而转动
         */
        mCurrentLat = location.getLatitude();
        mCurrentLon = location.getLongitude();
        locData = new MyLocationData.Builder().accuracy(0)//去掉精度圈
                //此mCurrentDirection为自己获取到的手机传感器方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);//显示当前定位位置点

        //给地图设置缩放中心点，和缩放比例值
        builder = new MapStatus.Builder();
        builder.target(ll).zoom(mCurrentZoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }
}