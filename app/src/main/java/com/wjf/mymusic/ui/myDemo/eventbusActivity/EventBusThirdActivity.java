package com.wjf.mymusic.ui.myDemo.eventbusActivity;

import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.eventbusActivity.bean.MessageEvent2;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by wjf on 2019/3/25.
 */
public class EventBusThirdActivity extends BaseToolbarActivity {

    @BindView(R.id.tv)
    TextView mTv;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_eventbus;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv.setText("EventBusThirdActivity");
    }

    @OnClick(R.id.tv)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv:
                EventBus.getDefault().post(new MessageEvent2("EventBus 传参了！"));
                finish();
                break;
        }
    }
}
