package com.wjf.mymusic.ui.myDemo.recyclerViewActivity;

import android.view.View;

import com.wjf.mymusic.R;
import com.wjf.mymusic.base.BaseToolbarActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.Multi.MultiRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.base.BaseRecyclerViewActivity;
import com.wjf.mymusic.ui.myDemo.recyclerViewActivity.staggered.StaggeredRecyclerViewActivity;

import butterknife.OnClick;

/**
 * Created by wjf on 2019/1/28.
 */
public class RecyclerViewActivity extends BaseToolbarActivity {

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_recyclerview;
    }

    @Override
    protected void initViewsAndEvents() {

    }

    @OnClick({R.id.tv_base_recyclerview, R.id.tv_multi_recyclerview, R.id.tv_staggered_recyclerview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_base_recyclerview:
                startActivity(BaseRecyclerViewActivity.class);
                break;
            case R.id.tv_multi_recyclerview:
                startActivity(MultiRecyclerViewActivity.class);
                break;
            case R.id.tv_staggered_recyclerview:
                startActivity(StaggeredRecyclerViewActivity.class);
                break;
        }
    }
}
