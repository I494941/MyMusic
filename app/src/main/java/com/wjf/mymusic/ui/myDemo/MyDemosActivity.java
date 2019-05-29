package com.wjf.mymusic.ui.myDemo;

import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.baidumap.BaiduMapActivity;
import com.wjf.mymusic.ui.myDemo.basepopup.BasePopupActivity;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.EventBusFirstActivity;
import com.wjf.mymusic.ui.myDemo.parcelable.ParcelableActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.RecyclerViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class MyDemosActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv4)
    TextView mTv4;
    @BindView(R.id.tv5)
    TextView mTv5;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv1.setText(R.string.RecyclerView);
        mTv3.setText(R.string.BaiduMap);
        mTv3.setVisibility(View.VISIBLE);
        mTv4.setText(R.string.BasePopup);
        mTv4.setVisibility(View.VISIBLE);
        mTv5.setText(R.string.Parcelable);
        mTv5.setVisibility(View.VISIBLE);
        mTv2.setText(R.string.EventBus);
    }

    @OnClick({R.id.tv1, R.id.tv3, R.id.tv4,  R.id.tv5, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(RecyclerViewActivity.class);
                break;
            case R.id.tv3:
                startActivity(BaiduMapActivity.class);
                break;
            case R.id.tv4:
                startActivity(BasePopupActivity.class);
                break;
            case R.id.tv5:
                startActivity(ParcelableActivity.class);
                break;
            case R.id.tv2:
                startActivity(EventBusFirstActivity.class);
                break;
        }
    }
}
