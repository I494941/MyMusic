package com.wjf.mymusic.ui.myDemo.baidumap.trace;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wjf on 2019/4/8.
 */
public class TraceActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private Polyline mPolyline;
    private List<LatLng> latLngs = new ArrayList();

    private InfoWindow mInfoWindow;  //地图中显示信息窗口
    private BitmapDescriptor startBD;
    private BitmapDescriptor finishBD;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map;
    }

    @Override
    protected void initViewsAndEvents() {
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        //获取运动后的定位点
        initLocation();

        LatLng target = new LatLng(36.686312, 117.081062);
        //设置缩放中点LatLng target，和缩放比例
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(target).zoom(18f);

        //地图设置缩放状态
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        /**
         * 配置线段图层参数类： PolylineOptions
         * ooPolyline.width(13)：线宽
         * ooPolyline.color(0xAAFF0000)：线条颜色红色
         * ooPolyline.points(latLngs)：List<LatLng> latLngs位置点，将相邻点与点连成线就成了轨迹了
         */
        OverlayOptions ooPolyline = new PolylineOptions().width(13).color(0xAAFF0000).points(latLngs);

        //在地图上画出线条图层，mPolyline：线条图层
        mPolyline = (Polyline) mBaiduMap.addOverlay(ooPolyline);
        mPolyline.setZIndex(3);

        initTrail();
    }

    private void initLocation() {
        LatLng latLng = new LatLng(36.686312, 117.081062);
        latLngs.add(latLng);
        latLng = new LatLng(36.686312, 117.081062);
        latLngs.add(latLng);
        latLng = new LatLng(36.685652, 117.074087);
        latLngs.add(latLng);
        latLng = new LatLng(36.68526, 117.068284);
        latLngs.add(latLng);
        latLng = new LatLng(36.684495, 117.062545);
        latLngs.add(latLng);
        latLng = new LatLng(36.678056, 117.062603);
        latLngs.add(latLng);
        latLng = new LatLng(36.672503, 117.062437);
        latLngs.add(latLng);
        latLng = new LatLng(36.666849, 117.061033);
        latLngs.add(latLng);
        latLng = new LatLng(36.666857, 117.053621);
        latLngs.add(latLng);
        latLng = new LatLng(36.66205, 117.051464);
        latLngs.add(latLng);
        latLng = new LatLng(36.664672, 117.069188);
        latLngs.add(latLng);
    }

    private void initTrail() {
        //始点图层图标
        startBD = BitmapDescriptorFactory
                .fromResource(R.drawable.startpoint);
        //终点图层图标
        finishBD = BitmapDescriptorFactory
                .fromResource(R.drawable.finishpoint);


        MarkerOptions oStart = new MarkerOptions();//地图标记类型的图层参数配置类
        oStart.position(latLngs.get(0));//图层位置点，第一个点为起点
        oStart.icon(startBD);//设置图层图片
        oStart.zIndex(1);//设置图层Index
        //添加起点图层
        Marker mMarkerA = (Marker) (mBaiduMap.addOverlay(oStart));

        //添加终点图层
        MarkerOptions oFinish = new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).icon(finishBD).zIndex(2);
        Marker mMarkerB = (Marker) (mBaiduMap.addOverlay(oFinish));

        //设置图层点击监听回调
        mBaiduMap.setOnMarkerClickListener(marker -> {
            if (marker.getZIndex() == mMarkerA.getZIndex()) {//如果是起始点图层
                TextView textView = new TextView(getApplicationContext());
                textView.setText("起点");
                textView.setTextColor(Color.BLACK);
                textView.setGravity(Gravity.CENTER);
                textView.setBackgroundResource(R.drawable.popup);

                //设置信息窗口点击回调
                InfoWindow.OnInfoWindowClickListener listener = () -> {
                    //这里是主线线程，可以实现自己的一些功能
                    Toast.makeText(getApplicationContext(), "这里是起点", Toast.LENGTH_SHORT).show();
                    mBaiduMap.hideInfoWindow();//隐藏信息窗口
                };

                LatLng latLng = marker.getPosition();//信息窗口显示的位置点

                /**
                 * 通过传入的 bitmap descriptor 构造一个 InfoWindow
                 * bd - 展示的bitmap
                 position - InfoWindow显示的位置点
                 yOffset - 信息窗口会与图层图标重叠，设置Y轴偏移量可以解决
                 listener - 点击监听者
                 */
                mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(textView), latLng, -47, listener);
                mBaiduMap.showInfoWindow(mInfoWindow);//显示信息窗口
            } else if (marker.getZIndex() == mMarkerB.getZIndex()) {//如果是终点图层
                Button button = new Button(getApplicationContext());
                button.setText("终点");
                button.setOnClickListener(v -> {
                    Toast.makeText(getApplicationContext(), "这里是终点", Toast.LENGTH_SHORT).show();
                    mBaiduMap.hideInfoWindow();
                });

                LatLng latLng = marker.getPosition();
                /**
                 * 通过传入的 view 构造一个 InfoWindow, 此时只是利用该view生成一个Bitmap绘制在地图中，监听事件由自己实现。
                 view - 展示的 view
                 position - 显示的地理位置
                 yOffset - Y轴偏移量
                 */
                mInfoWindow = new InfoWindow(button, latLng, -47);
                mBaiduMap.showInfoWindow(mInfoWindow);
            }
            return true;
        });

        //也可以给运动轨迹添加点击事件
        mBaiduMap.setOnPolylineClickListener(polyline -> {
            if (polyline.getZIndex() == mPolyline.getZIndex()) {
                Toast.makeText(getApplicationContext(), "点数：" + polyline.getPoints().size() + ",width:" + polyline.getWidth(), Toast.LENGTH_SHORT).show();
            }
            return false;
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
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        startBD.recycle();
        finishBD.recycle();
        super.onDestroy();
    }
}