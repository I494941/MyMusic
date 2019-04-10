package com.wjf.mymusic.ui.myDemo.baidumap.navi;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import androidx.fragment.app.FragmentActivity;
import com.baidu.navisdk.adapter.*;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wjf.mymusic.constants.Constants;
import com.wjf.mymusic.util.FileUtil;
import com.wjf.mymusic.util.LogUtil;
import com.wjf.mymusic.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangshuai on 2018/12/21 0021 11:10.
 */
public class NavigationUtil {
    public static boolean hasInitSuccess;

    public static void initNavi(final Activity activity) {
//        if (!AndPermission.hasPermissions(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            //ToastUtil.show(activity, "缺少定位或存储权限，不能使用导航");
//            return;
//        }

        new RxPermissions((FragmentActivity) activity)
                .request(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {

                        BaiduNaviManagerFactory.getBaiduNaviManager().init(activity,
                                FileUtil.getImageDir(), "navi", new IBaiduNaviManager.INaviInitListener() {

                                    @Override
                                    public void onAuthResult(int status, String msg) {
                                        String result;
                                        if (0 == status) {
                                            result = "key校验成功!";
                                        } else {
                                            result = "key校验失败, " + msg;
                                        }
                                        //showShortToast(result);
                                    }

                                    @Override
                                    public void initStart() {
                                        //showShortToast("百度导航引擎初始化开始");
                                    }

                                    @Override
                                    public void initSuccess() {
                                        //showShortToast("百度导航引擎初始化成功");
                                        hasInitSuccess = true;
                                        // 初始化tts
                                        initTTS(activity);
                                    }

                                    @Override
                                    public void initFailed() {
                                        //showShortToast("百度导航引擎初始化失败");
                                    }
                                });
                    }
                });
    }

    private static void initTTS(Activity activity) {
        // 使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(activity.getApplicationContext(),
                FileUtil.getImageDir(), "navi", Constants.TTSAPI);

        // 不使用内置TTS
        //         BaiduNaviManagerFactory.getTTSManager().initTTS(mTTSCallback);

        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                        LogUtil.e("navi", "ttsCallback.onPlayStart");
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                        LogUtil.e("navi", "ttsCallback.onPlayEnd");
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                        LogUtil.e("navi", "ttsCallback.onPlayError");
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        LogUtil.e("navi", "ttsHandler.msg.what=" + msg.what);
                    }
                }
        );
    }

    public static void routeplanToNavi(final Context context, int coType, double startLongitude, double startLatitude, String startName, double endLongitude, double endLatitude, String endName) {
        if (!hasInitSuccess) {
            //Toast.makeText(context, "还未初始化!", Toast.LENGTH_SHORT).show();
            ToastUtil.show(context, "还未初始化");
        }

        BNRoutePlanNode sNode = new BNRoutePlanNode(startLongitude, startLatitude, startName, null, coType);
        BNRoutePlanNode eNode = new BNRoutePlanNode(endLongitude, endLatitude, endName, null, coType);

        final BNRoutePlanNode mStartNode = sNode;

        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
        list.add(sNode);
        list.add(eNode);

        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            /*case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                Toast.makeText(mContext, "算路开始", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                Toast.makeText(mContext, "算路成功", Toast.LENGTH_SHORT)
                                        .show();
                                break;*/
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                //Toast.makeText(context, "算路失败", Toast.LENGTH_SHORT).show();
                                ToastUtil.show(context, "算路失败");
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                //Toast.makeText(mContext, "算路成功准备进入导航", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(context, NavigationGuideActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("routePlanNode", mStartNode);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            default:
                                // nothing
                                break;
                        }
                    }
                });
    }
}
