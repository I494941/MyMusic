package com.wjf.mymusic.ui.myDemo.baidumap.trace;

import android.graphics.Color;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjf on 2019/4/8.
 */
public class TraceActivity extends BaseToolbarActivity {

    @BindView(R.id.bmapView)
    MapView mMapView;

    private BaiduMap mBaiduMap;
    private List<LatLng> latLngs = new ArrayList();

    private InfoWindow mInfoWindow;  //地图中显示信息窗口
    private BitmapDescriptor startBD, finishBD;
    private Polyline mPolyline;
    private Marker mMarkerA, mMarkerB, mMarker1, mMarker2;

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
        initLocations();
        //TODO  判空
        LatLng target = latLngs.get(latLngs.size() - 1);
        //设置缩放中点LatLng target，和缩放比例
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(target).zoom(18f);

        //地图设置缩放状态
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        //画点
        drawPoint();
        //画线
        drawLine();
        //画起点、终点  添加点击事件
        initStartAndStop();
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

    private void initLocations() {
        LatLng latLng = new LatLng(36.686312, 117.081062);
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

    private void drawPoint() {
        /**
         * 绘制Marker，地图上常见的类似气球形状的图层
         */

        LatLng latLng = new LatLng(36.686312, 117.079667);
        MarkerOptions markerOptions = new MarkerOptions();//参数设置类
        markerOptions.position(latLng);//marker坐标位置
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.position));//marker图标，可以自定义
        markerOptions.draggable(false);//是否可拖拽，默认不可拖拽
        markerOptions.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
        markerOptions.alpha(0.8f);//marker图标透明度，0~1.0，默认为1.0
        markerOptions.animateType(MarkerOptions.MarkerAnimateType.drop);//marker出现的方式，从天上掉下
        markerOptions.flat(false);//marker突变是否平贴地面
        markerOptions.zIndex(1);//index

        ////Marker动画效果
        //markerOptions.icons(bitmapList);//如果需要显示动画，可以设置多张图片轮番显示
        //markerOptions.period(10);//每个10ms显示bitmapList里面的图片

        mMarker1 = (Marker) mBaiduMap.addOverlay(markerOptions);//在地图上增加mMarker图层

        MarkerOptions markerOptions2 = new MarkerOptions();//参数设置类
        markerOptions2.position(latLngs.get(latLngs.size() - 1));//marker坐标位置
        markerOptions2.icon(BitmapDescriptorFactory.fromResource(R.drawable.position));//marker图标，可以自定义
        markerOptions2.draggable(false);//是否可拖拽，默认不可拖拽
        markerOptions2.anchor(0.5f, 1.0f);//设置 marker覆盖物与位置点的位置关系，默认（0.5f, 1.0f）水平居中，垂直下对齐
        markerOptions2.alpha(0.8f);//marker图标透明度，0~1.0，默认为1.0
        markerOptions2.animateType(MarkerOptions.MarkerAnimateType.drop);//marker出现的方式，从天上掉下
        markerOptions2.flat(false);//marker突变是否平贴地面
        markerOptions2.zIndex(1);//index

        ////Marker动画效果
        //markerOptions.icons(bitmapList);//如果需要显示动画，可以设置多张图片轮番显示
        //markerOptions.period(10);//每个10ms显示bitmapList里面的图片

        mMarker2 = (Marker) mBaiduMap.addOverlay(markerOptions2);//在地图上增加mMarker图层
    }

    private void drawLine() {
        /**
         * 绘制折线
         */
        PolylineOptions polylineOptions = new PolylineOptions();//参数设置类
        polylineOptions.width(10);//宽度,单位：像素
        polylineOptions.color(0xAAFF0000);//设置折线颜色
        polylineOptions.points(latLngs);//折线顶点坐标集

        /**
         * colorValue：List<Integer>，折线颜色集合
         * 如果这里设置了折现颜色集合，那么options.color()中设置的颜色会被覆盖
         * 例：5个点能画出4条线段，每条线段绘制时按照索引依次取值，
         如果颜色个数小于线段个数，剩余线段取最后一个颜色绘制
         */
        //polylineOptions.colorsValues(colorValue);
        polylineOptions.dottedLine(false);//是否是虚线，默认为false
        polylineOptions.zIndex(9);//index
        polylineOptions.visible(true);//是否可见，默认可见
        //polylineOptions.extraInfo(bundle);//附加bundle


        /**
         * 设置线段纹理样式，所以折线纹理样式是可以自己定义的
         * textureList:折现纹理样式集合
         * textureIndexs:折线纹理索引，绘制时按照索引从textureList里面取样式，
         如下面代码中，0和1就是textureList的索引，这样就会先取a再取b，
         如果textureIndexs.add(1)然后再textureIndexs.add(0)就先取b再取a了，
         所以textureIndexs里面的值不能乱加，应该是小于等于textureList个数的值，不然就出错了。
         */
        //List<BitmapDescriptor> textureList = new ArrayList<BitmapDescriptor>();
        //textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.a));
        //textureList.add(BitmapDescriptorFactory.fromResource(R.drawable.b));
        //polylineOptions.customTextureList(textureList);//设置折现纹理样式
        //List<Integer> textureIndexs = new ArrayList<Integer>();
        //textureIndexs.add(0);
        //textureIndexs.add(1);
        //polylineOptions.textureIndex(textureIndexs);

        mPolyline = (Polyline) mBaiduMap.addOverlay(polylineOptions);//在地图上增加mPolyline图层
    }

    private void initStartAndStop() {
        //始点图层图标
        startBD = BitmapDescriptorFactory
                .fromResource(R.drawable.start_point);
        //终点图层图标
        finishBD = BitmapDescriptorFactory
                .fromResource(R.drawable.stop_point);


        MarkerOptions oStart = new MarkerOptions();//地图标记类型的图层参数配置类
        oStart.position(latLngs.get(0));//图层位置点，第一个点为起点
        oStart.icon(startBD);//设置图层图片
        oStart.zIndex(1);//设置图层Index
        //添加起点图层
        mMarkerA = (Marker) (mBaiduMap.addOverlay(oStart));

        //添加终点图层
        MarkerOptions oFinish = new MarkerOptions().position(latLngs.get(latLngs.size() - 1)).icon(finishBD).zIndex(2);
        mMarkerB = (Marker) (mBaiduMap.addOverlay(oFinish));

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
}