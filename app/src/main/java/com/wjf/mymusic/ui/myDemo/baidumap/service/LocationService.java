package com.wjf.mymusic.ui.myDemo.baidumap.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.wjf.mymusic.R;
import com.wjf.mymusic.sp.SharePreferenceManager;
import com.wjf.mymusic.ui.myDemo.baidumap.mapInfo.MapInfoActivity;
import com.wjf.mymusic.util.LocationUtil;
import com.wjf.mymusic.util.LogUtil;

/**
 * Created by wjf on 2019/4/2.
 */
public class LocationService extends Service {

    public static boolean isLocating;
    private LocationClient locationClient;
    public static final int NOTICE_ID = 100;
    protected SharePreferenceManager sp = new SharePreferenceManager(this);

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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (!isLocating) {
            isLocating = true;
            initLocation();
            //使用startForeground方法开启前台服务
            startForeground(NOTICE_ID, getNotification());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initLocation() {
        LocationUtil.getLocationContinue(locationClient, 1000, new LocationUtil.OnResponse() {
            @Override
            public void getAddrDetail(BDLocation location) {

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
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Notification getNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MapInfoActivity.class), 0);
        return new Notification.Builder(this)
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
