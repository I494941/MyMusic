package com.wjf.mymusic.ui.myDemo.baidumap.trail;

import android.view.Gravity;
import android.view.View;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
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
import butterknife.OnClick;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

/**
 * Created by wjf on 2019/4/8.
 */
public class TrailActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private float mCurrentZoom;
    private int mSpan = 1000;
    private boolean mIsFirstLoc = true;

    private LatLng mLast;
    private List<LatLng> mLatLngs = new ArrayList<>();
    private MapStatus.Builder mBuilder;
    private BitmapDescriptor mStartBD, mStopBD;
    private Polyline mPolyline;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViewsAndEvents() {
        mIvMore.setVisibility(View.VISIBLE);

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置定位图标类型为跟随模式
        mBaiduMap.setMyLocationConfiguration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        //设置地图 缩放监听
        initMapStatusChangeListener();
        //始点图层图标、终点图层图标
        initStartAndStop();
        //开始定位
        initLocation();
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

    @OnClick({R.id.iv_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_more:
                QuickPopupBuilder.with(mContext)
                        .contentView(R.layout.popup_trail_popup)
                        .config(new QuickPopupConfig()
                                .gravity(Gravity.BOTTOM)
                                .withClick(R.id.tv1, v -> showShortToast("stop"))
                                .withClick(R.id.tv2, v -> showShortToast("clear")))
                        .show(mIvMore);
                break;
        }
    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()) {
    //            case R.id.item1:
    //                if (mLocationClient != null && mLocationClient.isStarted()) {
    //                    mLocationClient.stop();//停止定位
    //                    if (mLatLngs.size() > 0) {
    //                        drawPoint(false);
    //                    }
    //                }
    //                break;
    //            case R.id.item2:
    //                mLocationClient.stop();
    //                mLatLngs.clear();
    //                mMapView.getMap().clear();
    //                mIsFirstLoc = false;
    //                mLast = null;
    //                mLocationClient.start();
    //                break;
    //            default:
    //                break;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }

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

    private void initStartAndStop() {
        //始点图层图标
        mStartBD = BitmapDescriptorFactory.fromResource(R.drawable.start_point);
        //终点图层图标
        mStopBD = BitmapDescriptorFactory.fromResource(R.drawable.stop_point);
    }

    private void initLocation() {
        mLocationClient = new LocationClient(this);
        LocationUtil.getLocationContinue(mLocationClient, mSpan, location -> {
            if (location != null || mMapView != null) {
                if (mIsFirstLoc) {//首次定位
                    //第一个点很重要，决定了轨迹的效果，gps刚接收到信号时返回的一些点精度不高，
                    //尽量选一个精度相对较高的起始点，这个过程大概从gps刚接收到信号后5-10秒就可以完成，不影响效果。
                    //注：gps接收卫星信号少则十几秒钟，多则几分钟，
                    //如果长时间手机收不到gps，退出，重启手机再试，这是硬件的原因

                    //选一个精度相对较高的起始点
                    LatLng ll = getMostAccuracyLocation(location);
                    if (ll != null) {
                        mIsFirstLoc = false;
                        mLatLngs.add(ll);//加入集合
                        mLast = ll;

                        //显示当前定位点，缩放地图
                        locateAndZoom(location, ll);
                        //标记起点图层位置
                        drawPoint(true);
                        //画轨迹最少得2个点，首地定位到这里就可以返回了
                    }
                } else {
                    //从第二个点开始
                    LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());

                    //sdk回调gps位置的频率是1秒1个，位置点太近动态画在图上不是很明显，
                    //可以设置点之间距离大于为5米才添加到集合中
                    if (DistanceUtil.getDistance(mLast, ll) >= 5) {
                        mLatLngs.add(ll);//如果要运动完成后画整个轨迹，位置点都在这个集合中
                        mLast = ll;
                        //显示当前定位点，缩放地图
                        locateAndZoom(location, ll);
                        //清除上一次轨迹，避免重叠绘画
                        mMapView.getMap().clear();
                        //起始点图层也会被清除，重新绘画
                        drawPoint(true);
                        //将points集合中的点绘制轨迹线条图层，显示在地图上
                        drawLine();
                    }
                }
            }
        });
    }

    private void drawPoint(boolean isStart) {
        if (isStart) {
            //标记起点图标
            MarkerOptions oFinish = new MarkerOptions();
            oFinish.position(mLatLngs.get(0));
            oFinish.icon(mStartBD);
            mBaiduMap.addOverlay(oFinish);
        } else {
            //运动结束记得标记终点图标
            MarkerOptions oFinish = new MarkerOptions();
            oFinish.position(mLatLngs.get(mLatLngs.size() - 1));
            oFinish.icon(mStopBD);
            mBaiduMap.addOverlay(oFinish);
        }
    }

    private void drawLine() {
        OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(mLatLngs);
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
    }

    /**
     * 首次定位很重要，选一个精度相对较高的起始点
     * 注意：如果一直显示gps信号弱，说明过滤的标准过高了，
     * 你可以将location.getRadius()>25中的过滤半径调大，比如>50，
     * 并且将连续5个点之间的距离DistanceUtil.getDistance(last, ll ) > 5也调大一点，比如>10，
     * 这里不是固定死的，你可以根据你的需求调整。
     */
    private LatLng getMostAccuracyLocation(BDLocation location) {
        // TODO 显示第一次定位的点，不管准不准
        locateAndZoom(location, new LatLng(location.getLatitude(), location.getLongitude()));
        if (location.getRadius() <= 50) {//gps位置精度大于25米的点直接弃用，
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            if (mLast == null) {
                mLatLngs.clear();
                mLatLngs.add(ll);
                mLast = ll;
            } else {
                if (DistanceUtil.getDistance(mLast, ll) > 5) {
                    mLatLngs.clear();//有两点位置大于5，重新来过
                    mLatLngs.add(ll);
                    mLast = ll;
                } else {
                    mLatLngs.add(ll);
                    mLast = ll;
                    //有5个连续的点之间的距离小于5，认为gps已稳定，以最新的点为起始点
                    if (mLatLngs.size() >= 5) {
                        mLatLngs.clear();
                        return ll;
                    }
                }
            }
        }
        return null;
    }

    //显示当前定位点，缩放地图
    private void locateAndZoom(BDLocation location, LatLng ll) {
        /**
         * 记录当前经纬度，当位置不变，手机转动，取得方向传感器的方向，
         * 给地图重新设置位置参数，在跟随模式下可使地图箭头随手机转动而转动
         */
        MyLocationData locData = new MyLocationData.Builder().accuracy(0)//去掉精度圈
                //此mCurrentDirection为自己获取到的手机传感器方向信息，顺时针0-360
                .direction(location.getDirection()).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);//显示当前定位位置点

        //给地图设置缩放中心点，和缩放比例值
        mBuilder = new MapStatus.Builder();
        mBuilder.target(ll).zoom(mCurrentZoom);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(mBuilder.build()));
    }
}