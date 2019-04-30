package com.wjf.mymusic.ui.myDemo.recyclerViewActivity;

import android.view.View;
import android.widget.TextView;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi.MultiRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base.BaseRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered.StaggeredRecyclerViewActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class RecyclerViewActivity extends BaseToolbarActivity {

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
        mTv1.setText(R.string.Base_RecyclerView);
        mTv3.setText(R.string.Multi_RecyclerView);
        mTv3.setVisibility(View.VISIBLE);
        mTv2.setText(R.string.Staggered_RecyclerViewActivity);
    }

    @OnClick({R.id.tv1, R.id.tv3, R.id.tv2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv1:
                startActivity(BaseRecyclerViewActivity.class);
                break;
            case R.id.tv3:
                startActivity(MultiRecyclerViewActivity.class);
                break;
            case R.id.tv2:
                startActivity(StaggeredRecyclerViewActivity.class);
                break;
        }
    }
}
