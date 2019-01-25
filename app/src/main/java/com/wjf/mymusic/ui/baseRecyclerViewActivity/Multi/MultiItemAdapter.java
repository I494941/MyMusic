package com.wjf.mymusic.ui.baseRecyclerViewActivity.Multi;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wjf.mymusic.R;

import java.util.List;

/**
 * Created by wjf on 2019/1/24.
 */
public class MultiItemAdapter extends BaseQuickAdapter<MultiBean.ItemBean, BaseViewHolder> {


    public MultiItemAdapter(int layoutResId, @Nullable List<MultiBean.ItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MultiBean.ItemBean item) {
        helper.setText(R.id.tv, item.getName())
                .setBackgroundRes(R.id.tv, R.drawable.gray_border_no_corner_bg);
    }
}
