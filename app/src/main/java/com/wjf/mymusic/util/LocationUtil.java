package com.wjf.mymusic.util;

import android.content.Context;
import android.text.TextUtils;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationUtil {

    public static void getLocation(final Context context, final OnResponse onResponse) {

        final LocationClient locationClient = new LocationClient(context);
        //设置定位条件
        LocationClientOption option = initBaiduMapOption(0);
        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }

                LogUtil.e("1111====LocationUtil====", "获取到的定位类型：location.getLocType() = " + location.getLocType());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLongitude() = " + location.getLongitude());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLatitude() = " + location.getLatitude());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getAddrStr() = " + location.getAddrStr());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getCountry() = " + location.getCountry());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getProvince() = " + location.getProvince());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getCity() = " + location.getCity());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getDistrict() = " + location.getDistrict());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getStreet() = " + location.getStreet());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLocationDescribe() = " + location.getLocationDescribe());

                if (TextUtils.isEmpty(location.getAddrStr()))
                    locationClient.restart();
                else {
                    onResponse.getAddrDetail(location);
                    locationClient.stop();
                }
            }
        });

        if (locationClient.isStarted()) {
            locationClient.stop();
        }
        locationClient.start();//start：启动定位SDK。 stop：关闭定位SDK。
        locationClient.requestLocation();
    }

    /**
     * @param locationClient
     * @param span           设置发起定位请求的间隔，int类型，单位ms
     *                       如果设置为0，则代表单次定位，即仅定位一次，默认为0
     *                       如果设置非0，需设置1000ms以上才有效
     * @param onResponse
     */
    public static void getLocationContinue(LocationClient locationClient, int span, final OnResponse onResponse) {

        //设置定位条件
        LocationClientOption option = initBaiduMapOption(span);

        locationClient.setLocOption(option);
        //注册位置监听器
        locationClient.registerLocationListener(new BDAbstractLocationListener() {

            @Override
            public void onReceiveLocation(BDLocation location) {
                if (location == null) {
                    return;
                }

                LogUtil.e("1111====LocationUtil====", "获取到的定位类型：location.getLocType() = " + location.getLocType());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLongitude() = " + location.getLongitude());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLatitude() = " + location.getLatitude());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getAddrStr() = " + location.getAddrStr());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getCountry() = " + location.getCountry());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getProvince() = " + location.getProvince());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getCity() = " + location.getCity());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getDistrict() = " + location.getDistrict());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getStreet() = " + location.getStreet());
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLocationDescribe() = " + location.getLocationDescribe());

                onResponse.getAddrDetail(location);
            }
        });
        locationClient.start();//start：启动定位SDK。 stop：关闭定位SDK。
        locationClient.requestLocation();
    }

    private static LocationClientOption initBaiduMapOption(int span) {
        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认GCJ02
        //GCJ02：国测局坐标；
        //BD09ll：百度经纬度坐标；
        //BD09：百度墨卡托坐标；
        //海外地区定位，无需设置坐标类型，统一返回WGS84类型坐标

        option.setScanSpan(span);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationDescribe(true);

        option.setLocationNotify(true);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
        ////可选，是否需要地址信息，默认为不需要，即参数为false
        ////如果开发者需要获得当前点的地址信息，此处必须为true
        return option;
    }

    public interface OnResponse {

        void getAddrDetail(BDLocation location);
    }

    //计算两点之间距离
    private static final double EARTH_RADIUS = 6378.137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * Lat1 Lung1 表示A点经纬度，Lat2 Lung2 表示B点经纬度； a=Lat1 – Lat2 为两点纬度之差 b=Lung1
     * -Lung2 为两点经度之差； 6378.137为地球半径，单位为千米；  计算出来的结果单位为千米。
     * 通过经纬度获取距离(单位：千米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;    //乘以1000是换算成米
        return s;
    }
}
