package com.wjf.mymusic.ui.myDemo.basepopup;

import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.basepopup.quickpopupbuilder.QuickPopupBuilderActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/4/30.
 */
public class BasePopupActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv1.setText("QuickPopupBuilderActivity");
    }

    @OnClick({R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(QuickPopupBuilderActivity.class);
                break;
            case R.id.tv2:
                break;
        }
    }
}
