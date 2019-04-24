package com.wjf.mymusic.ui.myDemo.baidumap.mapInfo;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.adapter.LocationAdapter;
import com.wjf.mymusic.ui.myDemo.baidumap.navi.NavigationUtil;
import com.wjf.mymusic.ui.myDemo.baidumap.service.LocationService;
import com.wjf.mymusic.ui.myDemo.litepalBean.LocationBean;
import com.wjf.mymusic.ui.myDemo.litepalBean.LocationBean2;
import com.wjf.mymusic.util.LocationUtil;
import com.wjf.mymusic.util.LogUtil;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjf on 2019/3/28.
 */
public class MapInfoActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.rv_location)
    RecyclerView mRvLocation;
    @BindView(R.id.rv_location_sql)
    RecyclerView mRvLocationSQL;

    private MyLocationBroadcastReceiver mBroadcastReceiver;
    private List<LocationBean> mList = new ArrayList<>();
    private LocationAdapter mAdapter;
    private List<LocationBean> mListSQL = new ArrayList<>();
    private LocationAdapter mAdapterSQL;
    private boolean isShowSQL;

    final RxPermissions rxPermissions = new RxPermissions(this);

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_map_info;
    }

    @Override
    protected void initViewsAndEvents() {
        if (LocationService.isLocating) {
            mTv1.setText("正在定位……");
        } else {
            mTv1.setText("开启服务");
        }

        initRecyclerView();

        NavigationUtil.initNavi(this);
    }

    private void initRecyclerView() {
        mRvLocation.setLayoutManager(new LinearLayoutManager(mContext));
        mRvLocation.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
        mRvLocation.setNestedScrollingEnabled(false);//去滑动
        mRvLocation.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));//分割线
        mAdapter = new LocationAdapter(R.layout.item_location, mList);

        RelativeLayout rlEmpty = (RelativeLayout) getLayoutInflater().inflate(R.layout.empty_view, null);
        TextView tvEmpty = rlEmpty.findViewById(R.id.tv_empty);
        SpannableStringBuilder style = new SpannableStringBuilder("没有你要的结果，扯淡了吧");
        style.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red)), 8, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new AbsoluteSizeSpan(20, true), 8, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        tvEmpty.setText(style);
        rlEmpty.setOnClickListener(v -> showShortToast("哈哈哈哈哈哈哈哈哈"));
        mAdapter.setEmptyView(rlEmpty);

        mRvLocation.setAdapter(mAdapter);

        mRvLocationSQL.setLayoutManager(new LinearLayoutManager(mContext));
        mRvLocationSQL.setFocusable(false);//去焦点，否则RecyclerView显示在最上面
        mRvLocationSQL.setNestedScrollingEnabled(false);//去滑动
        mRvLocationSQL.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));//分割线
        mAdapterSQL = new LocationAdapter(R.layout.item_location, mListSQL);

        RelativeLayout rlEmptySQL = (RelativeLayout) getLayoutInflater().inflate(R.layout.empty_view, null);
        TextView tvEmptySQL = rlEmptySQL.findViewById(R.id.tv_empty);
        tvEmptySQL.setText(style);
        rlEmptySQL.setOnClickListener(v -> showShortToast("呵呵呵呵呵呵呵呵呵"));
        mAdapterSQL.setEmptyView(rlEmptySQL);

        mRvLocationSQL.setAdapter(mAdapterSQL);
    }

    // 选择在Activity生命周期方法中的onResume()中注册
    @Override
    protected void onResume() {
        super.onResume();

        // 1. 实例化BroadcastReceiver子类 &  IntentFilter
        mBroadcastReceiver = new MyLocationBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();

        // 2. 设置接收广播的类型
        intentFilter.addAction("get_location_my");

        // 3. 动态注册：调用Context的registerReceiver（）方法
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    // 注册广播后，要在相应位置记得销毁广播
    // 即在onPause() 中unregisterReceiver(mBroadcastReceiver)
    // 当此Activity实例化时，会动态将MyBroadcastReceiver注册到系统中
    // 当此Activity销毁时，动态注册的MyBroadcastReceiver将不再接收到相应的广播。
    @Override
    protected void onPause() {
        super.onPause();
        //销毁在onResume()方法中的广播
        unregisterReceiver(mBroadcastReceiver);
    }

    @OnClick({R.id.tv1, R.id.tv2, R.id.tv3, R.id.tv4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startLocationService();//开始定位 服务
                            }
                        });
                break;
            case R.id.tv2:
                Intent mIntent2 = new Intent(MapInfoActivity.this, LocationService.class);
                stopService(mIntent2);
                mTv1.setText("开启服务");
                break;
            case R.id.tv3:
                rxPermissions
                        .request(Manifest.permission.ACCESS_FINE_LOCATION)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                startLocation();//开始定位 定位1次
                            }
                        });
                break;
            case R.id.tv4:
                rxPermissions
                        .request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .subscribe(granted -> {
                            if (granted) { // Always true pre-M
                                // I can control the Location now
                                if (!isShowSQL) {
                                    isShowSQL = true;
                                    mTv4.setText("显示正在定位的数据");
                                    mListSQL.clear();
                                    List<LocationBean> locationBeans = LitePal.findAll(LocationBean.class);
                                    mListSQL.addAll(locationBeans);
                                    mAdapterSQL.notifyDataSetChanged();
                                    mRvLocation.setVisibility(View.GONE);
                                    mRvLocationSQL.setVisibility(View.VISIBLE);

                                    for (int i = 0; i < locationBeans.size(); i++) {
                                        LogUtil.e("11111+++", locationBeans.get(i).toString());
                                    }
                                } else {
                                    isShowSQL = false;
                                    mTv4.setText("显示本地定位的数据");
                                    mRvLocation.setVisibility(View.VISIBLE);
                                    mRvLocationSQL.setVisibility(View.GONE);
                                }

                                List<LocationBean2> locationBean2s = LitePal.findAll(LocationBean2.class);
                                for (int i = 0; i < locationBean2s.size(); i++) {
                                    LogUtil.e("22222+++", locationBean2s.get(i).toString());
                                }
                            }
                        });
                break;
        }
    }

    private void startLocation() {
        LocationUtil.getLocation(mContext, location -> {

            LogUtil.e("1111====MapInfoActivity====", "location.getLocType() = " + location.getLocType());
            LogUtil.e("1111====MapInfoActivity====", "location.getLongitude() = " + location.getLongitude());
            LogUtil.e("1111====MapInfoActivity====", "location.getLatitude() = " + location.getLatitude());
            LogUtil.e("1111====MapInfoActivity====", "location.getAddrStr() = " + location.getAddrStr());
            LogUtil.e("1111====MapInfoActivity====", "location.getCountry() = " + location.getCountry());
            LogUtil.e("1111====MapInfoActivity====", "location.getProvince() = " + location.getProvince());
            LogUtil.e("1111====MapInfoActivity====", "location.getCity() = " + location.getCity());
            LogUtil.e("1111====MapInfoActivity====", "location.getDistrict() = " + location.getDistrict());
            LogUtil.e("1111====MapInfoActivity====", "location.getStreet() = " + location.getStreet());
            LogUtil.e("1111====MapInfoActivity====", "location.getLocationDescribe() = " + location.getLocationDescribe());
        });
    }

    private void startLocationService() {
        Intent mIntent = new Intent(MapInfoActivity.this, LocationService.class);
        startService(mIntent);
        mTv1.setText("正在定位……");
    }

    /**
     * 定义广播接收器（内部类）
     *
     * @author lenovo
     */
    private class MyLocationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            LocationBean location = (LocationBean) intent.getExtras().getSerializable("LocationBean");
            mList.add(location);
            mAdapter.notifyDataSetChanged();
        }
    }
}
