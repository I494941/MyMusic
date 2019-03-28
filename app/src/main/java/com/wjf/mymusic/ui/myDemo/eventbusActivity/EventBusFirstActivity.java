package com.wjf.mymusic.ui.myDemo.eventbusActivity;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.bean.MessageEvent;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.bean.MessageEvent2;
import com.wjf.mymusic.util.LogUtil;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by wjf on 2019/3/25.
 */
public class EventBusFirstActivity extends BaseToolbarActivity {

    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_eventbus;
    }

    @Override
    protected void initViewsAndEvents() {
        EventBus.getDefault().register(this);
        mTv.setText("MapActivity");
    }

    @OnClick(R.id.tv)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                startActivity(EventBusSecondActivity.class);
                break;
        }
    }

    /**
     * EventBusThirdActivity 传递参数(MessageEvent2)过来  用MessageEvent接收不到信息
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent messageEvent) {
        mTv.setText(messageEvent.getMessage());

        LogUtil.e("1111111+MapActivity", " = ");
        LogUtil.e("1111111+MapActivity", " = " + messageEvent.getMessage());
    }

    /**
     * EventBusThirdActivity 传递参数(MessageEvent2)过来  用MessageEvent接收信息
     * 虽然传递了两个页面
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void Event(MessageEvent2 messageEvent2) {
        mTv.setText(messageEvent2.getMessage());

        LogUtil.e("2222222+MapActivity", " = ");
        LogUtil.e("2222222+MapActivity", " = " + messageEvent2.getMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
