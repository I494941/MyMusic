package com.wjf.mymusic.ui.myDemo.baidumap.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.wjf.mymusic.R;
import com.wjf.mymusic.ui.myDemo.baidumap.mapInfo.MapInfoActivity;
import com.wjf.mymusic.ui.myDemo.litepalBean.LocationBean;
import com.wjf.mymusic.ui.myDemo.litepalBean.LocationBean2;
import com.wjf.mymusic.util.DateUtil;
import com.wjf.mymusic.util.LocationUtil;
import com.wjf.mymusic.util.LogUtil;

/**
 * Created by wjf on 2019/4/2.
 */
public class LocationService extends Service {

    public static boolean isLocating;
    private LocationClient locationClient;
    public static final int NOTICE_ID = 100;
    public static final String CHANNEL_ID = "fore_service";
    private LatLng preLatLng;   //上次定位 经纬度
    private int mSpan = 6000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isLocating = false;
        locationClient = new LocationClient(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isLocating) {
            isLocating = true;
            initLocation();
            //使用startForeground方法开启前台服务
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "前台服务", NotificationManager.IMPORTANCE_HIGH);
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.createNotificationChannel(channel);
            }
            startForeground(NOTICE_ID, getNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation() {
        LocationUtil.getLocationContinue(locationClient, mSpan, location -> {

            LogUtil.e("1111====LocationService====", "location.getLocType() = " + location.getLocType());
            LogUtil.e("1111====LocationService====", "location.getLongitude() = " + location.getLongitude());
            LogUtil.e("1111====LocationService====", "location.getLatitude() = " + location.getLatitude());
            LogUtil.e("1111====LocationService====", "location.getAddrStr() = " + location.getAddrStr());
            LogUtil.e("1111====LocationService====", "location.getCountry() = " + location.getCountry());
            LogUtil.e("1111====LocationService====", "location.getProvince() = " + location.getProvince());
            LogUtil.e("1111====LocationService====", "location.getCity() = " + location.getCity());
            LogUtil.e("1111====LocationService====", "location.getDistrict() = " + location.getDistrict());
            LogUtil.e("1111====LocationService====", "location.getStreet() = " + location.getStreet());
            LogUtil.e("1111====LocationService====", "location.getLocationDescribe() = " + location.getLocationDescribe());


            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


            LogUtil.e("2222====LocationService====", "location.getLongitude() = " + location.getLongitude());
            LogUtil.e("2222====LocationService====", "location.getLatitude() = " + location.getLatitude());
            if (preLatLng != null) {
                LogUtil.e("2222====LocationService====", "preLng = " + preLatLng.longitude);
                LogUtil.e("2222====LocationService====", "preLat = " + preLatLng.latitude);
                LogUtil.e("2222====LocationService====", "DistanceUtil.getDistance(location.getLatitude(), location.getLongitude(), preLat, preLng) = " + DistanceUtil.getDistance(preLatLng, latLng));
            }


            insertLocation2(location, preLatLng == null ? 0 : DistanceUtil.getDistance(preLatLng, latLng));
            sendLocationBroadcast(location, preLatLng == null ? 0 : DistanceUtil.getDistance(preLatLng, latLng));
            if (preLatLng == null) {
                //第一次定位
                insertLocation(location, 0);
                preLatLng = latLng;
            } else if (DistanceUtil.getDistance(preLatLng, latLng) > 500) {
                //与上一次有效地址距离大于500米，定位有效
                insertLocation(location, DistanceUtil.getDistance(preLatLng, latLng));
                preLatLng = latLng;
            }
        });
    }

    private void sendLocationBroadcast(BDLocation location, double distance) {
        Intent intent = new Intent();
        //对应BroadcastReceiver中intentFilter的action
        intent.setAction("get_location");

        LocationBean locationBean = new LocationBean();
        locationBean.setTime(DateUtil.getTime());
        locationBean.setLng(location.getLongitude());
        locationBean.setLat(location.getLatitude());
        locationBean.setDistance(distance);

        intent.putExtra("LocationBean", locationBean);
        //发送广播
        sendBroadcast(intent);
    }

    private void insertLocation(BDLocation location, double distance) {
        LocationBean locationBean = new LocationBean();
        locationBean.setLng(location.getLongitude());
        locationBean.setLat(location.getLatitude());
        locationBean.setTime(DateUtil.getTime());
        locationBean.setDistance(distance);
        locationBean.save();
    }

    private void insertLocation2(BDLocation location, double distance) {
        LocationBean2 locationBean = new LocationBean2();
        locationBean.setLng(location.getLongitude());
        locationBean.setLat(location.getLatitude());
        locationBean.setTime(DateUtil.getTime());
        locationBean.setDistance(distance);
        locationBean.save();
    }

    private Notification getNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MapInfoActivity.class), 0);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setContentTitle("设置下拉列表里的标题")
                .setContentText("设置要显示的内容")
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())//设置通知上的时间
                .setShowWhen(true)//设置是否显示时间
                .setAutoCancel(true)
                .build();
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        locationClient.stop();
        isLocating = false;
        super.onDestroy();
    }
}
