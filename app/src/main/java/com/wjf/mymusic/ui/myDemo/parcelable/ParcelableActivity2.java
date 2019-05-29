package com.wjf.mymusic.ui.myDemo.parcelable;

import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;

import butterknife.BindView;

/**
 * Created by wjf on 2019/4/30.
 */
public class ParcelableActivity2 extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mydemo;
    }

    @Override
    protected void initViewsAndEvents() {

        ParcelableBean parcelableBean = getIntent().getParcelableExtra("bean");
        if (parcelableBean != null) {
            mTv1.setText("parcelableBean = " + parcelableBean.toString());
        }
    }
}
