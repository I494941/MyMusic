package com.wjf.mymusic.ui.myDemo.basepopup.quickpopupbuilder;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;

import butterknife.BindView;
import butterknife.OnClick;
import razerdp.basepopup.QuickPopupBuilder;
import razerdp.basepopup.QuickPopupConfig;

/**
 * Created by wjf on 2019/4/30.
 */
public class QuickPopupBuilderActivity extends BaseToolbarActivity {

    @BindView(R.id.tv1)
    TextView mTv1;
    @BindView(R.id.tv3)
    TextView mTv3;
    @BindView(R.id.tv2)
    TextView mTv2;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_basepopup;
    }

    @Override
    protected void initViewsAndEvents() {
        mTv1.setText("QuickPopupBuilderActivity");
    }

    @OnClick({R.id.tv1, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                QuickPopupBuilder.with(mContext)
                        .contentView(R.layout.popup_trail_popup)
                        .config(new QuickPopupConfig()
                                .gravity(Gravity.CENTER)
                                .gravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL)
                                .withClick(R.id.tv1, v -> showShortToast("clicked tv1"))
                                .withClick(R.id.tv2, v -> showShortToast("clicked tv2")))
                        .show();//.show(anchorView)。。。。。。
                break;
            case R.id.tv2:
                break;
        }
    }
}
