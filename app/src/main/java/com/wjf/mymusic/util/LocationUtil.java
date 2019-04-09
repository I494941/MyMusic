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
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLocType()==BDLocation.TypeGpsLocation = " + (location.getLocType() == BDLocation.TypeGpsLocation));
                LogUtil.e("1111====LocationUtil====", "获取到的定位信息：location.getLocType() = " + location.getLocType());

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

                LogUtil.e("2222====LocationUtil====", "获取到的定位类型：location.getLocType() = " + location.getLocType());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getLongitude() = " + location.getLongitude());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getLatitude() = " + location.getLatitude());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getAddrStr() = " + location.getAddrStr());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getCountry() = " + location.getCountry());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getProvince() = " + location.getProvince());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getCity() = " + location.getCity());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getDistrict() = " + location.getDistrict());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getStreet() = " + location.getStreet());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getLocationDescribe() = " + location.getLocationDescribe());
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getLocType()==BDLocation.TypeGpsLocation = " + (location.getLocType() == BDLocation.TypeGpsLocation));
                LogUtil.e("2222====LocationUtil====", "获取到的定位信息：location.getLocType() = " + location.getLocType());

                onResponse.getAddrDetail(location);
            }
        });
        locationClient.start();//start：启动定位SDK。 stop：关闭定位SDK。
        locationClient.requestLocation();
    }

    private static LocationClientOption initBaiduMapOption(int span) {
        LocationClientOption option = new LocationClientOption();

        /**
         * 默认高精度，设置定位模式
         * LocationMode.Hight_Accuracy 高精度定位模式：这种定位模式下，会同时使用
         * LocationMode.Battery_Saving 低功耗定位模式：这种定位模式下，不会使用GPS，只会使用网络定位。
         * LocationMode.Device_Sensors 仅用设备定位模式：这种定位模式下，
         */
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);

        /**
         * 默认是true，设置是否使用gps定位
         * 如果设置为false，即使mOption.setLocationMode(LocationMode.Hight_Accuracy)也不会gps定位
         */
        option.setOpenGps(true);

        /**
         * 默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
         * 目前国内主要有以下三种坐标系：
         1. wgs84：目前广泛使用的GPS全球卫星定位系统使用的标准坐标系；
         2. gcj02：经过国测局加密的坐标；
         3. bd09：为百度坐标系，其中bd09ll表示百度经纬度坐标，bd09mc表示百度墨卡托米制坐标；
         * 在国内获得的坐标系类型可以是：国测局坐标、百度墨卡托坐标 和 百度经纬度坐标。
         在海外地区，只能获得WGS84坐标。请在使用过程中注意选择坐标。
         */
        option.setCoorType("bd09ll");

        /**
         * 默认0，即仅定位一次；设置间隔需大于等于1000ms，表示周期性定位
         * 如果不在AndroidManifest.xml声明百度指定的Service，周期性请求无法正常工作
         * 这里需要注意的是：如果是室外gps定位，不用访问服务器，设置的间隔是3秒，那么就是3秒返回一次位置
         如果是WiFi基站定位，需要访问服务器，这个时候每次网络请求时间差异很大，设置的间隔是3秒，
         只能大概保证3秒左右会返回就一次位置，有时某次定位可能会5秒才返回
         */
        option.setScanSpan(span);

        /**
         * 默认false，设置是否需要地址信息
         * 返回省、市、区、街道等地址信息，这个api用处很大，
         很多新闻类app会根据定位返回的市区信息推送用户所在市的新闻
         */
        option.setIsNeedAddress(true);

        /**
         * 默认false，设置是否需要位置语义化结果
         * 可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
         */
        option.setIsNeedLocationDescribe(true);

        /**
         * 默认false,设置是否需要设备方向传感器的方向结果
         * 一般在室外gps定位时，返回的位置信息是带有方向的，但是有时候gps返回的位置也不带方向，
         这个时候可以获取设备方向传感器的方向
         * wifi基站定位的位置信息是不带方向的，如果需要可以获取设备方向传感器的方向
         */
        option.setNeedDeviceDirect(false);

        /**
         * 默认false，设置是否当gps有效时按照设定的周期频率输出GPS结果
         * 室外gps有效时，周期性1秒返回一次位置信息，其实就是设置了
         locationManager.requestLocationUpdates中的minTime参数为1000ms，1秒回调一个gps位置
         * 如果设置了mOption.setScanSpan(3000)，那minTime就是3000ms了，3秒回调一个gps位置
         */
        option.setLocationNotify(true);

        /**
         * 默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
         * 如果你已经拿到了你要的位置信息，不需要再定位了，不杀死留着干嘛
         */
        option.setIgnoreKillProcess(true);

        /**
         * 默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
         * POI就是获取到的位置附近的一些商场、饭店、银行等信息
         */
        option.setIsNeedLocationPoiList(true);

        /**
         * 默认false，设置是否收集CRASH信息，默认收集
         */
        option.SetIgnoreCacheException(false);

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，V7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前Wi-Fi是否超出有效期，若超出有效期，会先重新扫描Wi-Fi，然后定位

        /**
         * 默认false，设置定位时是否需要海拔高度信息，默认不需要，除基础定位版本都可用
         */
        option.setIsNeedAltitude(false);

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        return option;
    }

    public interface OnResponse {

        void getAddrDetail(BDLocation location);
    }
}
